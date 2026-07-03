package com.Somagep.service;


import com.Somagep.entity.HistoriqueCompteur;
import com.Somagep.repository.HistoriqueCompteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HistoriqueService {

    @Autowired
    private HistoriqueCompteurRepository historiqueCompteurRepository;

    public List<HistoriqueCompteur> getByCompteur(Long compteurId) {
        return historiqueCompteurRepository.findByCompteurId(compteurId);
    }

    @Transactional
    public HistoriqueCompteur save(HistoriqueCompteur historique) {
        return historiqueCompteurRepository.save(historique);
    }
}
