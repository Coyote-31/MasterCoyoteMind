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
package main;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

import config.ImportConfig;
import jeu.JeuMasterMind;
import jeu.JeuRecherche;

/**
 * Classe principale de l'application.
 * <p>Ce logiciel : <b>MasterCoyoteMind™</b> est un jeu basé sur les mécaniques du MasterMind®.
 * Il a été crée dans le but de mettre en pratique les cours de Java
 * dispensés par l'école OpenClassroom.</p>
 * 
 * @author Coyote
 * @version 0.6.1
 */
public class Main {

	
	/**
	 * Détermine le <i>type</i> de jeu entre :
	 * <ul>
	 * <li>1 : Recherche</li>
	 * <li>2 : MasterMind</li>
	 * </ul>
	 */	
	private static int type;
	/**
	 * Détermine le <i>mode</i> de jeu entre :
	 * <ul>
	 * <li>1 : Challenger</li>
	 * <li>2 : Defenseur</li>
	 * <li>3 : Duel</li>
	 * </ul>
	 */
	private static int mode;
	/**
	 * Détermine le nombre de cases du plateau de jeu. {1..8}
	 */
	private static int nbrDeCases;
	/**
	 * Détermine le nombre d'essais autorisés pour découvrir la suite adverse. {1..20}
	 */
	private static int nbrEssais;
	/**
	 * Détermine le nombre de couleurs possibles, 
	 * c'est à dire ici les chiffres utilisables. 
	 * {4..10}
	 */
	private static int nbrDeCouleurs;
	/**
	 * Définit si le jeu est en mode developpeur pour le déboggage.
	 * <ul>
	 * <li><code>true</code> : Mode deboggage actif</li>
	 * <li><code>false</code> : Mode normal</li>
	 * </ul>
	 */
	private static boolean dev;
	/**
	 * Création de l'objet Scanner qui sert à lire les entrées clavier de l'utilisateur.
	 */
	private static Scanner scan = new Scanner(System.in, "UTF-8");

	
	/**
	 * Fonction d'entrée de l'application (Main).
	 * Demande à l'utilisateur de choisir :
	 * <ul>
	 * <li>Le type de jeu : <code>int jeu = {1,2}</code></li>
	 * <li>Le mode de jeu : <code>int mode = {1,2,3}</code></li>
	 * </ul>
	 * 
	 * @see Main#type
	 * @see Main#mode
	 * 
	 * @param args main args
	 */
	public static void main(String[] args) {

		importConfig();

		System.out.println("\nBienvenue dans le jeu :\n");
		System.out.println(" ************************");
		System.out.println(" * Master Coyote Mind ™ *");
		System.out.println(" ************************");
		
		if(dev) {
			System.out.println("Mode developpeur : ON");
		}

		do {
			type = typeSelection();
			mode = modeSelection();
			launcher(type);
		
		} while (retry());
		
		scan.close();
	}

	/**
	 * Importe les données de configuration du jeu
	 * <ul>
	 * <li>Nombre de cases du jeu : <code>int nbrDeCases</code></li>
	 * <li>Nombre d'essais autorisés : <code>int nbrEssais</code></li>
	 * <li>Mode developpeur : <code>bool dev</code></li>
	 * </ul>
	 * 
	 * @see Main#nbrDeCases
	 * @see Main#nbrEssais
	 * @see Main#dev
	 */
	public static void importConfig() {

		// Création de l'objet dédié au XML
		ImportConfig impConf = new ImportConfig();
		impConf.init();
		
		// Correspondance des attribus
		nbrDeCases = impConf.getNbrDeCases();
		nbrEssais = impConf.getNbrEssais();
		nbrDeCouleurs = impConf.getNbrDeCouleurs();
		dev = impConf.isDev();

	}

