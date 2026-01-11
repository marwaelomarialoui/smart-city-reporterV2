package com.examen.signalement.view;

import com.examen.signalement.model.Signalement;
import com.examen.signalement.model.StatutSignalement;
import com.examen.signalement.model.Utilisateur;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConsoleView {

    private final Scanner scanner;

    public ConsoleView() {
        this.scanner = new Scanner(System.in);
    }

    public void afficherMessage(String message) {
        System.out.println(message);
    }

    public void afficherErreur(String message) {
        System.err.println("ERREUR: " + message);
    }

    public Signalement saisirSignalement() {
        System.out.println("=== Nouveau Signalement ===");

        System.out.print("Titre: ");
        String titre = scanner.nextLine();

        System.out.print("Description: ");
        String description = scanner.nextLine();

        System.out.print("Type de problème (Route, Éclairage, Déchets, Eau, Autre): ");
        String type = scanner.nextLine();

        System.out.print("ID Utilisateur (ex: 1 ou 2): ");
        Long userId = Long.parseLong(scanner.nextLine());

        Utilisateur user = new Utilisateur();
        user.setId(userId); // Assuming user exists

        Signalement signalement = new Signalement();
        signalement.setTitre(titre);
        signalement.setDescription(description);
        signalement.setTypeProbleme(type);
        signalement.setStatut(StatutSignalement.EN_ATTENTE);
        signalement.setDateSignalement(LocalDate.now());
        signalement.setUtilisateur(user);

        return signalement;
    }

    public Long saisirIdSignalement() {
        System.out.print("Entrez l'ID du signalement: ");
        try {
            return Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1L;
        }
    }

    public StatutSignalement saisirNouveauStatut() {
        System.out.println("Choisissez le nouveau statut:");
        System.out.println("1. EN_ATTENTE");
        System.out.println("2. EN_COURS");
        System.out.println("3. RESOLU");
        System.out.print("Votre choix: ");

        String choix = scanner.nextLine();
        switch (choix) {
            case "1":
                return StatutSignalement.EN_ATTENTE;
            case "2":
                return StatutSignalement.APPROUVE;
            case "3":
                return StatutSignalement.REFUSE;
            default:
                return null;
        }
    }

    public void afficherSignalements(List<Signalement> signalements) {
        if (signalements == null || signalements.isEmpty()) {
            System.out.println("Aucun signalement trouvé.");
            return;
        }
        System.out.println("\n--- Liste des Signalements ---");
        // Using Stream for display as well (optional but clean)
        signalements.forEach(System.out::println);
        System.out.println("-------------------------------\n");
    }

    public void afficherCompteurParType(Map<String, Long> stats) {
        if (stats == null || stats.isEmpty()) {
            System.out.println("Aucune donnée statistique.");
            return;
        }
        System.out.println("\n--- Nombre de signalements par Type ---");
        stats.forEach((k, v) -> System.out.println(k + ": " + v));
        System.out.println("---------------------------------------\n");
    }
}
