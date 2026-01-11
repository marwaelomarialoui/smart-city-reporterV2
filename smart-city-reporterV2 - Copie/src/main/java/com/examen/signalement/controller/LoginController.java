package com.examen.signalement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        // Simulation d'authentification simple
        if ("admin".equals(username) && "admin123".equals(password)) {
            session.setAttribute("user", "admin");
            session.setAttribute("role", "ADMIN");
            return "redirect:/admin/signalements";
        } else if ("user".equals(username) && "user123".equals(password)) {
            session.setAttribute("user", "user");
            session.setAttribute("role", "USER");
            return "redirect:/user/profil";
        } else {
            redirectAttributes.addFlashAttribute("error", "Identifiants incorrects !");
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/accueil";
    }
}
