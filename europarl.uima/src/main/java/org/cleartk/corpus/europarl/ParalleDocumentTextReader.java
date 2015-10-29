package org.cleartk.corpus.europarl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CASException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import com.google.common.io.CharStreams;

public class ParalleDocumentTextReader extends JCasAnnotator_ImplBase{

	public static final String EN_VIEW = "enView";
	public static final String FR_VIEW = "frView";

	private static final String[] VIEWES = new String[]{EN_VIEW, FR_VIEW}; 
	public static AnalysisEngineDescription getDescription() throws ResourceInitializationException {
		return AnalysisEngineFactory.createEngineDescription(ParalleDocumentTextReader.class);
	}

	@Override
	public void initialize(UimaContext context) throws ResourceInitializationException {
		super.initialize(context);
		
	}
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		try {
			JCas uriView = aJCas.getView(ParallelFileCollectionReader.PARALLEL_URI_VIEW);
			String[] langUris = uriView.getDocumentText().split("\n");
			for (int i = 0; i < VIEWES.length; i++){
				String uri = langUris[i];
				String content = CharStreams.toString(new InputStreamReader(new URI(uri).toURL().openStream()));
			    aJCas.createView(VIEWES[i]).setSofaDataString(content, "text/plain");
			}
		} catch (CASException | IOException | URISyntaxException e) {
			throw new AnalysisEngineProcessException(e);
		}
	}

}
