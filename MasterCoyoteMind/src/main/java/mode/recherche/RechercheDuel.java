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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import main.Main;
import mode.AbstractMode;
import mode.AbstractModeDuel;

/**
 * Classe qui gère une partie de type <i>Recherche</i> en mode <i>Duel</i> 
 * et se charge du déroulement de la partie dans les détails.
 * 
 * @see AbstractMode
 * @see AbstractModeDuel
 * 
 * @author Coyote
 */
public class RechercheDuel extends AbstractModeDuel {

	
	/**
	 * Tableau de listes de choix possibles pour les tentatives de l'IA.
	 */
	private ArrayList<Integer>[] tabListChoixRestantIA;
	/**
	 * Création de l'objet Logger permettant la gestion des logs de l'application.
	 */
	private static final Logger LOG = LogManager.getLogger();
	/**
	 * String de constante pour les erreurs d'entrées clavier de l'utilisateur.
	 */
	private static final String ERRORUSER = "Erreur entrée clavier utilisateur";
	
	
	/**
	 * Constructeur qui fait suivre les valeurs de configuration.
	 * 
	 * @param nbrDeCases : <code>int</code>
	 * @param nbrEssais : <code>int</code>
	 * 
	 * @see Main#nbrDeCases
	 * @see Main#nbrEssais
	 * @see AbstractMode#AbstractMode(int, int)
	 * @see AbstractModeDuel#AbstractModeDuel(int, int)
	 */
	@SuppressWarnings("unchecked")
	public RechercheDuel(int nbrDeCases, int nbrEssais) {
		
		super(nbrDeCases, nbrEssais);
		
		// Initialise le tableau de liste de choix de l'IA.
		tabListChoixRestantIA = new ArrayList[nbrDeCases];
		for (int i = 0; i < tabListChoixRestantIA.length; i++) {
			tabListChoixRestantIA[i] = new ArrayList<>();
		}
	}

	/**
	 * Lance l'initialisation de la <i>partie</i>.
	 * En demandant au joueur quelle combinaison l'ordinateur doit trouver 
	 * Initialise la liste de choix et lance la fonction <code>partie()</code>.
	 * 
	 * @see RechercheDuel#combinaisonDefinition()
	 * @see RechercheDuel#initListChoix()
	 * @see RechercheDuel#partie()
	 */
	@Override
	public void init() {
		
		// Création de la combinaison à trouver par le joueur.
		
		SecureRandom rand = new SecureRandom();

		for (int i = 0; i < nbrDeCases; i++) {
			tableauCombinaison[i] = rand.nextInt(10);
		}
		
		combinaisonDefinition();
		initListChoix();
		
		partie();

	}

	/**
	 * Fonction qui définit le déroulement en détail de la <i>partie</i>.
	 * 
	 * @see RechercheDuel#partieJoueur()
	 * @see RechercheDuel#partieIA()
	 */
	@Override
	public void partie() {
		
		
		if (dev) {
			// Affichage des réponses pour debug
			System.out.print("\n   Combinaison que le joueur doit trouver : " + Arrays.toString(tableauCombinaison));
			System.out.print("\n   Combinaison que l'IA doit trouver : " + Arrays.toString(tableauCombinaisonIA) + "\n");
		}
		
		boolean loopPartie = true;
		int compteurDeManche = nbrEssais;

		// Déroulement de la partie et test du nombre de manches restantes
		do {
			System.out.println("_____________________________________");
			System.out.println("\n" + compteurDeManche + " essai(s) restant(s).");

			partieJoueur();

			// Si le joueur n'a pas encore gagné l'IA tente sa chance.
			if (!victoire) {
				
				partieIA();

				compteurDeManche--;

				if (compteurDeManche <= 0) {
					loopPartie = false;
				}
			}

		} while (!victoire && !victoireIA && loopPartie);
		

		if (victoire) {
			System.out.println("\nBravo ! Vous avez trouvé la combinaison : " + Arrays.toString(tableauCombinaison));
			LOG.info("Victoire joueur : Recherche mode Duel");
			
		} else if (victoireIA) {
			System.out.println("\nDommage ! Vous avez perdu...");
			System.out.println("La bonne combinaison était : " + Arrays.toString(tableauCombinaison));
			LOG.info("Victoire IA : Recherche mode Duel");
			
		} else {
			System.out.println("\nEgalité ! Personne n'a trouvé la bonne combinaison !");
			System.out.println("La bonne combinaison était : " + Arrays.toString(tableauCombinaison));
			LOG.info("Egalité : Recherche mode Duel");
		}
		
	}
	
	/**
	 * Fonction qui définit le déroulement en détail de la <i>partie</i> du joueur.
	 * 
	 * @see RechercheDuel#tour()
	 * @see RechercheDuel#testCombi()
	 */
	public void partieJoueur() {
		
		System.out.println("\n   Votre tour :");
		tour();
		testCombi();
	}
	
