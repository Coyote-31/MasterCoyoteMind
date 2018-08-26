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

import main.Main;
import mode.mastermind.MasterMindDuel;
import mode.recherche.RechercheDuel;

/**
 * Classe abstraite qui permet de mettre en commun les codes sources identiques 
 * des classes de la partie :
 * <ul>
 * <li><code>RechercheDuel</code></li>
 * <li><code>MasterMindDuel</code></li>
 * </ul>
 * 
 * <p>Ces classes gèrent l'application au niveau du <i>mode</i> de jeu et se chargent
 * du déroulement de la partie dans les détails propre à chaque classe.</p>
 * 
 * @see RechercheDuel
 * @see MasterMindDuel
 * @see AbstractMode
 * 
 * @author Coyote
 */
public abstract class AbstractModeDuel extends AbstractMode {

	// Variables joueur :
	
		/**
		 * Tableau contenant la tentative du joueur.
		 */
		protected int[] tableauTentative;
		/**
		 * Tableau contenant la combinaison que le joueur doit trouver.
		 */
		protected int[] tableauCombinaison;
		/**
		 * Tableau contenant des booléens mis à true quand elle correspond à la combinaison.
		 */
		protected boolean[] tableauVictoire;
		/**
		 * Variable qui lorsque la partie est gagné par le joueur est à true.
		 */
		protected boolean victoire;
		
		// Variable IA :
		
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
	public AbstractModeDuel(int nbrDeCases, int nbrEssais) {
		super(nbrDeCases, nbrEssais);
		
		// Construction des variables du joueur
		tableauTentative = new int[nbrDeCases];
		tableauVictoire = new boolean[nbrDeCases];
		tableauCombinaison = new int[nbrDeCases];
		victoire = false;
		
		// Construction des variables de l'IA
		tableauTentativeIA = new int[nbrDeCases];
		tableauCombinaisonIA = new int[nbrDeCases];
		tableauVictoireIA = new boolean[nbrDeCases];
		victoireIA = false;
	}
	
	
	
}
