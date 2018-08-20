package config;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Classe qui se charge de lever les erreurs dans les fichiers de config.
 * 
 * @author Coyote
 */
public class ConfigErrorHandler implements ErrorHandler {

	@Override
	public void warning(SAXParseException exception) throws SAXException {
		
		System.err.println("WARNING : " + exception.getMessage());
	}

	@Override
	public void error(SAXParseException exception) throws SAXException {
		
		System.err.println("ERROR : " + exception.getMessage());
	}

	@Override
	public void fatalError(SAXParseException exception) throws SAXException {
		
		System.err.println("FATAL ERROR : " + exception.getMessage());
	}

}
