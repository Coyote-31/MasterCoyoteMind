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
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

import mode.AbstractMode;
import mode.AbstractModeDuel;

/**
 * Classe qui gère une partie de type <i>MasterMind</i> en mode <i>Duel</i> 
 * et se charge du déroulement de la partie dans les détails.
 * 
 * @see AbstractMode
 * @see AbstractModeDuel
 * 
 * @author Coyote
 */
public class MasterMindDuel extends AbstractModeDuel {

	
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
	 * Borne de limitation des choix possibles.
	 */
	private int limit;
	/**
	 * Tableau de listes de choix possibles pour les tentatives de l'IA.
	 */
	private LinkedList<Integer> listChoixRestantIA;
	/**
	 * Stock la tentative pour la maj de la list de choix.
	 */
	private int[] tableauTestTentativeIA;
	
	/**
	 * Constructeur de <i>MasterMindDuel</i> qui fait suivre les valeurs de configuration.
	 * Et initialise les différentes variables de la partie.
	 * 
	 * @param nbrEssais <code>:int</code> Nombre d'essais de la partie.
	 * @param nbrDeCases <code>:int</code> Nombre de cases du plateau de jeu.
	 * @param nbrDeCouleurs <code>:int</code> Nombre de couleurs.
	 */
	public MasterMindDuel(int nbrDeCases, int nbrEssais, int nbrDeCouleurs) {

		super(nbrDeCases, nbrEssais);
		
		this.nbrDeCouleurs = nbrDeCouleurs;
		tableauTestCombi = new boolean[nbrDeCases];
		listChoixRestantIA = new LinkedList<>();
		tableauTestTentativeIA = new int[nbrDeCases];
		
		limit = (int)Math.pow(nbrDeCouleurs, nbrDeCases);
	}

	/**
	 * Lance l'initialisation de la <i>partie</i>.
	 * En demandant au joueur quelle combinaison l'ordinateur doit trouver 
	 * Initialise la liste de choix et lance la fonction <code>partie()</code>.
	 * 
	 * @see MasterMindDuel#combinaisonDefinition()
	 * @see MasterMindDuel#initListChoix()
	 * @see MasterMindDuel#partie()
	 */
	@Override
	public void init() {

		// Création de la combinaison à trouver par le joueur.
		
		SecureRandom rand = new SecureRandom();
		
		for (int i = 0; i < nbrDeCases; i++) {
			tableauCombinaison[i] = rand.nextInt(nbrDeCouleurs);
		}
		
		combinaisonDefinition();
		initListChoix();
		
		partie();
		
	}

	/**
	 * Demande à l'utilisateur la combinaison que l'IA doit trouver.
	 * 
	 * @see AbstractModeDuel#tableauCombinaisonIA	 
	 */
	public void combinaisonDefinition() {

		String scanCombinaison = null;

		boolean loopTour = true;

		do {
			System.out.print("\nVeuillez rentrer une combinaison de " + nbrDeCases 
					+ " chiffres de 0 à " + (nbrDeCouleurs-1) + " :\n");

			try {
				scanCombinaison = scan.next();

				// Vérification du nombre de charactères
				if (scanCombinaison.length() > nbrDeCases) {
					throw new Exception();
				}

				for (int i = 0; i < tableauCombinaisonIA.length; i++) {

					//convertion char => nombre
					tableauCombinaisonIA[i] = Character.getNumericValue(scanCombinaison.charAt(i));
					// Vérification que se sont des chiffres de 0 à nbrDeCouleurs-1.
					for (int j = 0; j < tableauCombinaisonIA.length; j++) {
						if (tableauCombinaisonIA[i] < 0 || tableauCombinaisonIA[i] > (nbrDeCouleurs-1)) {
							throw new Exception();
						}
					}
				}
				loopTour = false;
				
			} catch (Exception e) {
				System.err.println("\nErreur : Veuillez rentrer un nombre de " + nbrDeCases 
						+ " chiffres de 0 à " + (nbrDeCouleurs-1) + " !\n");				

			} finally {
				scan.nextLine();
			}

		} while (loopTour);
	}
	
