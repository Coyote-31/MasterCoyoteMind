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

import java.util.Scanner;

import main.Main;
import mode.recherche.RechercheChallenger;
import mode.recherche.RechercheDefenseur;
import mode.recherche.RechercheDuel;
import mode.mastermind.MasterMindChallenger;
import mode.mastermind.MasterMindDefenseur;
import mode.mastermind.MasterMindDuel;

/**
 * Classe abstraite qui permet de mettre en commun les codes sources identiques 
 * des classes de la partie :
 * <ul>
 * <li><code>RechercheChallenger</code></li>
 * <li><code>RechercheDefenseur</code></li>
 * <li><code>RechercheDuel</code></li>
 * 
 * <li><code>MasterMindChallenger</code></li>
 * <li><code>MasterMindDefenseur</code></li>
 * <li><code>MasterMindDuel</code></li>
 * </ul>
 * 
 * <p>Ces classes gèrent l'application au niveau du <i>mode</i> de jeu et se chargent
 * du déroulement de la partie dans les détails propre à chaque classe.</p>
 * 
 * @see RechercheChallenger
 * @see RechercheDefenseur
 * @see RechercheDuel
 * @see MasterMindChallenger
 * @see MasterMindDefenseur
 * @see MasterMindDuel
 * 
 * @author Coyote
 */
public abstract class AbstractMode {

	
	/**
	 * Objet de la classe Scanner permettant de lire les entrées clavier de l'utilisateur.
	 */
	protected Scanner scan;
	/**
	 * Définit si le jeu est en mode developpeur pour le déboggage.
	 * <ul>
	 * <li><code>true</code> : Mode deboggage actif</li>
	 * <li><code>false</code> : Mode normal</li>
	 * </ul>
	 */
	protected boolean dev;
	/**
	 * Détermine le nombre de cases du plateau de jeu.
	 */
	protected int nbrDeCases;
	/**
	 * Détermine le nombre d'essais autorisés pour découvrir la suite adverse.
	 */
	protected int nbrEssais;
	
	
	/**
	 * Constructeur qui fait suivre les valeurs de configuration.
	 * 
	 * @param nbrDeCases : <code>int</code>
	 * @param nbrEssais : <code>int</code>
	 * 
	 * @see Main#nbrDeCases
	 * @see Main#nbrEssais
	 */
	public AbstractMode(int nbrDeCases, int nbrEssais) {
		
		scan = Main.getScanner();
		dev = Main.isDev();		
		this.nbrDeCases = nbrDeCases;
		this.nbrEssais = nbrEssais;

	}

	/**
	 * Lance l'initialisation de la <i>partie</i>.
	 * En créant les combinaisons à trouver et lance la fonction <code>partie()</code>.
	 * 
	 * @see AbstractMode#partie()
	 */
	public abstract void init();

	/**
	 * Fonction qui définit le déroulement en détail de la <i>partie</i>.
	 */
	public abstract void partie();

}
