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
import mode.mastermind.MasterMindChallenger;
import mode.mastermind.MasterMindDefenseur;
import mode.mastermind.MasterMindDuel;

/**
 * Cette classe gère une partie de type <i>MasterMind</i>.
 * Elle s'occupe de créer l'objet <code>modeDeJeu</code> en fonction du <i>mode</i> de jeu correspondant.
 * 
 * @see AbstractJeu
 * @see AbstractJeu#mode
 * 
 * @author Coyote
 */
public class JeuMasterMind extends AbstractJeu {

	/**
	 * Détermine le nombre de couleurs possibles, 
	 * c'est à dire ici les chiffres utilisables. 
	 * De 4 à 10.
	 */
	private int nbrDeCouleurs;
	
	/**
	 * Constructeur qui fait suivre les valeurs de configuration.
	 * 
	 * @param nbrDeCases : <code>int</code>
	 * @param nbrEssais : <code>int</code>
	 * @param mode : <code>int {1,2,3}</code>
	 * @param nbrDeCouleurs : <code>int {4..10}</code>
	 * 
	 * @see Main#nbrDeCases
	 * @see Main#nbrEssais
	 * @see Main#mode
	 * @see Main#nbrDeCouleurs
	 */
	public JeuMasterMind(int nbrDeCases, int nbrEssais, int mode, int nbrDeCouleurs) {
			
			super(nbrDeCases, nbrEssais, mode);
			
			this.nbrDeCouleurs = nbrDeCouleurs;
		
	}

	/**
	 * Lance l'initialisation du jeu de type <i>MasterMind</i> en créant l'objet <code>modeDeJeu</code>
	 * qui correspond au <i>mode</i> de jeu sélectionné.
	 * 
	 * @see AbstractJeu#modeDeJeu
	 * @see AbstractJeu#mode
	 */
	@Override
	public void init() {

		switch (mode) {
			
			case 1:
				lanceMasterMindChallenger();		
				break;

			case 2:
				lanceMasterMindDefenseur();
				break;
				
			case 3:
				lanceMasterMindDuel();
				break;
			}	
	}
	
	/**
	 * Crée l'objet <code>modeDeJeu</code> en mode <i>Challenger</i> pour le type <i>MasterMind</i>.
	 * Et lance la fonction <code>init()</code> de ce dernier.
	 * 
	 * @see MasterMindChallenger
	 * @see AbstractJeu#retry()
	 */
	public void lanceMasterMindChallenger() {
		
		do {
			System.out.print("=================================================");
			System.out.print("\nDébut de la partie MasterMind en mode Challenger :\n");
			modeDeJeu = new MasterMindChallenger(nbrDeCases, nbrEssais, nbrDeCouleurs);
			modeDeJeu.init();

		} while (retry());
	}

	/**
	 * Crée l'objet <code>modeDeJeu</code> en mode <i>Defenseur</i> pour le type <i>MasterMind</i>.
	 * Et lance la fonction <code>init()</code> de ce dernier.
	 * 
	 * @see MasterMindDefenseur
	 * @see AbstractJeu#retry()
	 */
	private void lanceMasterMindDefenseur() {
		
		do {
			System.out.print("================================================");
			System.out.print("\nDébut de la partie MasterMind en mode Defenseur :\n");
			modeDeJeu = new MasterMindDefenseur(nbrDeCases, nbrEssais, nbrDeCouleurs);
			modeDeJeu.init();

		} while (retry());
	}
	
	/**
	 * Crée l'objet <code>modeDeJeu</code> en mode <i>Duel</i> pour le type <i>MasterMind</i>.
	 * Et lance la fonction <code>init()</code> de ce dernier.
	 * 
	 * @see MasterMindDuel
	 * @see AbstractJeu#retry()
	 */
	private void lanceMasterMindDuel() {
		do {
			System.out.print("===========================================");
			System.out.print("\nDébut de la partie MasterMind en mode Duel :\n");
			modeDeJeu = new MasterMindDuel(nbrDeCases, nbrEssais, nbrDeCouleurs);
			modeDeJeu.init();

		} while (retry());
	}

}
