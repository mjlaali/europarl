package org.cleartk.corpus.europarl;

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

import org.apache.uima.UIMAException;
import org.apache.uima.cas.CAS;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.jcas.JCas;
import org.cleartk.util.cr.linereader.LineReaderXmiWriter;
import org.junit.Test;

public class ParallelFileCollectionReaderTest {
	
	//for lists
	public static <T, U> List<U> convertList(Collection<T> from, Function<T, U> func){
	    return from.stream().map(func).collect(Collectors.toList());
	}
	
	@Test
	public void givenTheDirectoryOfEuroparlWhenReadingFromItDocumentWithUrlsWithParalleDocumentsAreGenerated() throws IOException, UIMAException, URISyntaxException{
		File sampleDir = new File("resources/sample");
		Set<String> enFileNames = new HashSet<String>(convertList(Arrays.asList(new File(sampleDir, "en").listFiles()), f -> f.getAbsolutePath()));
		Set<String> frFileNames = new HashSet<String>(convertList(Arrays.asList(new File(sampleDir, "fr").listFiles()), f -> f.getAbsolutePath()));

		CollectionReaderDescription readerDesc = ParallelFileCollectionReader.getReaderDescription(sampleDir);
		CollectionReader reader = CollectionReaderFactory.createReader(readerDesc);

		JCas aJCas = JCasFactory.createJCas();
		CAS cas = aJCas.getCas();
		while (reader.hasNext()){
			reader.getNext(cas);
			JCas uriView = cas.getJCas().getView(ParallelFileCollectionReader.PARALLEL_URI_VIEW);

			String[] langsUri = uriView.getDocumentText().split("\n");
			for (String uri: langsUri){
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

		CollectionReaderDescription readerDesc = ParallelFileCollectionReader.getReaderDescription(sampleDir);
		
		
		File outDir = new File("outputs");
		SimplePipeline.runPipeline(readerDesc, LineReaderXmiWriter.getDescription(outDir));
		
		assertThat(outDir.listFiles()).hasSize(enFileNames.size());
		
	}
}