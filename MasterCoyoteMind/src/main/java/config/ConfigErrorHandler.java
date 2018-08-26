/*******************************************************************************
 * Copyright (C) 2018 Richard ANDRIAN
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
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
