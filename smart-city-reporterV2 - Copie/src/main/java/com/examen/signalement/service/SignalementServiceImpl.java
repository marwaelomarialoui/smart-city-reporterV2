package com.examen.signalement.service;

import com.examen.signalement.dao.SignalementDAO;
import com.examen.signalement.dao.SignalementDAOImpl;
import com.examen.signalement.exception.SignalementNotFoundException;
import com.examen.signalement.model.Signalement;
import com.examen.signalement.model.StatutSignalement;
import com.examen.signalement.util.StreamUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service

public class SignalementServiceImpl implements SignalementService {

    private final SignalementDAO signalementDAO;

    public SignalementServiceImpl() {
        this.signalementDAO = new SignalementDAOImpl();
    }

    @Override
    public Signalement ajouterSignalement(Signalement signalement) {
        return signalementDAO.save(signalement);
    }

    @Override
    public void modifierStatut(Long id, StatutSignalement nouveauStatut) {
        Signalement signalement = signalementDAO.findById(id);
        if (signalement == null) {
            throw new SignalementNotFoundException("Signalement avec l'ID " + id + " non trouvé.");
        }
        signalement.setStatut(nouveauStatut);
        signalementDAO.update(signalement);
    }

    @Override
    public void supprimerSignalement(Long id) {
        Signalement signalement = signalementDAO.findById(id);
        if (signalement == null) {
            throw new SignalementNotFoundException("Signalement avec l'ID " + id + " non trouvé pour suppression.");
        }
        signalementDAO.delete(signalement);
    }

    @Override
    public  List<Signalement>afficherTous() {
        return signalementDAO.findAll();
    }

    @Override
    public List<Signalement> filtrerParStatut(StatutSignalement statut) {
        List<Signalement> tous = signalementDAO.findAll();
        return StreamUtils.filtrerParStatut(tous, statut);
    }

    @Override
    public List<Signalement> trierParDate() {
        List<Signalement> tous = signalementDAO.findAll();
        return StreamUtils.trierParDate(tous);
    }

    @Override
    public Map<String, Long> compterParType() {
        List<Signalement> tous = signalementDAO.findAll();
        return StreamUtils.compterParType(tous);
    }

    @Override
    public void modifierSignalement(Signalement signalement) {
        signalementDAO.update(signalement);
    }
}
