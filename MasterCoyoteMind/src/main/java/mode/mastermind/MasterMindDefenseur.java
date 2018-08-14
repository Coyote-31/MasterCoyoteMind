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
import mode.AbstractModeDefenseur;

/**
 * Gère les détails d'une partie de type <i>MasterMind</i> en mode <i>Defenseur</i>.
 * 
 * @author Coyote
 *
 */
public class MasterMindDefenseur extends AbstractModeDefenseur {

	/**
	 * Détermine le nombre de couleurs possibles, 
	 * c'est à dire ici les chiffres utilisables. 
	 * De 4 à 10.
	 */
	private int nbrDeCouleurs;
	/**
	 * Borne de limitation des choix possibles.
	 */
	private int limit;
	/**
	 * Tableau de listes de choix possibles pour les tentatives de l'IA.
	 */
	private LinkedList<Integer> listChoixRestantIA;
	/**
	 * Permet d'éviter un double test d'un chiffre.
	 * 
	 * @see MasterMindDefenseur#testCombiIA()
	 */
	private boolean[] tableauTestCombi;
	/**
	 * Stock la tentative pour la maj de la list de choix.
	 */
	private int[] tableauTestTentativeIA;
	
	
	/**
	 * Constructeur de <i>MasterMindDefenseur</i>.
	 * 
	 * @param nbrEssais <code>:int</code> Nombre d'essais de la partie.
	 * @param nbrDeCases <code>:int</code> Nombre de cases du plateau de jeu.
	 * @param nbrDeCouleurs <code>:int</code> Nombre de couleurs differentes.
	 */
	public MasterMindDefenseur(int nbrDeCases, int nbrEssais, int nbrDeCouleurs) {
		
		super(nbrDeCases, nbrEssais);
		
		this.nbrDeCouleurs = nbrDeCouleurs;
		listChoixRestantIA = new LinkedList<>();		
		tableauTestCombi = new boolean[nbrDeCases];
		tableauTestTentativeIA = new int[nbrDeCases];

		limit = (int)Math.pow(nbrDeCouleurs, nbrDeCases);
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
		
		printReponseIA(present, bienPlace);
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
	 * Formate correctement la réponse du testCombi()
	 * 
	 * @param present <code>:int</code> Nombre de chiffres présents
	 * @param bienPlace <code>:int</code> Nombre de chiffres correctement placés
	 */
	public void printReponseIA(int present, int bienPlace) {
		
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
	 * @see MasterMindDefenseur#tableauTestCombi 
	 */
	public void reiniTableauTestCombi() {
		
		for (int i = 0; i < tableauCombinaisonIA.length; i++) {
			tableauTestCombi[i] = true;
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
	
	/**
	 * (Re)Initialise le tableau de Liste du début de partie.
	 * 
	 * @see MasterMindDefenseur#listChoixRestantIA
	 */
	@Override
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

}

