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
package mode.recherche;

import java.security.SecureRandom;

import main.Main;
import mode.AbstractMode;
import mode.AbstractModeChallenger;

/**
 * Classe qui gère une partie de type <i>Recherche</i> en mode <i>Challenger</i> 
 * et se charge du déroulement de la partie dans les détails.
 * 
 * @see AbstractMode
 * @see AbstractModeChallenger
 * 
 * @author Coyote
 */
public class RechercheChallenger extends AbstractModeChallenger {

	
	/**
	 * Constructeur qui fait suivre les valeurs de configuration.
	 * 
	 * @param nbrDeCases : <code>int</code>
	 * @param nbrEssais : <code>int</code>
	 * 
	 * @see Main#nbrDeCases
	 * @see Main#nbrEssais
	 * @see AbstractMode#AbstractMode(int, int)
	 * @see AbstractModeChallenger#AbstractModeChallenger(int, int)
	 */
	public RechercheChallenger(int nbrDeCases, int nbrEssais) {
		
		super(nbrDeCases, nbrEssais);
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
			tableauCombinaison[i] = rand.nextInt(10);
		}

		partie();
	}

	/**
	 * Récupération dans un tableau de la tentative du joueur.
	 * 
	 * @see AbstractModeChallenger#tableauTentative
	 */
	public void tentative() {

		// Récupération de la réponse :

		String scanTentative = null;

		boolean loopTour = true;

		do {
			System.out.println("Veuillez rentrer " + nbrDeCases + " chiffres de 0 à 9 :");

			try {
				scanTentative = scan.next();

				// Vérification du nombre de charactères
				if (scanTentative.length() > nbrDeCases) {
					throw new Exception();
				}

				for (int i = 0; i < tableauTentative.length; i++) {

					//convertion char => chiffre int
					tableauTentative[i] = Character.getNumericValue(scanTentative.charAt(i));

					// Vérification que se sont des chiffres de 0 à 9
					for (int j = 0; j < tableauTentative.length; j++) {
						if (tableauTentative[i] < 0 || tableauTentative[i] > 9) {
							throw new Exception();
						}
					}
				}
				loopTour = false;
				
			} catch (Exception e) {
				System.err.println("\nErreur : Veuillez rentrer un nombre de " + nbrDeCases + " chiffres de 0 à 9 !\n");				

			} finally {
				scan.nextLine();
			}

		} while (loopTour);
	}
	
	/**
	 * Compare la tentative avec la combinaison et affiche le résultat.
	 */
	public void testCombi() {

		String resultat = "";

		for (int i = 0; i < tableauTentative.length; i++) {

			if (tableauTentative[i] == tableauCombinaison[i]) {

				tableauVictoire[i] = true;
				resultat += "=";


			} else if (tableauTentative[i] < tableauCombinaison[i]) {

				tableauVictoire[i] = false;
				resultat += "+";		

			} else if (tableauTentative[i] > tableauCombinaison[i]) {

				tableauVictoire[i] = false;
				resultat += "-";					
			}
		}

		System.out.println(resultat);

		// Test pour savoir si tout le tableauVictoire est à true.
		victoire = true;

		for (int i = 0; i < tableauVictoire.length; i++) {
			if (!tableauVictoire[i]) {
				victoire = false;
			}
		}
	}

}
