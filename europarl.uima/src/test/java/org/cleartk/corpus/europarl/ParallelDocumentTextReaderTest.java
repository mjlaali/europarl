package org.cleartk.corpus.europarl;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.CASRuntimeException;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.jcas.JCas;
import org.junit.Before;
import org.junit.Test;

public class ParallelDocumentTextReaderTest {
	private JCas jCas;
	private File baseDir = new File("resources/sample/");
	private File enFile = new File(baseDir, "en/ep-00-01-17.txt");
	private File frFile = new File(baseDir, "fr/ep-00-01-17.txt");
	
	@Before
	public void setup() throws UIMAException, CASRuntimeException, IOException{
		jCas = JCasFactory.createJCas();
		
		JCas uriView = jCas.createView(ParallelFileCollectionReader.PARALLEL_URI_VIEW);
		uriView.setDocumentText(enFile.toURI().toString() + "\nen\n" + frFile.toURI().toString() + "\nfr\n" + baseDir.getCanonicalPath());
		
		AnalysisEngineDescription reader = ParalleDocumentTextReader.getDescription();
		
		SimplePipeline.runPipeline(jCas, reader);
	}

	@Test
	public void givenAJCasWithTwoUriWhenReadingThenTwoTextsOfUriAreSetInTwoView() throws UIMAException, IOException{
		assertThat(jCas.getView(ParalleDocumentTextReader.EN_VIEW).getDocumentText()).isEqualTo(FileUtils.readFileToString(enFile));
		assertThat(jCas.getView(ParalleDocumentTextReader.FR_VIEW).getDocumentText()).isEqualTo(FileUtils.readFileToString(frFile));
	}
	
	@Test
	public void whenReadingTextTheLanguageIsSet() throws CASException{
		assertThat(jCas.getView(ParalleDocumentTextReader.EN_VIEW).getDocumentLanguage()).isEqualTo("en");
		assertThat(jCas.getView(ParalleDocumentTextReader.FR_VIEW).getDocumentLanguage()).isEqualTo("fr");
	}
}
