package org.cleartk.corpus.europarl;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CASException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.SofaCapability;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.cleartk.corpus.europarl.type.Chapter;
import org.cleartk.corpus.europarl.type.ParallelChunk;
import org.cleartk.corpus.europarl.type.Speaker;

import de.tudarmstadt.ukp.dkpro.core.api.metadata.type.DocumentMetaData;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Paragraph;
import de.tudarmstadt.ukp.dkpro.core.io.xmi.XmiWriter;


@SofaCapability(
		inputSofas = {ParalleDocumentTextReader.EN_VIEW, ParalleDocumentTextReader.FR_VIEW},
		outputSofas = {EuroparlParallelTextAnnotator.EN_TEXT_VIEW, EuroparlParallelTextAnnotator.FR_TEXT_VIEW})
@TypeCapability(
		outputs = "org.cleartk.corpus.europarl.type.ParallelChunk")
public class EuroparlParallelTextAnnotator extends JCasAnnotator_ImplBase{
	public static final String EN_TEXT_VIEW = "enTextView";
	public static final String FR_TEXT_VIEW = "frTextView";
	private static final String TAG = "TAG";
	private Set<String> tags = new HashSet<>();

	public static AnalysisEngineDescription getDescription() throws ResourceInitializationException {
		return AnalysisEngineFactory.createEngineDescription(EuroparlParallelTextAnnotator.class);
	}

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		try {
			setAnnotations(aJCas, ParalleDocumentTextReader.EN_VIEW, EN_TEXT_VIEW);
			setAnnotations(aJCas, ParalleDocumentTextReader.FR_VIEW, FR_TEXT_VIEW);
			align(aJCas);
		} catch (CASException e) {
			e.printStackTrace();
		}
	}

	private void align(JCas aJCas) throws CASException {
		List<ParallelChunk> enChunks = new ArrayList<>(JCasUtil.select(aJCas.getView(EN_TEXT_VIEW), ParallelChunk.class));
		List<ParallelChunk> frChunks = new ArrayList<>(JCasUtil.select(aJCas.getView(FR_TEXT_VIEW), ParallelChunk.class));

		if (enChunks.size() != frChunks.size()){
			String enFile = DocumentMetaData.get(aJCas.getView(EN_TEXT_VIEW)).getDocumentId();
			String frFile = DocumentMetaData.get(aJCas.getView(FR_TEXT_VIEW)).getDocumentId();
			getLogger().error("The size of two parallel chunks are not equal for " + 
					enFile + " and " + frFile + "." + enChunks.size() + "<>" + frChunks.size());
		}

		for (int i = 0; i < enChunks.size(); i++){
			ParallelChunk enChunk = enChunks.get(i);
			ParallelChunk frChunk = frChunks.get(i);
			enChunk.setDocOffset(i); 
			frChunk.setDocOffset(i);
			enChunk.setTranslation(frChunk);
			frChunk.setTranslation(enChunk);
		}

	}

	private void setAnnotations(JCas aJCas, String orgViewName, String textViewName) throws CASException {
		JCas view = aJCas.getView(orgViewName);
		JCas textView = aJCas.createView(textViewName);

		String text = view.getDocumentText();

		List<String> lines = split(text);
		StringBuilder parallelChunksText = new StringBuilder();
		for (String line: lines){
			if (!line.startsWith("<")){
				parallelChunksText.append(line);
				parallelChunksText.append("\n");
			}
		}

		DocumentMetaData.copy(view, textView);
		textView.setDocumentText(parallelChunksText.toString());
		textView.setDocumentLanguage(view.getDocumentLanguage());

		int start = 0;
		Map<String, Map<String, String>> tagParams = new HashMap<>();
		Map<String, Integer> tagStart = new HashMap<>();
		
		for (String line: lines){
			if (!line.startsWith("<")){
				new ParallelChunk(textView, start, start + line.length()).addToIndexes();
				start += line.length() + 1;
			} else {
				Map<String, String> params = parseLine(line);
				String tag = params.get(TAG);
				addAnnotation(textView, tagParams.get(tag), tagStart.get(tag), start);

				tagStart.put(tag, start);
				tagParams.put(tag, params);
			}
		}
		
		for (String tag: tagParams.keySet()){
			addAnnotation(textView, tagParams.get(tag), tagStart.get(tag), start);
		}

	}


	private void addAnnotation(JCas aJCas, Map<String, String> params, Integer begin, int end) {
		if (params == null || begin == end)
			return;
		
		switch (params.get(TAG).toUpperCase()) {
		case "CHAPTER":
			Chapter chapter = new Chapter(aJCas, begin, end);
			chapter.setId(params.get("ID"));
			chapter.addToIndexes();
			break;
		case "SPEAKER":
			Speaker speaker = new Speaker(aJCas, begin, end);
			speaker.setId(params.get("ID"));
			speaker.setLanguage(params.get("LANGUAGE"));
			speaker.setName(params.get("NAME"));
			speaker.addToIndexes();
			break;
		case "P":
			new Paragraph(aJCas, begin, end).addToIndexes();
			break;
		default:
			break;
		}
	}

	private Map<String, String> parseLine(String line) {
		Map<String, String> params = new HashMap<>();

		int tokenStart = line.indexOf(' ');
		if (tokenStart == -1){
			params.put(TAG, line.substring(1, line.length() - 1));
			
		} else {
			params.put(TAG, line.substring(1, tokenStart));
			
			String key = null, value = null;
			tokenStart += 1;
			for (int i = tokenStart; i < line.length(); i++){
				switch (line.charAt(i)) {
				case ' ':
				case '>':
					value = line.substring(tokenStart, i).trim();
					tokenStart = i + 1;
					params.put(key.toUpperCase(), value);
					break;
				case '=':
					key = line.substring(tokenStart, i).trim();
					tokenStart = i + 1;
					break;

				default:
					break;
				}

			}
		}

		return params;
	}

	private List<String> split(String text) {
		List<String> results = new ArrayList<>();
		int start = 0;
		while (start < text.length()){
			int end = text.indexOf('\n', start);
			if (end == -1)
				end = text.length();
			results.add(text.substring(start, end));
			start = end + 1;
		}

		return results;
	}

	@Override
	public void collectionProcessComplete() throws AnalysisEngineProcessException {
		System.out.println(tags);
		super.collectionProcessComplete();
	}


	public static void main(String[] args) throws IOException, ResourceInitializationException, UIMAException {
		File sampleDir = new File("/Users/majid/Documents/git/parallel.corpus.sampling/parallel.corpus.sampling/sample/00");
		File outputDir = new File("outputs/temp/europarl_main_method");
		if (outputDir.exists()){
			FileUtils.deleteDirectory(outputDir);
		}
		outputDir.mkdirs();

		SimplePipeline.runPipeline(ParallelFileCollectionReader.getReaderDescription(sampleDir, "en", "fr"), 
				ParalleDocumentTextReader.getDescription(), 
				EuroparlParallelTextAnnotator.getDescription(), 
				createEngineDescription(XmiWriter.class, XmiWriter.PARAM_TARGET_LOCATION, outputDir));



	}
}
