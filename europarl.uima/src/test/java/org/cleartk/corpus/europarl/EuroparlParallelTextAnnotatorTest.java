package org.cleartk.corpus.europarl;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.uima.UIMAException;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.fit.util.CasIOUtil;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.cleartk.corpus.europarl.type.ParallelChunk;
import org.cleartk.util.cr.linereader.LineReaderXmiWriter;
import org.junit.Test;

public class EuroparlParallelTextAnnotatorTest {

	@Test
	public void givenAParallelTextWhenPuttingAnnotationsThenTheNumberOfChunksAreFine() throws ResourceInitializationException, UIMAException, IOException{
		File sampleDir = new File("resources/sample");
		File outputDir = new File("outputs/textWithAnnotations");
		if (outputDir.exists()){
			FileUtils.deleteDirectory(outputDir);
		}
		outputDir.mkdirs();
		
		SimplePipeline.runPipeline(ParallelFileCollectionReader.getReaderDescription(sampleDir, "en", "fr"), 
				ParalleDocumentTextReader.getDescription(), 
				EuroparlParallelTextAnnotator.getDescription(), 
				LineReaderXmiWriter.getDescription(outputDir));
		
		JCas aJCas = JCasFactory.createJCas();
		File aFile = new File(outputDir, "ep-00-01-17.txt.xmi");
		
		assertThat(aFile).exists();
		CasIOUtil.readJCas(aJCas, aFile);
		
		JCas enTextView = aJCas.getView(EuroparlParallelTextAnnotator.EN_TEXT_VIEW);
		
		Collection<ParallelChunk> sentences = JCasUtil.select(enTextView, ParallelChunk.class);
		assertThat(sentences).hasSize(1114 - 362);
		
	}
}
