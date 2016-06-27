package org.cleartk.corpus.europarl;

import java.util.ArrayList;
import java.util.List;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CASException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.SofaCapability;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.cleartk.corpus.europarl.type.ParallelChunk;

import de.tudarmstadt.ukp.dkpro.core.api.metadata.type.DocumentMetaData;


@SofaCapability(
		inputSofas = {ParalleDocumentTextReader.EN_VIEW, ParalleDocumentTextReader.FR_VIEW},
		outputSofas = {EuroparlParallelTextAnnotator.EN_TEXT_VIEW, EuroparlParallelTextAnnotator.FR_TEXT_VIEW})
@TypeCapability(
		outputs = "org.cleartk.corpus.europarl.type.ParallelChunk")
public class EuroparlParallelTextAnnotator extends JCasAnnotator_ImplBase{
	public static final String EN_TEXT_VIEW = "enTextView";
	public static final String FR_TEXT_VIEW = "frTextView";

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
		for (String line: lines){
			if (!line.startsWith("<")){
				new ParallelChunk(textView, start, start + line.length()).addToIndexes();
				start += line.length() + 1;
			} else {
				// I should put another annotations here.
			}
		}
		
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


}
