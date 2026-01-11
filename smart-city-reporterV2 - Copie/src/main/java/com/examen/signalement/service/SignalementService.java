package com.examen.signalement.service;

import com.examen.signalement.model.Signalement;
import com.examen.signalement.model.StatutSignalement;

import java.util.List;
import java.util.Map;

public interface SignalementService {
    Signalement ajouterSignalement(Signalement signalement);

    void modifierStatut(Long id, StatutSignalement nouveauStatut);

    void supprimerSignalement(Long id);

    List<Signalement> afficherTous();

    List<Signalement> filtrerParStatut(StatutSignalement statut);

    List<Signalement> trierParDate();

    Map<String, Long> compterParType();

    void modifierSignalement(Signalement signalement);
}
