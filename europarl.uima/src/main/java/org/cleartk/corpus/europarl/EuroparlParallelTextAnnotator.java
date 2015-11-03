package org.cleartk.corpus.europarl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CASException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.cleartk.corpus.europarl.type.ParallelChunk;

public class EuroparlParallelTextAnnotator extends JCasAnnotator_ImplBase{
	public static final String EN_TEXT_VIEW = "enViewText";
	public static final String FR_TEXT_VIEW = "frViewText";

	public static AnalysisEngineDescription getDescription() throws ResourceInitializationException {
		return AnalysisEngineFactory.createEngineDescription(EuroparlParallelTextAnnotator.class);
	}
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		try {
			setAnnotations(aJCas, ParalleDocumentTextReader.EN_VIEW, EN_TEXT_VIEW);
			setAnnotations(aJCas, ParalleDocumentTextReader.FR_VIEW, FR_TEXT_VIEW);
		} catch (CASException e) {
			e.printStackTrace();
		}
	}

	private void setAnnotations(JCas aJCas, String orgViewName, String textViewName) throws CASException {
		JCas view = aJCas.getView(ParalleDocumentTextReader.EN_VIEW);
		JCas textView = aJCas.createView(EN_TEXT_VIEW);

		String text = view.getDocumentText();
		
		List<String> lines = new ArrayList<>(Arrays.asList(text.split("\n")));
		StringBuilder parallelChunksText = new StringBuilder();
		for (String line: lines){
			if (!line.startsWith("<")){
				parallelChunksText.append(line);
				parallelChunksText.append("\n");
			}
		}
		
		textView.setDocumentText(parallelChunksText.toString());
		
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


}