	/**
	 * (Re)Initialise le tableau de Liste du début de partie.
	 * 
	 * @see MasterMindDuel#listChoixRestantIA
	 */
	public void initListChoix() {

		// (Re)Initialise la liste des choix de l'IA.
		listChoixRestantIA.clear();
		
		// Remplit la liste en changeant de base (radix = nbrDeCouleurs)
		String number;
		
		for (int i = 0; i < limit; i++) {
			
			number = Integer.toString(i, nbrDeCouleurs);
			listChoixRestantIA.add(Integer.valueOf(number));		
		}	
	}
	
	/**
	 * Fonction qui définit le déroulement en détail de la <i>partie</i>.
	 * 
	 * @see MasterMindDuel#partieJoueur()
	 * @see MasterMindDuel#partieIA()
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

		// Déroulement de la partie et test du nombre de manches restantes à l'avantage du joueur.
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
			
		} else if (victoireIA) {
			System.out.println("\nDommage ! Vous avez perdu...");
			System.out.println("La bonne combinaison était : " + Arrays.toString(tableauCombinaison));
		
		} else {
			System.out.println("\nEgalité ! Personne n'a trouvé la bonne combinaison !");
			System.out.println("La bonne combinaison était : " + Arrays.toString(tableauCombinaison));
		}
		
	}

	/**
	 * Fonction qui définit le déroulement en détail de la <i>partie</i> du joueur.
	 * 
	 * @see MasterMindDuel#tour()
	 * @see MasterMindDuel#testCombi()
	 */
	public void partieJoueur() {
		
		System.out.println("\n   Votre tour :");
		tour();
		testCombi();
	}

	/**
	 * Fonction qui définit le déroulement en détail de la <i>partie</i> de l'IA.
	 * 
	 * @see MasterMindDuel#tourIA()
	 * @see MasterMindDuel#testCombiIA()
	 */
	public void partieIA() {
		
		System.out.println("\n   Tour de l'ordinateur :");
		
		tourIA();
		testCombiIA();
	}
	
