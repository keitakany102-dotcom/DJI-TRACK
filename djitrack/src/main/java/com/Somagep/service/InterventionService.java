package com.Somagep.service;

import com.Somagep.entity.Intervention;
import com.Somagep.repository.InterventionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class InterventionService {

    @Autowired
    private InterventionRepository interventionRepository;

    public List<Intervention> getAll() {
        return interventionRepository.findAll();
    }

    public Intervention getById(Long id) {
        return interventionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Intervention non trouvée"));
    }

    @Transactional
    public Intervention save(Intervention intervention) {
        return interventionRepository.save(intervention);
    }

    @Transactional
    public Intervention terminer(Long id, String rapport) {
        Intervention intervention = getById(id);
        intervention.setStatut("terminée");
        intervention.setDateRealisation(new Date());
        intervention.setRapport(rapport);
        return interventionRepository.save(intervention);
    }

    public List<Intervention> getByEquipe(Long equipeId) {
        return interventionRepository.findByEquipeId(equipeId);
    }
}
