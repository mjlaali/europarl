package org.cleartk.corpus.europarl;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.uima.UimaContext;
import org.apache.uima.cas.CASException;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;

import de.tudarmstadt.ukp.dkpro.core.api.metadata.type.DocumentMetaData;

public class ParallelFileCollectionReader extends JCasCollectionReader_ImplBase{
	public static final String PARALLEL_URI_VIEW = "parallelUriView";
	public static final String PARAM_DIR = "dir";
	public static final String PARAM_LANGS = "langs";

	@ConfigurationParameter(
			name = PARAM_DIR, 
			mandatory = true)
	private File dir;
	
	@ConfigurationParameter(
			name = PARAM_LANGS, 
			mandatory = true)
	private String[] langs;
	private Iterator<Entry<String, List<File>>> iterParallelFiles;


	public static CollectionReaderDescription getReaderDescription(File dir, String first, String second) throws ResourceInitializationException {
		return CollectionReaderFactory.createReaderDescription(ParallelFileCollectionReader.class, 
				PARAM_DIR, dir, 
				PARAM_LANGS, new String[]{first, second});
	}

	@Override
	public void initialize(UimaContext context) throws ResourceInitializationException {
		super.initialize(context);
		try {
			dir = dir.getCanonicalFile();
		} catch (IOException e) {
			throw new ResourceInitializationException(e);
		}
		File[] langDirs = dir.listFiles();
		if (langDirs == null || langDirs.length != 2)
			throw new ResourceInitializationException(new RuntimeException(
					String.format("Only two languages are supported! Check the directory %s, it contains %s", dir.getAbsolutePath(), langDirs == null ? "nothing" : Arrays.toString(langDirs))));

		Map<String, List<File>> parallelFiles = new HashMap<>();
		File enDir = langDirs[0];
		Set<String> enFiles = new HashSet<>();
		for (File enFile: enDir.listFiles()){
			ArrayList<File> list = new ArrayList<>();
			parallelFiles.put(enFile.getName(), list);
			list.add(enFile);
			enFiles.add(enFile.getName());
		}

		File frDir = langDirs[1];
		for (File frFile: frDir.listFiles()){
			List<File> list = parallelFiles.get(frFile.getName());
			if (list == null){
				System.err.printf("The file <%s> only availabe in the French directory <%s>. \n", frFile.getName(), frDir.getAbsolutePath());
			} else {
				list.add(frFile);
				enFiles.remove(frFile.getName());
			}
		}

		for (String enFile: enFiles){
			List<File> toRemove = parallelFiles.remove(enFile);
			System.err.printf("The file <%s> only availabe in the English directory <%s>. \n", toRemove.get(0).getName(), enDir.getAbsolutePath());
		}
		
		for (Entry<String, List<File>> aParalleFiles: parallelFiles.entrySet()){
			aParalleFiles.setValue(sort(aParalleFiles.getValue()));
			
		}

		iterParallelFiles = parallelFiles.entrySet().iterator();
	}


	private List<File> sort(List<File> files) {
		List<File> res = new ArrayList<>();
		for (String lang: langs){
			Iterator<File> iterator = files.iterator();
			while (iterator.hasNext()){
				File file = iterator.next();
				if (file.getAbsolutePath().contains(String.format("/%s/", lang))){
					iterator.remove();
					res.add(file);
				}
			}
		}
		
		return res;
	}

	@Override
	public boolean hasNext() throws IOException, CollectionException {
		return iterParallelFiles.hasNext();
	}

	@Override
	public Progress[] getProgress() {
		return null;
	}

	@Override
	public void getNext(JCas jCas) throws IOException, CollectionException {
		JCas view;
		try {
			view = jCas.createView(PARALLEL_URI_VIEW);
		} catch (CASException e) {
			throw new CollectionException(e);
		}
		
		Entry<String, List<File>> parallelUris = iterParallelFiles.next();
		File enFile = parallelUris.getValue().get(0).getCanonicalFile();
		URI enUri = enFile.toURI();
		String strEnUri = enUri.toString();
		String strfrUri = parallelUris.getValue().get(1).getCanonicalFile().toURI().toString();
		
		DocumentMetaData documentMetaData = DocumentMetaData.create(jCas);
		documentMetaData.setDocumentUri(strEnUri);
		documentMetaData.setDocumentId(enFile.getName());
		DocumentMetaData.copy(jCas, view);
		
		view.setDocumentText(String.format("%s\n%s\n%s\n%s\n%s", strEnUri, langs[0], strfrUri, langs[1], dir.toURI().toString()));
	}


}
