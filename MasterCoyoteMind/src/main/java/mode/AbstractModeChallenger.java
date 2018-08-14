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

import main.Main;
import mode.mastermind.MasterMindChallenger;
import mode.recherche.RechercheChallenger;

/**
 * Classe abstraite qui permet de mettre en commun les codes sources identiques 
 * des classes de la partie :
 * <ul>
 * <li><code>RechercheChallenger</code></li>
 * <li><code>MasterMindChallenger</code></li>
 * </ul>
 * 
 * <p>Ces classes gèrent l'application au niveau du <i>mode</i> de jeu et se chargent
 * du déroulement de la partie dans les détails propre à chaque classe.</p>
 * 
 * @see RechercheChallenger
 * @see MasterMindChallenger
 * 
 * @author Coyote
 */
public abstract class AbstractModeChallenger extends AbstractMode {

	
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
	public AbstractModeChallenger(int nbrDeCases, int nbrEssais) {
		
		super(nbrDeCases, nbrEssais);
		tableauTentative = new int[nbrDeCases];
		tableauVictoire = new boolean[nbrDeCases];
		tableauCombinaison = new int[nbrDeCases];
		victoire = false;
	}
	
	/**
	 * Fonction qui définit le déroulement en détail de la <i>partie</i>.
	 * 
	 * @see AbstractModeChallenger#tentative()
	 * @see AbstractModeChallenger#testCombi()
	 */
	@Override
	public void partie() {

		// Affichage de la réponse pour debug
		if(dev) {
			System.out.print("" + Arrays.toString(tableauCombinaison) + "\n");
		}

		boolean loopPartie = true;
		int compteurDeManche = nbrEssais;

		// Déroulement de la partie et test du nombre de manches restantes
		do {
			if (compteurDeManche > 1) {
				System.out.println("\n" + compteurDeManche + " essais restants. ");
				
			} else if (compteurDeManche == 1) {
				System.out.println("\n" + compteurDeManche + " essai restant. ");
			}
			
			tentative();
			testCombi();
			compteurDeManche--;

			if (compteurDeManche <= 0) {
				loopPartie = false;
			}

		} while (!victoire && loopPartie);
		

		if (victoire) {
			System.out.println("\nBravo ! Vous avez trouvé la combinaison ! ");
			
		} else if (!loopPartie) {
			System.out.println("\nDommage ! Vous avez perdu... La bonne combinaison était : ");
			System.out.println(Arrays.toString(tableauCombinaison));
		}	
	}

	/**
	 * Récupération dans un tableau de la tentative du joueur.
	 * 
	 * @see AbstractModeChallenger#tableauTentative
	 */
	public abstract void tentative();
	
	/**
	 * Compare la tentative avec la combinaison et affiche le résultat.
	 */
	public abstract void testCombi();
	
}
