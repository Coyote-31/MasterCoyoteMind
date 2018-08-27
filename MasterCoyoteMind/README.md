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
 
 
							README :
							
							
						MasterCoyoteMind
						
						
Programme de jeu en ligne de commande MasterMind et sa variante Recherche, pour entrainement au code Java.
Cette application a été développée avec Java (JDK 10.0.2) sous Eclipse.


Contient les modes de jeu suivants :

- Type Recherche : Deviner la combinaison de chiffres de 0 à 9. Avec les indices + / - / = .
	- Mode : 	Challenger -> Le joueur doit deviner la combinaison.
				Defenseur -> L'ordinateur doit trouver la combinaison.
				Duel -> Le joueur et l'ordinateur jouent à tour de role dans une même partie.

- Type MasterMind : Deviner la combinaison de chiffres de 0 à x. Avec les indices du jeu MasterMind.
	- Mode : 	Challenger -> Le joueur doit deviner la combinaison.
				Defenseur -> L'ordinateur doit trouver la combinaison.
				Duel -> Le joueur et l'ordinateur jouent à tour de role dans une même partie.				
	


Requis pour fonctionner :

- JRE JavaSE v.10 (ou +)
	http://www.oracle.com/technetwork/java/javase/downloads/jre10-downloads-4417026.html



Comment lancer l'application :

	Attention ! Problème possible si la variable d'environnement de java n'est pas configuré :
		Dans ce cas lire les instructions : https://www.java.com/fr/download/help/path.xml

	- Sous Windows :
	
		Méthode 1 :
		
			1. Avoir le JRE version 10 ou +.
			2. Double Cliquer sur MasterCoyoteMind.bat 
				ou MasterCoyoteMind_Debug.bat pour le mode développeur.
		
		Méthode 2 :
		
			1. Avoir le JRE version 10 ou +.
			2. Windows+R , rentrer la commande: "cmd" + Entrée
			3. Dans l'invite de commande taper : java -jar <chemin_d_accès>\MasterCoyoteMind\MasterCoyoteMind.jar
					ou pour le mode developpeur : java -jar <chemin_d_accès>\MasterCoyoteMind\MasterCoyoteMind.jar dev
		
	- Sous MacOS / Linux :
	
			1. Avoir le JRE version 10 ou +.
			2. Dans l'invite de commande taper : java -jar <chemin_d_accès>\MasterCoyoteMind\MasterCoyoteMind.jar
					ou pour le mode developpeur : java -jar <chemin_d_accès>\MasterCoyoteMind\MasterCoyoteMind.jar dev



Comment modifier les paramètres du jeu :

	Il faut modifier le fichier : configProperties.xml 
	Qui se trouve dans : MasterCoyoteMind\src\main\resources\configProperties.xml

	Paramètres :
	
		- nbrdecases value="x" : Définit le nombre de case de la combinaison.
			x = 1..8 
			Attention à partir de 7 cases le programme consomme énormement de mémoire
			avec le mode Defenseur et Duel du type MasterMind. (+/- 6go)
			
	    - nbressais value="x" : Définit le nombre d'essais autorisés pour trouver la combinaison.
	    	x = 1..20
	    
	    - nbrdecouleurs value="x" : Définit le nombre de couleurs pour le type de jeu MasterMind,
	    							même si dans ce programme les couleurs sont des chiffres de 0 à 9.
	    	x = 4..10
	    
	    - dev value="x" : 	Définit si l'application est en mode développeur avec l'affichage des réponses,
	    					ainsi que des indications sur l'état de l'IA durant une partie.
	    					- true : Mode developpeur actif
	    					- false : Mode normal

			x = true | false



Autres informations :

	- log : Le dossier log contient le fichier MasterCoyoteMind.log contenant les logs du dernier 
			lancement de l'application uniquement.

	- doc : Ce dossier contient toute la javadoc de l'application.
	
	- lib : Ce dossier contient les librairies pour le fontionnement des log avec log4j2.
			Il contient aussi le fichier log4j2.xml relatif à la configuration de ce dernier.



Contact :

	Pour toute question :
	- Me contacter sur GitHub : https://github.com/Coyote-31 ou coyotepouil@gmail.com