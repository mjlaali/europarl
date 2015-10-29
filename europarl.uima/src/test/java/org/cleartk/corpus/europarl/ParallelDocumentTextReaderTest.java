package org.cleartk.corpus.europarl;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.jcas.JCas;
import org.junit.Test;

public class ParallelDocumentTextReaderTest {

	@Test
	public void givenAJCasWithTwoUriWhenReadingThenTwoTextsOfUriAreSetInTwoView() throws UIMAException, IOException{
		JCas jCas = JCasFactory.createJCas();
		File enFile = new File("resources/sample/en/ep-00-01-17.txt");
		File frFile = new File("resources/sample/fr/ep-00-01-17.txt");
		
		JCas uriView = jCas.createView(ParallelFileCollectionReader.PARALLEL_URI_VIEW);
		uriView.setDocumentText(enFile.toURI().toString() + "\n" + frFile.toURI().toString());
		
		AnalysisEngineDescription reader = ParalleDocumentTextReader.getDescription();
		
		SimplePipeline.runPipeline(jCas, reader);
		
		assertThat(jCas.getView(ParalleDocumentTextReader.EN_VIEW).getDocumentText()).isEqualTo(FileUtils.readFileToString(enFile));
		assertThat(jCas.getView(ParalleDocumentTextReader.FR_VIEW).getDocumentText()).isEqualTo(FileUtils.readFileToString(frFile));
	}
}
