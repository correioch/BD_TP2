package Menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.UnexpectedException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class ItemMenu{

	BufferedReader br;
	int selection;
	Connect baseDonnees = new Connect();
	boolean boucle = true;
	
	
	public void afficherListEtudiantComplet () {
		// 1. Affichage de la liste de tous les étudiants de l’école

		System.out.println("");
		System.out.println("---------------------------");
		System.out.println("liste de tous les étudiants de l’école\n");
		System.out.println(baseDonnees.traiterLigne(
				"SELECT e.NumeroDossier, e.Nom, e.CodePermanent, AVG(i.Note) FROM etudiant AS e "
						+ "INNER JOIN inscription AS i "
						+ "ON i.NumeroDossier = e.NumeroDossier "
						+ "WHERE i.Note NOT IN (SELECT note FROM inscription WHERE Note = NULL) "
						+ "GROUP BY e.Nom "
						+ "ORDER BY e.NumeroDossier;" ));
		
		retouner();
		
	}
	
	public void calculMoyenne(String titreCour) {
	
		
		System.out.println("La note mpyenne est: " + baseDonnees.traiterLigne(
				"SELECT AVG(i.Note) FROM inscription AS i "
						+ "INNER JOIN cours AS c "
						+ "ON i.Id_cours = c.Id_cours "
						+ "WHERE i.Note NOT IN (SELECT note FROM inscription WHERE Note = NULL) "
						+ "AND c.Titre = " + "'" + titreCour + "'" ));
	}
	
	public String choisirEtudiant() {
		System.out.println("");
		ArrayList<String> choiceList = new ArrayList<String>();
		choiceList = baseDonnees.traiterColonne("SELECT Nom FROM etudiant ORDER BY Nom;");

		int j = 0;
		for (String choice : choiceList) {
			System.out.print(++j);
			System.out.print(") ");
			System.out.println(choice);
		}

		System.out.println("---------------------------");
		System.out.print("SVP, sélectionner une option de 1 à " + String.valueOf(choiceList.size()));
		System.out.println("");
		boucle = true;
		while (boucle) {
			br = new BufferedReader(new InputStreamReader(System.in));
			try {
				selection = Integer.parseInt(br.readLine());
				if (selection < 1 || selection > choiceList.size()) {
					System.out.println("Mauvaise sélection");
					System.out.print("SVP, sélectionner une option de 1 à " + String.valueOf(choiceList.size()));
					continue;
				} else {
					return choiceList.get(selection - 1);
				}	
			}catch (IOException ioe) {
				System.out.println("Erreur d'entrée-sortie." + ioe);
				System.exit(1);
			}
		}	
		return "null";
	}
	
	public void ajouterEtudiant() {
		System.out.print("Entréee nom: ");
		Scanner scanner = new Scanner(System.in);
		String nomEtudiant = scanner.nextLine();
		System.out.print("Entréee prenom: ");
		String prenomEtudiant = scanner.nextLine();
		System.out.print("Entréee code permanent: ");
		String cp = scanner.nextLine();

		baseDonnees.traiterLigne("INSERT INTO etudiant (CodePermanent, Nom) " + "VALUES (" + cp + ", +  '"
				+ prenomEtudiant + " " + nomEtudiant + "'" + ");");
		
		retouner();
	}
	
	public void supprimerEtudiant(String nomEtudiant) {
		
		baseDonnees.traiterLigne("DELETE FROM inscription " + "WHERE NumeroDossier = "
				+ "(SELECT NumeroDossier FROM etudiant " + "WHERE nom = '"
				+ nomEtudiant + "'" + ")");

		baseDonnees.traiterLigne(
				"DELETE FROM etudiant " 
				+ "WHERE nom = '" + nomEtudiant + "'");
		System.out.println("Eudiant supprimé");
		retouner();
		
	}
	
	public void afficherListEtudiantPourCour(String titreCour) {

		System.out.println("");
		System.out.println("---------------------------");
		System.out.println("liste de tous les étudiants dans le cours " +titreCour +  "\n");
		System.out.println(baseDonnees.traiterLigne(
				"SELECT e.NumeroDossier, e.Nom, e.CodePermanent, Note, AVG(i.Note) FROM etudiant AS e "
						+ "INNER JOIN inscription AS i "
						+ "ON i.NumeroDossier = e.NumeroDossier "
						+ "INNER JOIN cours AS c "
						+ "ON i.Id_cours = c.Id_cours "
						+ "WHERE i.Note NOT IN (SELECT note FROM inscription WHERE Note = NULL) "
						+ "AND c.Titre = " + "'" + titreCour + "'"
						+ " GROUP BY e.Nom "
						+ "ORDER BY e.NumeroDossier;" ));
		
		calculMoyenne(titreCour);
		
		retouner();
	}
	
	
	public String afficherListCours(String nomEtudiant) {
		System.out.println("");
		ArrayList<String> choiceList = new ArrayList<String>();
		if (nomEtudiant == "vide") {
			choiceList = baseDonnees.traiterColonne("SELECT Titre FROM cours "
													+ "ORDER BY Titre;");
		}else {
			choiceList = baseDonnees.traiterColonne("SELECT c.Titre FROM cours c "
													+"INNER JOIN inscription i "
													+"ON i.Id_cours = c.Id_cours "
													+"INNER JOIN etudiant e "
													+"ON e.numeroDossier = i.NumeroDossier "
													+ "WHERE e.Nom = '" + nomEtudiant + "'"
													+ " ORDER BY Titre;");
		}

		int i = 0;
		for (String choice : choiceList) {
			System.out.print(++i);
			System.out.print(") ");
			System.out.println(choice);
		}

		System.out.println("---------------------------");
		System.out.print("SVP, sélectionner une option de 1 à " + String.valueOf(choiceList.size()));
		System.out.println("");
		boucle = true;
		while (boucle) {
			br = new BufferedReader(new InputStreamReader(System.in));
			try {
				selection = Integer.parseInt(br.readLine());
				if (selection < 1 || selection > choiceList.size()) {
					System.out.println("Mauvaise sélection");
					System.out.print("SVP, sélectionner une option de 1 à " + String.valueOf(choiceList.size()));
					continue;
				} else {
					System.out.println(choiceList.get(selection - 1));
					return(choiceList.get(selection - 1));
					//afficherListEtudiantPourCour(choiceList.get(selection - 1));
				}
			}

			catch (IOException ioe) {
				System.out.println("Erreur d'entrée-sortie." + ioe);
				System.exit(1);
			}
		}
		return "null";
	}
	
	public void faireInscription(String nomEtudiant, String cour) {
			baseDonnees.traiterLigne("INSERT INTO inscription (NumeroDossier, Id_cours) " + "VALUES ("
					+ "(SELECT NumeroDossier FROM etudiant " + "WHERE nom = '"
					+ nomEtudiant + "'" + "), " + "(SELECT Id_cours FROM cours "
					+ "WHERE Titre = '" + cour + "'" + "));");
			System.out.println("Inscription efetué");
			retouner();
			
	}
	
	public void desinscription(String nomEtudiant, String cour) {
		// 6. Supprimer inscription
					
		baseDonnees.traiterLigne("DELETE FROM inscription " 
				+ "WHERE NumeroDossier = "
				+ "(SELECT NumeroDossier FROM etudiant " 
				+ "WHERE nom = '"
				+ nomEtudiant + "'" 
				+ ") "
				+ "AND Id_cours = "
				+ "(SELECT Id_cours FROM cours " 
				+ "WHERE titre = '"
				+ cour + "'" 
				+ ")");
		System.out.println("Inscription supprimé");
		retouner();
	}
	
	public void modifierNote(String nomEtudiant, String cour) {
		Scanner sc = new Scanner(System.in);
		int note;
		System.out.print("Entrée la note: ");
		note = sc.nextInt();
		if (note>=0 && note<=100) {
			baseDonnees.traiterLigne("UPDATE inscription SET note =  " 
					+ note
					+ " WHERE NumeroDossier = " 
					+ "(SELECT NumeroDossier FROM etudiant "
					+ "WHERE nom = '"
					+ nomEtudiant
					+ "'" + ") AND " 
					+ "Id_cours = "
					+ "(SELECT Id_cours FROM cours "
					+ "WHERE Titre = '" 
					+ cour 
					+ "'" + ");");
			System.out.println("Note modifié");
		}else {
			System.out.println("Note invalide");
		}
		retouner();
	}
	
	public void retouner() {
		System.out.println("1. Retour au menu principal");
		System.out.println("");
		boucle = true;
		while (boucle) {
			br = new BufferedReader(new InputStreamReader(System.in));
			try {
				selection = Integer.parseInt(br.readLine());
				switch (selection) {
				case 1:
					new Menu();
					break;
				default:
					continue;
				}
			} catch (IOException ioe) {
				System.out.println("Erreur d'entrée-sortie." + ioe);
				System.exit(1);
			}
		}
	}
	
}
