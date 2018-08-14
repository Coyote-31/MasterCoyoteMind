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
package jeu;

import main.Main;
import mode.recherche.RechercheChallenger;
import mode.recherche.RechercheDefenseur;
import mode.recherche.RechercheDuel;

/**
 * Cette classe gère une partie de type <i>Recherche</i>.
 * Elle s'occupe de créer l'objet <code>modeDeJeu</code> en fonction du <i>mode</i> de jeu correspondant.
 * 
 * @see AbstractJeu
 * @see AbstractJeu#mode
 * 
 * @author Coyote
 */
public class JeuRecherche extends AbstractJeu {

	
	/**
	 * Constructeur qui fait suivre les valeurs de configuration.
	 * 
	 * @param nbrDeCases : <code>int</code>
	 * @param nbrEssais : <code>int</code>
	 * @param mode : <code>int {1,2,3}</code>
	 * 
	 * @see Main#nbrDeCases
	 * @see Main#nbrEssais
	 * @see Main#mode
	 */
	public JeuRecherche(int nbrDeCases, int nbrEssais, int mode) {
			
			super(nbrDeCases, nbrEssais, mode);
	}

	/**
	 * Lance l'initialisation du jeu de type <i>Recherche</i> en créant l'objet <code>modeDeJeu</code>
	 * qui correspond au <i>mode</i> de jeu sélectionné.
	 * 
	 * @see AbstractJeu#modeDeJeu
	 * @see AbstractJeu#mode
	 */
	public void init() {

		switch (mode) {
			
			case 1:
				lanceRechercheChallenger();		
				break;

			case 2:
				lanceRechercheDefenseur();
				break;
				
			case 3:
				lanceRechercheDuel();
				break;
			}	
	}
	
	/**
	 * Crée l'objet <code>modeDeJeu</code> en mode <i>Challenger</i> pour le type <i>Recherche</i>.
	 * Et lance la fonction <code>init()</code> de ce dernier.
	 * 
	 * @see RechercheChallenger
	 * @see AbstractJeu#retry()
	 */
	public void lanceRechercheChallenger() {
		
		do {
			System.out.print("================================================");
			System.out.print("\nDébut de la partie Recherche en mode Challenger :\n");
			modeDeJeu = new RechercheChallenger(nbrDeCases, nbrEssais);
			modeDeJeu.init();

		} while (retry());
	}
	
	/**
	 * Crée l'objet <code>modeDeJeu</code> en mode <i>Defenseur</i> pour le type <i>Recherche</i>.
	 * Et lance la fonction <code>init()</code> de ce dernier.
	 * 
	 * @see RechercheDefenseur
	 * @see AbstractJeu#retry()
	 */
	private void lanceRechercheDefenseur() {
		
		do {
			System.out.print("===============================================");
			System.out.print("\nDébut de la partie Recherche en mode Defenseur :\n");
			modeDeJeu = new RechercheDefenseur(nbrDeCases, nbrEssais);
			modeDeJeu.init();

		} while (retry());
	}
	
	/**
	 * Crée l'objet <code>modeDeJeu</code> en mode <i>Duel</i> pour le type <i>Recherche</i>.
	 * Et lance la fonction <code>init()</code> de ce dernier.
	 * 
	 * @see RechercheDuel
	 * @see AbstractJeu#retry()
	 */
	private void lanceRechercheDuel() {
		do {
			System.out.print("==========================================");
			System.out.print("\nDébut de la partie Recherche en mode Duel :\n");
			modeDeJeu = new RechercheDuel(nbrDeCases, nbrEssais);
			modeDeJeu.init();

		} while (retry());
	}

}