	/**
	 * Demande à l'utilisateur de sélectionner un <i>type</i> de jeu via une entrée clavier :
	 * <ul>
	 * <li>1 : Recherche</li>
	 * <li>2 : MasterMind</li>
	 * </ul>
	 * 
	 * @return Le numéro du type de jeu : <code>int</code>	 
	 * 
	 * @see Main#type
	 */
	public static int typeSelection() {

		int scanType = 0;
		boolean loop = true;

		do {
			System.out.println("\nChoisissez le type de jeu :");
			System.out.println("\t1 : Recherche");
			System.out.println("\t2 : MasterMind");

			try {
				scanType = scan.nextInt();		

				if(scanType == 1 || scanType == 2) {

					loop = false;

				} else {
					System.err.println("\n Erreur : Le chiffre attendu est 1 ou 2 !");
				}

			} catch (NoSuchElementException e) {
				System.err.println("\nErreur : Veuillez rentrer un chiffre !");

			} finally {
				scan.nextLine();
			}

		} while (loop);

		return scanType;
	}

	/**
	 * Demande à l'utilisateur de sélectionner un <i>mode</i> de jeu via une entrée clavier :
	 * <ul>
	 * <li>1 : Challenger</li>
	 * <li>2 : Defenseur</li>
	 * <li>3 : Duel</li>
	 * </ul>
	 * 
	 * @return Le numéro du mode de jeu : <code>int</code>
	 * 
	 * @see Main#mode
	 */
	public static int modeSelection() {

		int scanMode = 0;		
		boolean loop = true;

		do {
			System.out.println("\nChoisissez le mode de jeu :");
			System.out.println("\t1 : Challenger");
			System.out.println("\t2 : Défense");
			System.out.println("\t3 : Duel");

			try {						
				scanMode = scan.nextInt();

				if(scanMode == 1 || scanMode == 2 || scanMode == 3) {

					loop = false;
				} else {
					System.err.println("\nErreur : Le chiffre attendu est 1 , 2 ou 3 !");
				}
			} catch (NoSuchElementException e) {
				System.err.println("\nErreur : Veuillez rentrer un chiffre !");

			} finally {
				scan.nextLine();
			}

		} while (loop);

		return scanMode;

	}

	/**
	 * Lance le jeu en fonction du <i>type</i> choisit par l'utilisateur :
	 * <ul>
	 * <li>1 : Recherche</li>
	 * <li>2 : MasterMind</li>
	 * </ul>
	 * @see Main#type
	 * 
	 * @param type type du jeu
	 */
	public static void launcher(int type) {

		switch (type) {

		// Type Recherche
		case 1:

			JeuRecherche jeu1 = new JeuRecherche(nbrDeCases, nbrEssais, mode);
			jeu1.init();

			break;

			// Type MasterMind
		case 2:

			JeuMasterMind jeu2 = new JeuMasterMind(nbrDeCases, nbrEssais, mode, nbrDeCouleurs);
			jeu2.init();

			break;
		}
	}

	/**
	 * Demande à l'utilisateur de choisir de recommencer le jeu ou de quitter l'application.
	 * Et retourne un booléen pour continuer la boucle ou en sortir.
	 * 
	 * @return <code>boolean</code>
	 */
	public static boolean retry() {
		
		boolean retry = false;
		boolean loop = true;
		String scanChar;
		
		do {
			System.out.println("\n Voulez-vous commencer un nouveau jeu ? o/n");
			System.out.println("   o = Ecran de sélection || n = Quitter");
			
			try {
				scanChar = scan.next();
				scanChar = scanChar.toLowerCase(Locale.FRANCE);
				
				if (scanChar.charAt(0) == 'o') {
					retry = true;
					loop = false;
					
				} else if (scanChar.charAt(0) == 'n') {
					loop = false;
					
				} else {
					System.err.println("\n Veuillez rentrer 'o' pour oui ou 'n' pour non !");
				}
				
			} catch (Exception e) {
				System.err.println("\n Veuillez rentrer 'o' pour oui ou 'n' pour non !");
				
			} finally {
				scan.nextLine();
			}
			
		} while (loop);
		
		return retry;
	}
	
	/**
	 * Permet de faire suivre l'objet Scanner à travers les différentes classes.
	 * 
	 * @return L'objet <code>Scanner</code>
	 */
	public static Scanner getScanner() {
		return scan;
	}
	
	/**
	 * Récupère la variable dev du mode de deboggage developpeur.
	 * <ul>
	 * <li><code>true</code> : Mode deboggage actif</li>
	 * <li><code>false</code> : Mode normal</li>
	 * </ul>
	 * 
	 * @return <code>boolean</code>
	 */
	public static boolean isDev() {
		
		return dev;
	}

}

