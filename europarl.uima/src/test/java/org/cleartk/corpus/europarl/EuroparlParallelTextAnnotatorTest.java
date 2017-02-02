package org.cleartk.corpus.europarl;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
import org.cleartk.corpus.europarl.type.Speaker;
import org.junit.Test;

import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.Constituent;
import de.tudarmstadt.ukp.dkpro.core.berkeleyparser.BerkeleyParser;
import de.tudarmstadt.ukp.dkpro.core.io.xmi.XmiWriter;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpSegmenter;

public class EuroparlParallelTextAnnotatorTest {

	@Test
	public void givenAParallelTextWhenPuttingAnnotationsThenTheNumberOfChunksAreFine() throws ResourceInitializationException, UIMAException, IOException{
		File outputDir = runOnSample();
		
		JCas aJCas = loagJCas00_01_17(outputDir);
		
		JCas enTextView = aJCas.getView(EuroparlParallelTextAnnotator.EN_TEXT_VIEW);
		assertThat(enTextView.getCas().getDocumentLanguage()).isEqualTo("en");
		JCas frTextView = aJCas.getView(EuroparlParallelTextAnnotator.FR_TEXT_VIEW);
		assertThat(frTextView.getCas().getDocumentLanguage()).isEqualTo("fr");
		
		Collection<ParallelChunk> sentences = JCasUtil.select(enTextView, ParallelChunk.class);
		assertThat(sentences).hasSize(1114 - 362);
		
	}
	
	@Test
	public void givenAParallelTextWhenPuttingAnnotationsThenTheFirstSpeakerAnnotationsIsCorrect() throws ResourceInitializationException, UIMAException, IOException{
		File outputDir = runOnSample();
		
		JCas aJCas = loagJCas00_01_17(outputDir);
		
		JCas enTextView = aJCas.getView(EuroparlParallelTextAnnotator.EN_TEXT_VIEW);
		List<Speaker> speakers = new ArrayList<>(JCasUtil.select(enTextView, Speaker.class));
		assertThat(speakers).hasSize(92 - 5);	//five speakers did not speak!
		assertThat(speakers.get(0).getBegin()).isEqualTo(26);
		assertThat(speakers.get(0).getName()).isEqualTo("President");
		assertThat(speakers.get(speakers.size() - 1).getEnd()).isEqualTo(enTextView.getDocumentText().length());
		
	}

	private JCas loagJCas00_01_17(File outputDir) throws UIMAException, IOException {
		JCas aJCas = JCasFactory.createJCas();
		File aFile = new File(outputDir, "ep-00-01-17.txt.xmi");
		
		assertThat(aFile).exists();
		CasIOUtil.readJCas(aJCas, aFile);
		return aJCas;
	}

	private File runOnSample() throws IOException, UIMAException, ResourceInitializationException {
		File sampleDir = new File("resources/sample");
		File outputDir = new File("outputs/textWithAnnotations");
		if (outputDir.exists()){
			FileUtils.deleteDirectory(outputDir);
		}
		outputDir.mkdirs();
		
		SimplePipeline.runPipeline(ParallelFileCollectionReader.getReaderDescription(sampleDir, "en", "fr"), 
				ParalleDocumentTextReader.getDescription(), 
				EuroparlParallelTextAnnotator.getDescription(), 
				createEngineDescription(XmiWriter.class, 
						XmiWriter.PARAM_TARGET_LOCATION, outputDir,
						XmiWriter.PARAM_OVERWRITE, true));
		return outputDir;
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
                createEngineDescription(XmiWriter.class, XmiWriter.PARAM_TARGET_LOCATION, outputDir.getAbsolutePath()));
		
		JCas aJCas = JCasFactory.createJCas();
		File aFile = new File(outputDir, new File(sampleDir, "en").listFiles()[0].getName() + ".xmi");
		
		assertThat(aFile).exists();
		CasIOUtil.readJCas(aJCas, aFile);
		assertThat(JCasUtil.select(aJCas.getView(EuroparlParallelTextAnnotator.EN_TEXT_VIEW), Constituent.class).size()).isGreaterThan(0);
		
	}
	
	
	
	@Test
	public void whenReadingTextsThenParallelChunksOfSourceAreConnectedToTargetAndViceVersa() throws ResourceInitializationException, UIMAException, IOException{
		File outputDir = runOnSample();
		
		JCas aJCas = JCasFactory.createJCas();
		File aFile = new File(outputDir, "ep-00-01-17.txt.xmi");
		
		assertThat(aFile).exists();
		CasIOUtil.readJCas(aJCas, aFile);
		
		Collection<ParallelChunk> enChunks = JCasUtil.select(aJCas.getView(EuroparlParallelTextAnnotator.EN_TEXT_VIEW), ParallelChunk.class);
		Collection<ParallelChunk> frChunks = JCasUtil.select(aJCas.getView(EuroparlParallelTextAnnotator.FR_TEXT_VIEW), ParallelChunk.class);
		
		for (ParallelChunk enChunk: enChunks){
			assertThat(enChunk.getDocOffset()).isEqualTo(enChunk.getTranslation().getDocOffset());
		}
		
		for (ParallelChunk frChunk: frChunks){
			assertThat(frChunk.getDocOffset()).isEqualTo(frChunk.getTranslation().getDocOffset());
		}

	}
}
