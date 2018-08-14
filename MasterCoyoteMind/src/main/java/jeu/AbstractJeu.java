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

import java.util.Locale;
import java.util.Scanner;

import main.Main;
import mode.AbstractMode;
import mode.mastermind.MasterMindChallenger;
import mode.mastermind.MasterMindDefenseur;
import mode.mastermind.MasterMindDuel;
import mode.recherche.RechercheChallenger;
import mode.recherche.RechercheDefenseur;
import mode.recherche.RechercheDuel;

/**
 * Classe abstraite qui permet de mettre en commun les codes sources identiques 
 * des classes <code>JeuRecherche</code> et <code>JeuMasterMind</code>.
 * 
 * <p>Ces classes gèrent l'application au niveau du <i>type</i> de jeu et se chargent
 * de créer l'objet adéquat en fonction du <i>mode</i> sélectionné.</p>
 * 
 * @see JeuRecherche
 * @see JeuMasterMind
 * 
 * @author Coyote
 */
public abstract class AbstractJeu {
	

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
	 * Détermine le <i>mode</i> de jeu entre :
	 * <ul>
	 * <li>1 : Challenger</li>
	 * <li>2 : Defenseur</li>
	 * <li>3 : Duel</li>
	 * </ul>
	 */
	protected int mode;
	/**
	 * Objet qui permet l'instanciation de la classe correspondante au <i>mode</i> de jeu sélectionné.
	 * 
	 * @see AbstractJeu#mode
	 * 
	 * @see RechercheChallenger
	 * @see RechercheDefenseur
	 * @see RechercheDuel
	 * @see MasterMindChallenger
	 * @see MasterMindDefenseur
	 * @see MasterMindDuel
	 */
	protected AbstractMode modeDeJeu;
	
	
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
	public AbstractJeu(int nbrDeCases, int nbrEssais, int mode) {
		
		scan = Main.getScanner();
		dev = Main.isDev();
		
		this.nbrDeCases = nbrDeCases;
		this.nbrEssais = nbrEssais;
		this.mode = mode;
	}
	
	/**
	 * Lance l'initialisation du <i>jeu</i> en créant l'objet <code>modeDeJeu</code>
	 * qui correspond au <i>mode</i> de jeu sélectionné.
	 * Et lance la fonction <code>init()</code> de ce dernier.
	 * 
	 * @see AbstractJeu#modeDeJeu
	 * @see AbstractJeu#mode
	 */
	public abstract void init();
	
	/**
	 * Fonction qui demande au joueur si il veut recommencer la partie.
	 * 
	 * @return Le choix du joueur : 
	 * <ul>
	 * <li><code>true</code> : recommencer</li>
	 * <li><code>false</code> : quitter</li>
	 * </ul>
	 */
	public boolean retry() {
		
		boolean retry = false;
		boolean loop = true;
		String scanChar;
		
		do {
			System.out.println("\n Voulez-vous recommencer la partie ? o/n");
			
			try {
				scanChar = scan.next();
				scanChar = scanChar.toLowerCase(Locale.FRANCE);
				
				if (scanChar.charAt(0) == 'o') {
					retry = true;
					loop = false;
					
				} else if (scanChar.charAt(0) == 'n') {
					loop = false;
					
				} else {
					System.err.println("\n Veuillez rentrer 'o' pour oui ou 'n' pour non !");
				}
				
			} catch (Exception e) {
				System.err.println("\n Veuillez rentrer 'o' pour oui ou 'n' pour non !");
				
			} finally {
				scan.nextLine();
			}
			
		} while (loop);
		
		return retry;
	}
	
}
