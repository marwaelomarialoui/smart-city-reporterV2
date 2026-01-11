package com.examen.signalement.view;

import com.examen.signalement.model.Signalement;
import com.examen.signalement.model.StatutSignalement;
import com.examen.signalement.service.SignalementService;

import java.util.List;
import java.util.Scanner;

public class Menu {

    private final SignalementService service;
    private final ConsoleView view;
    private final Scanner scanner;

    public Menu(SignalementService service, ConsoleView view) {
        this.service = service;
        this.view = view;
        this.scanner = new Scanner(System.in);
    }

    public void demarrer() {
        while (true) {
            afficherMenuPrincipal();
            String choix = scanner.nextLine();

            try {
                switch (choix) {
                    case "1":
                        ajouterSignalement();
                        break;
                    case "2":
                        modifierStatut();
                        break;
                    case "3":
                        supprimerSignalement();
                        break;
                    case "4":
                        afficherTous();
                        break;
                    case "5":
                        filtrerParStatut();
                        break;
                    case "6":
                        trierParDate();
                        break;
                    case "7":
                        afficherStatistiques();
                        break;
                    case "0":
                        view.afficherMessage("Au revoir !");
                        return;
                    default:
                        view.afficherErreur("Choix invalide.");
                }
            } catch (Exception e) {
                view.afficherErreur("Une erreur est survenue : " + e.getMessage());
                // e.printStackTrace(); // Optional
            }
        }
    }

    private void afficherMenuPrincipal() {
        System.out.println("\n=== SMART CITY REPORTER ===");
        System.out.println("1. Ajouter un signalement");
        System.out.println("2. Modifier le statut d'un signalement");
        System.out.println("3. Supprimer un signalement");
        System.out.println("4. Afficher tous les signalements");
        System.out.println("5. Filtrer par statut");
        System.out.println("6. Trier par date (récent -> ancien)");
        System.out.println("7. Afficher statistiques par type");
        System.out.println("0. Quitter");
        System.out.print("Choix : ");
    }

    private void ajouterSignalement() {
        Signalement s = view.saisirSignalement();
        service.ajouterSignalement(s);
        view.afficherMessage("Signalement ajouté avec succès !");
    }

    private void modifierStatut() {
        Long id = view.saisirIdSignalement();
        StatutSignalement statut = view.saisirNouveauStatut();
        if (statut != null) {
            service.modifierStatut(id, statut);
            view.afficherMessage("Statut modifié avec succès !");
        } else {
            view.afficherErreur("Statut invalide.");
        }
    }

    private void supprimerSignalement() {
        Long id = view.saisirIdSignalement();
        service.supprimerSignalement(id);
        view.afficherMessage("Signalement supprimé avec succès !");
    }

    private void afficherTous() {
        List<Signalement> liste = service.afficherTous();
        view.afficherSignalements(liste);
    }

    private void filtrerParStatut() {
        StatutSignalement statut = view.saisirNouveauStatut(); // Reusing method
        if (statut != null) {
            List<Signalement> liste = service.filtrerParStatut(statut);
            view.afficherSignalements(liste);
        } else {
            view.afficherErreur("Statut invalide.");
        }
    }

    private void trierParDate() {
        List<Signalement> liste = service.trierParDate();
        view.afficherSignalements(liste);
    }

    private void afficherStatistiques() {
        view.afficherCompteurParType(service.compterParType());
    }
}
