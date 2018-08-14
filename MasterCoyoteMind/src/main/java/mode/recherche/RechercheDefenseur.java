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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import main.Main;
import mode.AbstractMode;
import mode.AbstractModeDefenseur;

/**
 * Classe qui gère une partie de type <i>Recherche</i> en mode <i>Defenseur</i> 
 * et se charge du déroulement de la partie dans les détails.
 * 
 * @see AbstractMode
 * @see AbstractModeDefenseur
 * 
 * @author Coyote
 */
public class RechercheDefenseur extends AbstractModeDefenseur {


	/**
	 * Tableau de listes de choix possibles pour les tentatives de l'IA.
	 */
	private ArrayList<Integer>[] tabListChoixRestantIA;
	
	
	/**
	 * Constructeur qui fait suivre les valeurs de configuration.
	 * 
	 * @param nbrDeCases : <code>int</code>
	 * @param nbrEssais : <code>int</code>
	 * 
	 * @see Main#nbrDeCases
	 * @see Main#nbrEssais
	 * @see AbstractMode#AbstractMode(int, int)
	 * @see AbstractModeDefenseur#AbstractModeDefenseur(int, int)
	 */
	@SuppressWarnings("unchecked")
	public RechercheDefenseur(int nbrDeCases, int nbrEssais) {
		
		super(nbrDeCases, nbrEssais);
		
		// Initialise le tableau de liste de choix de l'IA.
		tabListChoixRestantIA = (ArrayList<Integer>[])new ArrayList[nbrDeCases];
		for (int i = 0; i < tabListChoixRestantIA.length; i++) {
			tabListChoixRestantIA[i] = new ArrayList<>();
		}
	}

	/**
	 * Demande à l'utilisateur la combinaison que l'IA doit trouver.
	 * 
	 * @see AbstractModeDefenseur#tableauCombinaisonIA	 
	 */
	public void combinaisonDefinition() {

		String scanCombinaison = null;

		boolean loopTour = true;

		do {
			System.out.print("\nVeuillez rentrer une combinaison de " + nbrDeCases + " chiffres de 0 à 9 :\n");

			try {
				scanCombinaison = scan.next();

				// Vérification du nombre de charactères
				if (scanCombinaison.length() > nbrDeCases) {
					throw new Exception();
				}

				for (int i = 0; i < tableauCombinaisonIA.length; i++) {

					//convertion char => nombre
					tableauCombinaisonIA[i] = Character.getNumericValue(scanCombinaison.charAt(i));
					// Vérification que se sont des chiffres de 0 à 9
					for (int j = 0; j < tableauCombinaisonIA.length; j++) {
						if (tableauCombinaisonIA[i] < 0 || tableauCombinaisonIA[i] > 9) {
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
	 * Décrit la tentative de l'IA pour trouver la combinaison.
	 */
	public void tourIA() {

		if(dev) {
			System.out.println("ListeChoix IA :" + Arrays.toString(tabListChoixRestantIA));
		}
		
		// Choix de l'IA basé sur un nombre aléatoire qui permet de choisir parmis sa liste de choix.
		SecureRandom rand = new SecureRandom();

		for (int i = 0; i < nbrDeCases; i++) {

			if (tabListChoixRestantIA[i].size() == 1) {
				
				tableauTentativeIA[i] = tabListChoixRestantIA[i].get(0);
			
			} else {
				
				tableauTentativeIA[i] = tabListChoixRestantIA[i].get(rand.nextInt(tabListChoixRestantIA[i].size()-1));
			}
		}
		
		System.out.println("Tentative de l'ordinateur :");
		System.out.println(Arrays.toString(tableauTentativeIA));
	}
	
	/**
	 * Compare la tentative de l'IA avec la combinaison et affiche le résultat.
	 */
	public void testCombiIA() {

		String resultat = "";
		
		
		for (int i = 0; i < tableauTentativeIA.length; i++) {
			
			if (tableauTentativeIA[i] == tableauCombinaisonIA[i]) {

				tableauVictoireIA[i] = true;
				majListChoixEgale(i);
				resultat += "=";				

			} else if (tableauTentativeIA[i] < tableauCombinaisonIA[i]) {

				tableauVictoireIA[i] = false;
				majListChoixSup(i);
				resultat += "+";
				

			} else if (tableauTentativeIA[i] > tableauCombinaisonIA[i]) {

				tableauVictoireIA[i] = false;
				majListChoixInf(i);
				resultat += "-";
				
			}
		}

		System.out.println(resultat);

		// Test pour savoir si tout le tableauVictoire est à true.
		victoireIA = true;

		for (int i = 0; i < tableauVictoireIA.length; i++) {
			if (!tableauVictoireIA[i]) {
				victoireIA = false;
			}
		}
	}

	/**
	 * (Re)Initialise le tableau de Liste du début de partie.
	 * 
	 * @see RechercheDefenseur#tabListChoixRestantIA
	 */
	public void initListChoix() {

		for (int i = 0; i < tabListChoixRestantIA.length; i++) {

			if (!tabListChoixRestantIA[i].isEmpty()) {
				tabListChoixRestantIA[i].clear();
			}

			for (int j = 0; j < 10; j++) {
				tabListChoixRestantIA[i].add(j);
			}
		}
	}	
	
	/**
	 * Quand la combinaison correspond à la tentative :
	 * réduit la liste de choix restant à 1 si il y a besoin.
	 * 
	 * @param i <code>:int</code> index du tableau de tentative IA
	 * 
	 * @see RechercheDefenseur#tableauTentativeIA
	 * @see RechercheDefenseur#tabListChoixRestantIA
	 */
	public void majListChoixEgale(int i) {
		
		if(tabListChoixRestantIA[i].size() > 1) {
			
			tabListChoixRestantIA[i].clear();
			tabListChoixRestantIA[i].add(tableauTentativeIA[i]);
		}
	}
	
	/**
	 * Quand la combinaison est superieure à la tentative :
	 * réduit la liste de choix restant inferieure à la tentative.
	 * 
	 * @param i <code>:int</code> index du tableau de tentative IA
	 * 
	 * @see RechercheDefenseur#tableauTentativeIA
	 * @see RechercheDefenseur#tabListChoixRestantIA
	 */
	public void majListChoixSup(int i) {

		Iterator<Integer> tabListChoixIaIterator = tabListChoixRestantIA[i].iterator();

		while (tabListChoixIaIterator.hasNext()) {

			if(tabListChoixIaIterator.next() <= tableauTentativeIA[i]) {
				tabListChoixIaIterator.remove();
			}
		}
	}

	/**
	 * Quand la combinaison est inférieure à la tentative :
	 * réduit la liste de choix restant superieure à la tentative.
	 * 
	 * @param i <code>:int</code> index du tableau de tentative IA
	 * 
	 * @see RechercheDefenseur#tableauTentativeIA
	 * @see RechercheDefenseur#tabListChoixRestantIA
	 */
	public void majListChoixInf(int i) {

		Iterator<Integer> tabListChoixIaIterator = tabListChoixRestantIA[i].iterator();

		while (tabListChoixIaIterator.hasNext()) {

			if(tabListChoixIaIterator.next() >= tableauTentativeIA[i]) {
				tabListChoixIaIterator.remove();
			}
		}
	}
	
}
