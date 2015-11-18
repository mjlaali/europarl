package org.cleartk.corpus.europarl;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.uima.UIMAException;
import org.apache.uima.cas.CAS;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.jcas.JCas;
import org.junit.Test;

import de.tudarmstadt.ukp.dkpro.core.io.xmi.XmiWriter;

public class ParallelFileCollectionReaderTest {
	
	//for lists
	public static <T, U> List<U> convertList(Collection<T> from, Function<T, U> func){
	    return from.stream().map(func).collect(Collectors.toList());
	}
	
	public void givenTwoParallelFilesWhenOrderingIsENFRThenURIAreOrderedENFR() throws IOException, UIMAException{
		File sampleDir = new File("resources/sample");
		String enFileName = convertList(Arrays.asList(new File(sampleDir, "en").listFiles())
				, f -> f.getAbsolutePath()).iterator().next();
		String frFileName = convertList(Arrays.asList(new File(sampleDir, "fr").listFiles())
				, f -> f.getAbsolutePath()).iterator().next();

		CollectionReaderDescription readerDesc = ParallelFileCollectionReader.getReaderDescription(sampleDir, "en", "fr");
		CollectionReader reader = CollectionReaderFactory.createReader(readerDesc);
		JCas aJCas = JCasFactory.createJCas();
		CAS cas = aJCas.getCas();
		
		reader.getNext(cas);
		JCas uriView = cas.getJCas().getView(ParallelFileCollectionReader.PARALLEL_URI_VIEW);
		assertThat(uriView.getDocumentText()).isEqualTo(String.format("%s\n%s", 
				new File(enFileName).toURI().toString(), new File(frFileName).toURI().toString()));
	}

	@Test
	public void givenTwoParallelFilesWhenOrderingIsFRENThenURIAreOrderedFREN() throws IOException, UIMAException{
		File sampleDir = new File("resources/small-sample");
		String enFileName = convertList(Arrays.asList(new File(sampleDir, "en").listFiles())
				, f -> f.getAbsolutePath()).iterator().next();
		String frFileName = convertList(Arrays.asList(new File(sampleDir, "fr").listFiles())
				, f -> f.getAbsolutePath()).iterator().next();

		CollectionReaderDescription readerDesc = ParallelFileCollectionReader.getReaderDescription(sampleDir, "fr", "en");
		CollectionReader reader = CollectionReaderFactory.createReader(readerDesc);
		JCas aJCas = JCasFactory.createJCas();
		CAS cas = aJCas.getCas();
		
		reader.getNext(cas);
		JCas uriView = cas.getJCas().getView(ParallelFileCollectionReader.PARALLEL_URI_VIEW);
		assertThat(uriView.getDocumentText()).isEqualTo(String.format("%s\nfr\n%s\nen\n%s", 
				new File(frFileName).toURI().toString(), new File(enFileName).toURI().toString()
				, sampleDir.getCanonicalFile().toURI().toString()));
	}

	@Test
	public void givenTheDirectoryOfEuroparlWhenReadingFromItDocumentWithUrlsWithParalleDocumentsAreGenerated() throws IOException, UIMAException, URISyntaxException{
		File sampleDir = new File("resources/sample");
		Set<String> enFileNames = new HashSet<String>(convertList(Arrays.asList(new File(sampleDir, "en").listFiles()), f -> f.getAbsolutePath()));
		Set<String> frFileNames = new HashSet<String>(convertList(Arrays.asList(new File(sampleDir, "fr").listFiles()), f -> f.getAbsolutePath()));

		CollectionReaderDescription readerDesc = ParallelFileCollectionReader.getReaderDescription(sampleDir, "en", "fr");
		CollectionReader reader = CollectionReaderFactory.createReader(readerDesc);

		JCas aJCas = JCasFactory.createJCas();
		CAS cas = aJCas.getCas();
		while (reader.hasNext()){
			reader.getNext(cas);
			JCas uriView = cas.getJCas().getView(ParallelFileCollectionReader.PARALLEL_URI_VIEW);

			String[] langsUri = uriView.getDocumentText().split("\n");
			for (int i = 0; i < langsUri.length/2; i++){
				String uri = langsUri[2 * i];
				File aFile = new File(new URI(uri));
				if (aFile.getAbsolutePath().contains("/en/"))
					assertThat(enFileNames.remove(aFile.getAbsolutePath())).isTrue();
				else 
					assertThat(frFileNames.remove(aFile.getAbsolutePath())).isTrue();
			}
			
			cas.reset();
		}
		assertThat(enFileNames).isEmpty();
		assertThat(frFileNames).isEmpty();
	}
	
	@Test
	public void givenAJCasWithParallelTextWhenSavingItThenTheFileNameIsAutomaticallyDetected() throws UIMAException, IOException{
		File sampleDir = new File("resources/sample");
		Set<String> enFileNames = new HashSet<String>(convertList(Arrays.asList(new File(sampleDir, "en").listFiles()), f -> f.getAbsolutePath()));
//		Set<String> frFileNames = new HashSet<String>(convertList(Arrays.asList(new File(sampleDir, "fr").listFiles()), f -> f.getAbsolutePath()));

		CollectionReaderDescription readerDesc = ParallelFileCollectionReader.getReaderDescription(sampleDir, "en", "fr");
		
		
		File outDir = new File("outputs/saving");
		if (outDir.exists())
			FileUtils.deleteDirectory(outDir);
		outDir.mkdirs();
		
		SimplePipeline.runPipeline(readerDesc, createEngineDescription(XmiWriter.class, XmiWriter.PARAM_TARGET_LOCATION, outDir));
		assertThat(FileUtils.listFiles(outDir, new String[]{"xmi"}, true)).hasSize(enFileNames.size());
		
	}
}