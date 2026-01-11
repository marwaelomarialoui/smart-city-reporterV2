package com.examen.signalement.controller;

import com.examen.signalement.model.Signalement;
import com.examen.signalement.model.StatutSignalement;
import com.examen.signalement.service.SignalementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDate;

@Controller
public class SignalementWebController {

    @Autowired
    private SignalementService signalementService;

    // 1. Quand on tape localhost:8080/ ou localhost:8080/accueil
    @GetMapping({ "/", "/accueil" })
    public String pageAccueil() {
        return "accueil"; // Ouvre accueil.html
    }

    // 2. Page Admin (Liste des signalements)
    @GetMapping("/admin/signalements")
    public String listeAdmin(Model model) {
        model.addAttribute("liste", signalementService.afficherTous());
        return "index"; // Ouvre index.html (ton tableau admin)
    }

    // 3. Page Profil Utilisateur (Vue simplifiée)
    @GetMapping("/user/profil")
    public String profilUser(Model model) {
        // Pour la démo, on simule que l'utilisateur est celui qui a l'email
        // "alami@mail.com"
        // Ou on affiche tout mais en mode lecture seule
        model.addAttribute("liste", signalementService.afficherTous()); // Idéalement filtrer par email
        return "profil";
    }

    // Sauvegarde le formulaire avec tous les nouveaux champs
    @PostMapping("/ajouter")
    public String ajouterSignalement(
            @RequestParam String nom,
            @RequestParam String email,
            @RequestParam String telephone,
            @RequestParam String localisation,
            @RequestParam String type,
            @RequestParam String titre,
            RedirectAttributes redirectAttributes) { // <--- KHASS DARORI TZID HADA HNA

        Signalement s = new Signalement();
        s.setNom(nom);
        s.setEmail(email);
        s.setTelephone(telephone);
        s.setLocalisation(localisation);
        s.setTypeProbleme(type);
        s.setTitre(titre);
        s.setDescription("Signalement via formulaire client");
        s.setStatut(StatutSignalement.EN_ATTENTE);
        s.setDateSignalement(LocalDate.now());

        signalementService.ajouterSignalement(s);

        // Daba had l-line ghadi t-khdem bla error
        redirectAttributes.addFlashAttribute("success", "Votre signalement a été envoyé avec succès !");

        return "redirect:/accueil";
    }

    @GetMapping("/admin/accepter/{id}")
    public String accepterSignalement(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            // On utilise APPROUVE ici
            signalementService.modifierStatut(id, StatutSignalement.APPROUVE);
            redirectAttributes.addFlashAttribute("success", "Le signalement a été APPROUVÉ.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur : " + e.getMessage());
        }
        return "redirect:/admin/signalements";
    }

    @GetMapping("/admin/refuser/{id}")
    public String refuserSignalement(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            // On utilise REFUSE ici
            signalementService.modifierStatut(id, StatutSignalement.REFUSE);
            redirectAttributes.addFlashAttribute("success", "Le signalement a été REFUSÉ.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur : " + e.getMessage());
        }
        return "redirect:/admin/signalements";
    }
}