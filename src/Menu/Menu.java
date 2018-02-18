package Menu;
//tes
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {

	BufferedReader br;
	int selection;
	Connect baseDonnees = new Connect();
	boolean boucle = true;

	public Menu() {
		menuPrincipal();
		br = new BufferedReader(new InputStreamReader(System.in));
		try {
			boucle = true;
			while (boucle) {
				selection = Integer.parseInt(br.readLine());
				switch (selection) {
				case 1:
					sousMenu(1);
					break;
				case 2:
					sousMenu(2);
					break;
				case 3:
					sousMenu(3);
					break;
				case 4:
					sousMenu(4);
					break;
				case 5:
					sousMenu(5);
					break;
				case 6:
					sousMenu(6);
					break;
				case 7:
					sousMenu(7);
					break;
				case 8:
					quitterApplication();
					break;
				default:
					continue;
				}

			}
		} catch (IOException ioe) {
			System.out.println("Erreur d'entrée-sortie:" + ioe);
			System.exit(1);
		} catch (NumberFormatException nfe) {
			System.out.println("Erreur de format " + nfe);
			new Menu();
		}
	}

	public void menuPrincipal() {
		System.out.println("");
		System.out.println("Menu principal");
		System.out.println("---------------------------");
		System.out.println("1. Affichage de la liste de tous les étudiants de l’école");
		System.out.println("2. Affichage de la liste des étudiants d’un cours");
		System.out.println("3. Ajout d’un nouvel étudiant");
		System.out.println("4. Suppression d’un étudiant");
		System.out.println("5. Inscription d’un étudiant à un cours");
		System.out.println("6. Désinscription d’un étudiant à un cours");
		System.out.println("7. Entrée ou modification de la note d’un étudiant à un cours");
		System.out.println("8. Quitter");
		System.out.println("----------------------------");
		System.out.println("");
		System.out.print("SVP, sélectionner une option de 1 à 8");
		System.out.println("");
		System.out.println("");
	}

	public void sousMenu(int menuPrincipalChoix) {

		switch (menuPrincipalChoix) {
		case 1:
			ItemMenu item1 = new ItemMenu();
			item1.afficherListEtudiantComplet();
			break;

		case 2:
			// 2. Affichage des clients ayant loué un film
			String cour;
			ItemMenu item2 = new ItemMenu();
			cour = item2.afficherListCours();
			item2.afficherListEtudiantPourCour(cour);
			break;
		case 3:
			ItemMenu item3 = new ItemMenu();
			item3.ajouterEtudiant();
			break;
		case 4:
			// 4. Supprimer etudiant
			ItemMenu item4 = new ItemMenu();
			String nomEtudiant;
			nomEtudiant = item4.choisirEtudiant();
			item4.supprimerEtudiant(nomEtudiant);
			
			break;
		case 5:
			// 5. Nouvelle inscription
			ItemMenu item5 = new ItemMenu();
			nomEtudiant = item5.choisirEtudiant();
			cour = item5.afficherListCours();
			item5.faireInscription(nomEtudiant, cour);
			break;
		case 6:
			ItemMenu item6 = new ItemMenu();
			nomEtudiant = item6.choisirEtudiant();
			cour = item6.afficherListCours();
			item6.desinscription(nomEtudiant, cour);
			break;
		case 7:
			// 7. Entrée ou modifié la note
			ItemMenu item7 = new ItemMenu();
			nomEtudiant = item7.choisirEtudiant();
			cour = item7.afficherListCours();
			item7.modifierNote(nomEtudiant, cour);
			break;
		
		case 8:
			quitterApplication();
			break;
		}	

	}

	public void quitterApplication() {
		System.out.println("Au revoir");
		System.exit(0);
	}

	public static void main(String[] args) {
		new Menu();
	}
}
