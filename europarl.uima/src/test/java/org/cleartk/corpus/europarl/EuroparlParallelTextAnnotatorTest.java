package org.cleartk.corpus.europarl;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.uima.UIMAException;
import org.apache.uima.cas.CAS;
import org.apache.uima.fit.factory.AggregateBuilder;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.fit.util.CasIOUtil;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.cleartk.corpus.europarl.type.ParallelChunk;
import org.junit.Test;

import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.Constituent;
import de.tudarmstadt.ukp.dkpro.core.berkeleyparser.BerkeleyParser;
import de.tudarmstadt.ukp.dkpro.core.io.xmi.XmiWriter;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpSegmenter;

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
				createEngineDescription(XmiWriter.class, XmiWriter.PARAM_TARGET_LOCATION, outputDir));
		
		JCas aJCas = JCasFactory.createJCas();
		File aFile = new File(outputDir, "ep-00-01-17.txt.xmi");
		
		assertThat(aFile).exists();
		CasIOUtil.readJCas(aJCas, aFile);
		
		JCas enTextView = aJCas.getView(EuroparlParallelTextAnnotator.EN_TEXT_VIEW);
		assertThat(enTextView.getCas().getDocumentLanguage()).isEqualTo("en");
		JCas frTextView = aJCas.getView(EuroparlParallelTextAnnotator.FR_TEXT_VIEW);
		assertThat(frTextView.getCas().getDocumentLanguage()).isEqualTo("fr");
		
		Collection<ParallelChunk> sentences = JCasUtil.select(enTextView, ParallelChunk.class);
		assertThat(sentences).hasSize(1114 - 362);
		
	}
	
	@Test
	public void whenReadingTextThenTheTextCanBeParsedByDKProBerkeleyParser() throws ResourceInitializationException, UIMAException, IOException{
		System.out.println(
				"EuroparlParallelTextAnnotatorTest.whenReadingTextThenTheTextCanBeParsedByDKProBerkeleyParser()");
		File sampleDir = new File("resources/small-sample");
		File outputDir = new File("outputs/processedText");
		if (outputDir.exists()){
			FileUtils.deleteDirectory(outputDir);
		}
		outputDir.mkdirs();
		
		AggregateBuilder aggregateBuilder = new AggregateBuilder();
		aggregateBuilder.add(createEngineDescription(OpenNlpSegmenter.class), CAS.NAME_DEFAULT_SOFA, EuroparlParallelTextAnnotator.EN_TEXT_VIEW);
		aggregateBuilder.add(createEngineDescription(OpenNlpPosTagger.class), CAS.NAME_DEFAULT_SOFA, EuroparlParallelTextAnnotator.EN_TEXT_VIEW);
		aggregateBuilder.add(createEngineDescription(BerkeleyParser.class), CAS.NAME_DEFAULT_SOFA, EuroparlParallelTextAnnotator.EN_TEXT_VIEW);
		
		runPipeline(ParallelFileCollectionReader.getReaderDescription(sampleDir, "en", "fr"), 
				ParalleDocumentTextReader.getDescription(), 
				EuroparlParallelTextAnnotator.getDescription(), 
        		aggregateBuilder.createAggregateDescription(),
                createEngineDescription(XmiWriter.class, XmiWriter.PARAM_TARGET_LOCATION, "outputs"));
		
		JCas aJCas = JCasFactory.createJCas();
		File aFile = new File(outputDir, new File(sampleDir, "en").listFiles()[0].getName() + ".xmi");
		
		assertThat(aFile).exists();
		CasIOUtil.readJCas(aJCas, aFile);
		assertThat(JCasUtil.select(aJCas, Constituent.class).size()).isGreaterThan(0);
		
	}
}
