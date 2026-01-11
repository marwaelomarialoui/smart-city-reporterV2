package com.examen.signalement.util;

import com.examen.signalement.model.Signalement;
import com.examen.signalement.model.StatutSignalement;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamUtils {

    /**
     * Filter signalements by status.
     */
    public static List<Signalement> filtrerParStatut(List<Signalement> signalements, StatutSignalement statut) {
        if (signalements == null) return null;
        return signalements.stream()
                .filter(s -> s.getStatut() == statut)
                .collect(Collectors.toList());
    }

    /**
     * Sort signalements by date (descending).
     */
    public static List<Signalement> trierParDate(List<Signalement> signalements) {
        if (signalements == null) return null;
        return signalements.stream()
                .sorted(Comparator.comparing(Signalement::getDateSignalement).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Count signalements by type.
     */
    public static Map<String, Long> compterParType(List<Signalement> signalements) {
        if (signalements == null) return null;
        return signalements.stream()
                .collect(Collectors.groupingBy(Signalement::getTypeProbleme, Collectors.counting()));
    }
}