	/**
	 * Récupération dans un tableau de la tentative du joueur.
	 * 
	 * @see AbstractModeDuel#tableauTentative
	 */
	public void tour() {

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
					for (int j = 0; j < tableauTentative.length; j++) {
						if (tableauTentative[i] < 0 || tableauTentative[i] > (nbrDeCouleurs-1)) {
							throw new Exception();
						}
					}
				}
				loopTour = false;
				
			} catch (Exception e) {
				System.err.println("\nErreur : Veuillez rentrer un nombre de "
						+ nbrDeCases + " chiffres de 0 à "+ (nbrDeCouleurs-1) +" !\n");				

			} finally {
				scan.nextLine();
			}

		} while (loopTour);
	}
	
	/**
	 * Compare la tentative avec la combinaison et affiche le résultat.
	 * 
	 * @see MasterMindDuel#printReponse(int, int)
	 */
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

	/**
	 * Reinitialise le tableauTestCombi
	 * 
	 * @see MasterMindDuel#tableauTestCombi 
	 */
	public void reiniTableauTestCombi() {
		
		for (int i = 0; i < tableauCombinaison.length; i++) {
			tableauTestCombi[i] = true;
		}
	}
	
	/**
	 * Décrit la tentative de l'IA pour trouver la combinaison.
	 */
	public void tourIA() {
		
		if(dev) {
			System.out.println("ListeChoix IA :" + listChoixRestantIA.size());
		}
		
		// Choix de l'IA basé sur un nombre aléatoire qui permet de choisir parmis sa liste de choix.
		SecureRandom rand = new SecureRandom();
		int choixBrut;
		
		if (listChoixRestantIA.size() > 1) {
			choixBrut = listChoixRestantIA.get(rand.nextInt(listChoixRestantIA.size()-1));
		
		} else {
			choixBrut = listChoixRestantIA.get(0);
		}
		
		conversionListToTab(choixBrut);
		
		System.out.println("Tentative de l'ordinateur :");
		System.out.println(Arrays.toString(tableauTentativeIA));
	}	

	/**
	 * Convertie le choix brut en nombre typé pour le tableauTentativeIA.
	 * Rajoute les zeros devant le nombre si besoin.
	 * @param choixBrut : nombre brut de la tentative.
	 */
	public void conversionListToTab(int choixBrut) {

		int i = tableauTentativeIA.length - 1;
		
		while(choixBrut > 0) {
			
			tableauTentativeIA[i] = choixBrut % 10;
			choixBrut = choixBrut / 10;
			i--;
		}
		
		while (i >= 0) {
			
			tableauTentativeIA[i] = 0;
			i--;
		}
	}
	
	/**
	 * Compare la tentative de l'IA avec la combinaison et affiche le résultat.
	 */
	public void testCombiIA() {

		int present = 0;
		int bienPlace = 0;
		
		reiniTableauTestCombi();
		
		for (int i = 0; i < tableauCombinaisonIA.length; i++) {
			
			if (tableauCombinaisonIA[i] == tableauTentativeIA[i]) {
				
				tableauVictoireIA[i] = true;
				tableauTestCombi[i] = false;
				bienPlace ++;
				
			} else {
				
				tableauVictoireIA[i] = false;
				
				for (int j = 0; j < tableauTentativeIA.length; j++) {

					// Evite les doublons avec les biens placés et test la présence dans la tentative.
					if (i != j && 
						tableauCombinaisonIA[i] == tableauTentativeIA[j] && 
						tableauTestCombi[j] &&
						tableauCombinaisonIA[j] != tableauTentativeIA[j]) {

						tableauTestCombi[j] = false;
						present ++;
						break;
					}
				}
			}
		}
		
		printReponse(present, bienPlace);
		majListDeChoixIA(present, bienPlace);
		
		// Test pour savoir si tout le tableauVictoire est à true.
		victoireIA = true;

		for (int i = 0; i < tableauVictoireIA.length; i++) {
			if (!tableauVictoireIA[i]) {
				victoireIA = false;
			}
		}		
	}
	
	/**
	 * Met à jours la listDeChoixIA.
	 * Enlève toutes combinaisons qui ne renverraient pas
	 * le même resultat si elle étaient la solution.
	 * 
	 * @param testPresent : nombre de chiffres présents.
	 * @param testBienPlace : nombre de chiffres bien placés.
	 */
	public void majListDeChoixIA(int testPresent, int testBienPlace) {

		Iterator<Integer> itListChoixRestantIA = listChoixRestantIA.iterator();
		
		while (itListChoixRestantIA.hasNext()) {
			
			int testChoixRestantIA = itListChoixRestantIA.next();
			
			conversionTestListToTab(testChoixRestantIA);
			
			if(!testMajCombiIA(testPresent, testBienPlace)) {
				itListChoixRestantIA.remove();
			}
		}	
	}
	
	/**
	 * Permet de tester si la réponse de la tentative est égale à celle du test,
	 * parmi un element de listChoixRestantIA.
	 * 
	 * @param testPresent : nombre de chiffres présents de la tentative.
	 * @param testBienPlace : nombre de chiffres bien placés de la tentative.
	 * 
	 * @return true si la réponse de la tentative est égale à celle du test.
	 */
	public boolean testMajCombiIA(int testPresent, int testBienPlace) {
		
		int present = 0;
		int bienPlace = 0;
		
		reiniTableauTestCombi();
		
		for (int i = 0; i < tableauTestTentativeIA.length; i++) {
			
			if (tableauTestTentativeIA[i] == tableauTentativeIA[i]) {
				
				tableauTestCombi[i] = false;
				bienPlace ++;
				
			} else {
				
				for (int j = 0; j < tableauTentativeIA.length; j++) {

					// Evite les doublons avec les biens placés et test la présence dans la tentative.
					if (i != j && 
						tableauTestTentativeIA[i] == tableauTentativeIA[j] && 
						tableauTestCombi[j] &&
						tableauCombinaisonIA[j] != tableauTentativeIA[j]) {

						tableauTestCombi[j] = false;
						present ++;
						break;
					}
				}
			}
		}
		
		return (testPresent == present && testBienPlace == bienPlace);

	}
	
	/**
	 * Met à jour tableauTestTentativeIA en formatant le nombre dans un tableau.
	 * Met des zeros si necessaire.
	 * 
	 * @param testChoixBrut : La valeur brute du nombre.
	 */
	public void conversionTestListToTab(int testChoixBrut) {

		int i = tableauTestTentativeIA.length - 1;
		
		while(testChoixBrut > 0) {
			
			tableauTestTentativeIA[i] = testChoixBrut % 10;
			testChoixBrut = testChoixBrut / 10;
			i--;
		}
		
		while (i >= 0) {
			
			tableauTestTentativeIA[i] = 0;
			i--;
		}	
	}
	
}
