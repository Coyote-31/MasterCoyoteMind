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
package mode.mastermind;

import java.security.SecureRandom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mode.AbstractModeChallenger;

/**
 * Gère les détails d'une partie de type <i>MasterMind</i> en mode <i>Challenger</i>.
 * 
 * @author Coyote
 *
 */
public class MasterMindChallenger extends AbstractModeChallenger {
	
	/**
	 * Détermine le nombre de couleurs possibles, 
	 * c'est à dire ici les chiffres utilisables. 
	 * De 4 à 10.
	 */
	private int nbrDeCouleurs;
	/**
	 * Permet d'éviter un double test d'un chiffre.
	 * 
	 * @see MasterMindChallenger#testCombi()
	 */
	private boolean[] tableauTestCombi;
	/**
	 * Création de l'objet Logger permettant la gestion des logs de l'application.
	 */
	private static final Logger LOG = LogManager.getLogger();
	/**
	 * String de constante pour les erreurs d'entrées clavier de l'utilisateur.
	 */
	private static final String ERRORUSER = "Erreur entrée clavier utilisateur";
	
	
	/**
	 * Constructeur de <i>MasterMindChallenger</i>.
	 * 
	 * @param nbrEssais <code>:int</code> Nombre d'essais de la partie.
	 * @param nbrDeCases <code>:int</code> Nombre de cases du plateau de jeu.
	 * @param nbrDeCouleurs <code>:int</code> Nombre de couleurs differentes.
	 */
	public MasterMindChallenger(int nbrDeCases, int nbrEssais, int nbrDeCouleurs) {
		
		super(nbrDeCases, nbrEssais);
		
		this.nbrDeCouleurs = nbrDeCouleurs;
		tableauTestCombi = new boolean[nbrDeCases];
	}

	/**
	 * Lance l'initialisation de la <i>partie</i>.
	 * En créant les combinaisons à trouver et lance la fonction <code>partie()</code>.
	 * 
	 * @see AbstractModeChallenger#partie()
	 */
	@Override
	public void init() {

		// Création de la liste de combinaison du jeu.
		
		SecureRandom rand = new SecureRandom();
		
		for (int i = 0; i < nbrDeCases; i++) {
			tableauCombinaison[i] = rand.nextInt(nbrDeCouleurs);
		}
		
		partie();
	}
	
	/**
	 * Récupération dans un tableau de la tentative du joueur.
	 * 
	 * @see AbstractModeChallenger#tableauTentative
	 */
	@Override
	public void tentative() {

		// Récupération de la réponse :

		String scanTentative = null;

		boolean loopTour = true;

		do {
			System.out.println("Veuillez rentrer " + nbrDeCases + " chiffres de 0 à " + (nbrDeCouleurs-1) + " :");

			try {
				scanTentative = scan.next();

				// Vérification du nombre de charactères
				if (scanTentative.length() > nbrDeCases) {
					throw new Exception();
				}

				for (int i = 0; i < tableauTentative.length; i++) {

					//convertion char => chiffre int
					tableauTentative[i] = Character.getNumericValue(scanTentative.charAt(i));

					// Vérification que se sont des chiffres de 0 à nbrDeCouleurs-1
					if (tableauTentative[i] < 0 || tableauTentative[i] > (nbrDeCouleurs-1)) {
						throw new Exception();
					}
				}
					
				loopTour = false;
				
			} catch (Exception e) {
				System.err.println("\nErreur : Veuillez rentrer un nombre de "
						+ nbrDeCases + " chiffres de 0 à "+ (nbrDeCouleurs-1) +" !\n");	
				LOG.error(ERRORUSER, e);

			} finally {
				scan.nextLine();
			}

		} while (loopTour);
	}
	
	/**
	 * Compare la tentative avec la combinaison et affiche le résultat.
	 * 
	 * @see MasterMindChallenger#printReponse(int, int)
	 */
	@Override
	public void testCombi() {
		
		int present = 0;
		int bienPlace = 0;
		
		reiniTableauTestCombi();
		
		for (int i = 0; i < tableauCombinaison.length; i++) {
			
			if (tableauCombinaison[i] == tableauTentative[i]) {
				
				tableauVictoire[i] = true;
				tableauTestCombi[i] = false;
				bienPlace ++;
				
			} else {
				
				tableauVictoire[i] = false;
				
				for (int j = 0; j < tableauTentative.length; j++) {

					// Evite les doublons avec les biens placés et test la présence dans la tentative.
					if (i != j && 
						tableauCombinaison[i] == tableauTentative[j] && 
						tableauTestCombi[j] &&
						tableauCombinaison[j] != tableauTentative[j]) {

						tableauTestCombi[j] = false;
						present ++;
						break;
					}
				}
			}
		}
		
		printReponse(present, bienPlace);
		
		// Test pour savoir si tout le tableauVictoire est à true.
		victoire = true;

		for (int i = 0; i < tableauVictoire.length; i++) {
			if (!tableauVictoire[i]) {
				victoire = false;
			}
		}
	}
	
	/**
	 * Reinitialise le tableauTestCombi
	 * 
	 * @see MasterMindChallenger#tableauTestCombi 
	 */
	public void reiniTableauTestCombi() {
		
		for (int i = 0; i < tableauCombinaison.length; i++) {
			tableauTestCombi[i] = true;
		}
	}
	
	/**
	 * Formate correctement la réponse du testCombi()
	 * 
	 * @param present <code>:int</code> Nombre de chiffres présents
	 * @param bienPlace <code>:int</code> Nombre de chiffres correctement placés
	 */
	public void printReponse(int present, int bienPlace) {
		
		String strPresent = "";
		String strBienPlace = "";
		String strSeparateur = "";
		
		if (present == 1) {
			strPresent = present + " présent";
		} else if (present > 1) {
			strPresent = present + " présents";
		}
		
		if (bienPlace == 1) {
			strBienPlace = bienPlace + " bien placé";
		} else if (bienPlace > 1) {
			strBienPlace = bienPlace + " bien placés";
		}
		
		if (present > 0 && bienPlace > 0) {
			strSeparateur = ", ";
		}
		
		if (present == 0 && bienPlace == 0) {
			strPresent = present + " présent";
		}
		
		System.out.println("-> Réponse : " + strPresent + strSeparateur + strBienPlace + ".");
	}
	
}
