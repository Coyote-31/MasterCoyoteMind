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
package mode;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import main.Main;
import mode.mastermind.MasterMindDefenseur;
import mode.recherche.RechercheDefenseur;

/**
 * Classe abstraite qui permet de mettre en commun les codes sources identiques 
 * des classes de la partie :
 * <ul>
 * <li><code>RechercheDefenseur</code></li>
 * <li><code>MasterMindDefenseur</code></li>
 * </ul>
 * 
 * <p>Ces classes gèrent l'application au niveau du <i>mode</i> de jeu et se chargent
 * du déroulement de la partie dans les détails propre à chaque classe.</p>
 * 
 * @see RechercheDefenseur
 * @see MasterMindDefenseur
 * 
 * @author Coyote
 */
public abstract class AbstractModeDefenseur extends AbstractMode {

	
	/**
	 * Tableau contenant la tentative de l'IA.
	 */
	protected int[] tableauTentativeIA;
	/**
	 * Tableau contenant la combinaison que l'IA doit trouver.
	 */
	protected int[] tableauCombinaisonIA;
	/**
	 * Tableau contenant des booléens mis à true quand elle correspond à la combinaison.
	 */
	protected boolean[] tableauVictoireIA;
	/**
	 * Variable qui lorsque la partie est gagné par l'IA est à true.
	 */
	protected boolean victoireIA;
	/**
	 * Création de l'objet Logger permettant la gestion des logs de l'application.
	 */
	private static final Logger LOG = LogManager.getLogger();
	
	
	/**
	 * Constructeur qui fait suivre les valeurs de configuration.
	 * Et initialise les différentes variables de la partie.
	 * 
	 * @param nbrDeCases : <code>int</code>
	 * @param nbrEssais : <code>int</code>
	 * 
	 * @see Main#nbrDeCases
	 * @see Main#nbrEssais
	 * @see AbstractMode#AbstractMode(int, int)
	 */
	public AbstractModeDefenseur(int nbrDeCases, int nbrEssais) {
		super(nbrDeCases, nbrEssais);
		
		tableauTentativeIA = new int[nbrDeCases];
		tableauCombinaisonIA = new int[nbrDeCases];
		tableauVictoireIA = new boolean[nbrDeCases];
		victoireIA = false;
	}
	
	/**
	 * Lance l'initialisation de la <i>partie</i>.
	 * En demandant au joueur quelle combinaison l'ordinateur doit trouver 
	 * Initialise la liste de choix et lance la fonction <code>partie()</code>.
	 * 
	 * @see AbstractModeDefenseur#combinaisonDefinition()
	 * @see AbstractModeDefenseur#initListChoix()
	 * @see AbstractModeDefenseur#partie()
	 */
	@Override
	public void init() {
		
		// Création de la liste de combinaison du jeu.
		combinaisonDefinition();
		partie();
	}
	
	/**
	 * Demande à l'utilisateur la combinaison que l'IA doit trouver.
	 * 
	 * @see AbstractModeDefenseur#tableauCombinaisonIA	 
	 */
	public abstract void combinaisonDefinition();
	

	/**
	 * Fonction qui définit le déroulement en détail de la <i>partie</i>.
	 * 
	 * @see AbstractModeDefenseur#tourIA()
	 * @see AbstractModeDefenseur#testCombiIA()
	 */
	@Override
	public void partie() {
		
		// Affichage de la réponse pour debug
		if (dev) {
			System.out.print("" + Arrays.toString(tableauCombinaisonIA) + "\n");
		}
		
		boolean loopPartie = true;
		int compteurDeManche = nbrEssais;

		initListChoix();
		
		// Déroulement de la partie et test du nombre de manches restantes
		do {
			if (compteurDeManche > 1) {
				System.out.println("\n" + compteurDeManche + " essais restants. ");
				
			} else if (compteurDeManche == 1) {
				System.out.println("\n" + compteurDeManche + " essai restant. ");
			}
			
			tourIA();
			testCombiIA();
			compteurDeManche--;

			if (compteurDeManche <= 0) {
				loopPartie = false;
			}

		} while (!victoireIA && loopPartie);
		

		if (victoireIA) {
			System.out.println("\nDommage ! L'ordinateur a trouvé la bonne combinaison ! ");
			LOG.info("Victoire joueur : Mode Defenseur");
			
		} else if (!loopPartie) {
			System.out.println("\nBravo ! L'ordinateur a perdu !");
			LOG.info("Victoire IA : Mode Defenseur");
			
		}
	}
	
	/**
	 * (Re)Initialise le tableau de Liste du début de partie.
	 */
	public abstract void initListChoix();
		
	/**
	 * Décrit la tentative de l'IA pour trouver la combinaison.
	 */
	public abstract void tourIA();
	
	/**
	 * Compare la tentative de l'IA avec la combinaison et affiche le résultat.
	 */
	public abstract void testCombiIA();
	
}
