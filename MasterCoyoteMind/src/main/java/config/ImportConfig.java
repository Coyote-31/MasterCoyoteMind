package config;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Classe qui charge les configurations depuis le fichier <i>configProperties.xml</i>,
 * vérifie la conformité avec le fichier <i>configProperties.dtd</i>
 * et le cas échéant recré le fichier xml.
 * 
 * @author Coyote
 */
public class ImportConfig {

	
	/**
	 * Détermine le nombre de cases du plateau de jeu.
	 */
	private int nbrDeCases;
	/**
	 * Détermine le nombre d'essais autorisés pour découvrir la suite adverse. {1..20}
	 */
	private int nbrEssais;
	/**
	 * Détermine le nombre de couleurs possibles, 
	 * c'est à dire ici les chiffres utilisables. 
	 * {4..10}
	 */
	private int nbrDeCouleurs;
	/**
	 * Définit si le jeu est en mode developpeur pour le déboggage.
	 * <ul>
	 * <li><code>true</code> : Mode deboggage actif</li>
	 * <li><code>false</code> : Mode normal</li>
	 * </ul>
	 */
	private boolean dev;
	/**
	 * Constante de nom d'attribut du fichier XML.
	 */
	private static final String VALUE = "value";
	
	
	/**
	 * Constructeur qui attribut les valeurs par défaut.
	 */
	public ImportConfig() {
		// Valeurs par défaut si le document XML est mal formaté.
		nbrDeCases = 4;
		nbrEssais = 10;
		nbrDeCouleurs = 6;
		dev = false;
	}

	/**
	 * Initialise la lecture du document XML et gère les erreurs possibles.
	 */
	public void init() {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		try {
			// Méthode qui permet d'activer la vérification du fichier.
			factory.setValidating(true);

			DocumentBuilder builder = factory.newDocumentBuilder();

			// Création de l'objet qui gère les erreurs.
			ErrorHandler errHandler = new ConfigErrorHandler();
			
			// Affectation de notre objet au document pour interception des erreurs éventuelles
			builder.setErrorHandler(errHandler);
			File fileXML = new File("./src/main/resources/configProperties.xml");
			
			Document xml = builder.parse(fileXML);
			Element root = xml.getDocumentElement();
			
			importProperties(root);
				
		} catch (SAXParseException e){
			e.printStackTrace();
			createFileXML();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			createFileXML();
		} catch (SAXException e) {
			e.printStackTrace();
			createFileXML();
		} catch (IOException e) {
			e.printStackTrace();
			createFileXML();
		}      
	}
	
	/**
	 * Importe les valeurs depuis le fichier XML.
	 */
	public void importProperties(Element root) {
		
		Element e;
		
		// Récupération du nombre de cases.
		e = (Element)root.getElementsByTagName("nbrdecases").item(0);
		nbrDeCases = Integer.parseInt(e.getAttribute(VALUE));
		
		// Récupération du nombre d'essais.
		e = (Element)root.getElementsByTagName("nbressais").item(0);
		nbrEssais = Integer.parseInt(e.getAttribute(VALUE));
		
		// Récupération du nombre de couleurs.
		e = (Element)root.getElementsByTagName("nbrdecouleurs").item(0);
		nbrDeCouleurs = Integer.parseInt(e.getAttribute(VALUE));
		
		// Récupération de l'attribut développeur pour debug.
		e = (Element)root.getElementsByTagName("dev").item(0);
		dev = Boolean.parseBoolean(e.getAttribute(VALUE));
	}
	
	/**
	 * Créé un fichier XML correct lié au DTD, avec des valeurs par défaut.
	 */
	public void createFileXML() {

		try {			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			// DocBuilder
			Document doc = docBuilder.newDocument();
		    
			// Root element : properties
			Element rootElement = doc.createElement("properties");
			doc.appendChild(rootElement);
			
			// Element : nbrdecases
			Element eNbrDeCases = doc.createElement("nbrdecases");
			rootElement.appendChild(eNbrDeCases);
			// Attribut de l'element nbrdecases
			eNbrDeCases.setAttribute(VALUE, "4");
			
			// Element : nbressais
			Element eNbrEssais = doc.createElement("nbressais");
			rootElement.appendChild(eNbrEssais);
			// Attribut de l'element nbressais
			eNbrEssais.setAttribute(VALUE, "10");
			
			// Element : nbrdecouleurs
			Element eNbrDeCouleurs = doc.createElement("nbrdecouleurs");
			rootElement.appendChild(eNbrDeCouleurs);
			// Attribut de l'element nbrdecouleurs
			eNbrDeCouleurs.setAttribute(VALUE, "6");
			
			// Element : dev
			Element eDev = doc.createElement("dev");
			rootElement.appendChild(eDev);
			// Attribut de l'element dev
			eDev.setAttribute(VALUE, "true");
			
			
			// Ecrit le contenu dans un fichier XML.
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "configProperties.dtd");

			
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("./src/main/resources/configProperties.xml"));

			// Output vers la console pour debug :
			if(dev) {
				result = new StreamResult(System.out);
			}
			
			transformer.transform(source, result);

			System.out.println("Fichier XML recréé avec des valeurs par défaut.");
			
			// Relance la phase d'initialisation
			init();

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}

	}

	public int getNbrDeCases() {
		
		return nbrDeCases;
	}
	
	public int getNbrEssais() {
		
		return nbrEssais;
	}
	
	public int getNbrDeCouleurs() {
		
		return nbrDeCouleurs;
	}
	
	public boolean isDev() {
		
		return dev;
	}
	
}
