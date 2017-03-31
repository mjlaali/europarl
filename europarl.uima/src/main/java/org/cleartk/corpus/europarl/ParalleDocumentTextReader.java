package org.cleartk.corpus.europarl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.io.IOUtils;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CASException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.api.metadata.type.DocumentMetaData;

public class ParalleDocumentTextReader extends JCasAnnotator_ImplBase{
	public static final String PARAM_ENCODING = "encodign";
	
	@ConfigurationParameter(name=PARAM_ENCODING, defaultValue="UTF-8")
	private String encoding;

	public static final String EN_VIEW = "enView";
	public static final String FR_VIEW = "frView";
	
	private String xml10pattern = "[^"
            + "\u0009\r\n"
            + "\u0020-\uD7FF"
            + "\uE000-\uFFFD"
            + "\ud800\udc00-\udbff\udfff"
            + "]";

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
			String baseUri = langUris[langUris.length - 1];
			for (int i = 0; i < VIEWES.length; i++){
				String uri = langUris[2 * i];
				String lang = langUris[2 * i + 1];
				InputStream is = new URI(uri).toURL().openStream();
				String content = IOUtils.toString(is, encoding).replace("&#x02BC;", "'").replace("&#x010D;", "c").replaceAll(xml10pattern, "");
				
			    JCas view = aJCas.createView(VIEWES[i]);
			    DocumentMetaData documentMetaData = DocumentMetaData.create(view.getCas());
			    documentMetaData.setDocumentTitle(new File(uri).getName());
			    documentMetaData.setDocumentId(new File(uri).getCanonicalPath());
			    documentMetaData.setDocumentUri(uri.toString());
			    view.setDocumentLanguage(lang);
			    documentMetaData.setCollectionId(baseUri);
			    documentMetaData.setDocumentBaseUri(baseUri);
			    
				view.setSofaDataString(content, "text/plain");
			}
		} catch (CASException | IOException | URISyntaxException e) {
			throw new AnalysisEngineProcessException(e);
		}
	}

}