	/**
	 * Fonction qui définit le déroulement en détail de la <i>partie</i> de l'IA.
	 * 
	 * @see RechercheDuel#tourIA()
	 * @see RechercheDuel#testCombiIA()
	 */
	public void partieIA() {
		
		System.out.println("\n   Tour de l'ordinateur :");
		
		if (dev) {
			System.out.println("\nListeChoix IA :" + Arrays.toString(tabListChoixRestantIA));
		}
		
		tourIA();
		testCombiIA();
	}
	
	/**
	 * Demande à l'utilisateur la combinaison à trouver par l'IA.
	 * 
	 * @see RechercheDuel#tableauCombinaisonIA
	 */
	private void combinaisonDefinition() {

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

					//convertion char => chiffre int
					tableauCombinaisonIA[i] = Character.getNumericValue(scanCombinaison.charAt(i));

					// Vérification que se sont des chiffres de 0 à 9
					if (tableauCombinaisonIA[i] < 0 || tableauCombinaisonIA[i] > 9) {
						throw new Exception();
					}
				}
				
				loopTour = false;
				
			} catch (Exception e) {
				System.err.println("\nErreur : Veuillez rentrer un nombre de " + nbrDeCases + " chiffres de 0 à 9 !\n");				
				LOG.error(ERRORUSER, e);
				
			} finally {
				scan.nextLine();
			}

		} while (loopTour);
	}
	
	/**
	 * (Re)Initialise le tableau de Liste du début de partie. 
	 * 
	 * @see RechercheDuel#tabListChoixRestantIA
	 */
	private void initListChoix() {

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
	 * Récupération dans un tableau de la tentative du joueur.
	 * 
	 * @see RechercheDuel#tableauTentative
	 */
	public void tour() {

		// Récupération de la réponse :

		String scanTentative = null;

		boolean loopTour = true;

		do {
			System.out.print("\nVeuillez rentrer " + nbrDeCases + " chiffres de 0 à 9 :\n");

			try {
				scanTentative = scan.next();

				// Vérification du nombre de charactères
				if (scanTentative.length() > nbrDeCases) {
					throw new Exception();
				}

				for (int i = 0; i < tableauTentative.length; i++) {

					//convertion char => nombre en ASCII
					tableauTentative[i] = (scanTentative.charAt(i) - 48);

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
				LOG.error(ERRORUSER, e);
				
			} finally {
				scan.nextLine();
			}

		} while (loopTour);
	}

	/**
	 * Décrit la tentative de l'IA pour trouver la combinaison.
	 * 
	 * @see RechercheDuel#tableauCombinaisonIA
	 * @see RechercheDuel#tabListChoixRestantIA
	 */
	private void tourIA() {

		//  Choix de l'IA basé sur un nombre aléatoire qui permet de choisir parmis sa liste de choix.
		
		SecureRandom rand = new SecureRandom();

		for (int i = 0; i < nbrDeCases; i++) {

			if (tabListChoixRestantIA[i].size() == 1) {
				
				tableauTentativeIA[i] = tabListChoixRestantIA[i].get(0);
			
			} else {
				
				tableauTentativeIA[i] = tabListChoixRestantIA[i].get(rand.nextInt(tabListChoixRestantIA[i].size()-1));
			}
		}
		
		System.out.println("\nTentative de l'ordinateur :");
		System.out.println(Arrays.toString(tableauTentativeIA));
	}
	
	/**
	 * Compare la tentative du joueur avec la combinaison et affiche le résultat.
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
	
	/**
	 * Compare la tentative de l'IA avec la combinaison et affiche le résultat.
	 */
	private void testCombiIA() {

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
	 * Quand la combinaison correspond à la tentative :
	 * réduit la liste de choix restant à 1 si il y a besoin.
	 * 
	 * @param i <code>:int</code> index du tableau de tentative IA
	 * 
	 * @see RechercheDuel#tableauTentativeIA
	 * @see RechercheDuel#tabListChoixRestantIA
	 */
	private void majListChoixEgale(int i) {
		
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
	 * @see RechercheDuel#tableauTentativeIA
	 * @see RechercheDuel#tabListChoixRestantIA
	 */
	private void majListChoixSup(int i) {

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
	 * @see RechercheDuel#tableauTentativeIA
	 * @see RechercheDuel#tabListChoixRestantIA
	 */
	private void majListChoixInf(int i) {

		Iterator<Integer> tabListChoixIaIterator = tabListChoixRestantIA[i].iterator();

		while (tabListChoixIaIterator.hasNext()) {

			if(tabListChoixIaIterator.next() >= tableauTentativeIA[i]) {
				tabListChoixIaIterator.remove();
			}
		}
	}

}

