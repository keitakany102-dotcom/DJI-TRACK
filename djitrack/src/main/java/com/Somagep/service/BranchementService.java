package com.Somagep.service;


import com.Somagep.entity.Branchement;
import com.Somagep.entity.Client;
import com.Somagep.entity.Equipe;
import com.Somagep.repository.BranchementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class BranchementService {

    @Autowired
    private BranchementRepository branchementRepository;

    @Autowired
    private NotificationService notificationService;

    @Transactional
    public Branchement demandeBranchement(Client client, String etudeTechnique) {
        Branchement b = new Branchement();
        b.setClient(client);
        b.setDateDemande(new Date());
        b.setStatut("NOUVELLE");
        b.setEtudeTechnique(etudeTechnique);
        return branchementRepository.save(b);
    }

    @Transactional
    public Branchement validerEtude(Long branchementId) {
        Branchement b = branchementRepository.findById(branchementId).orElseThrow();
        b.setStatut("ETUDE_TECHNIQUE");
        return branchementRepository.save(b);
    }

    @Transactional
    public Branchement genererDevis(Long branchementId, Double montant) {
        Branchement b = branchementRepository.findById(branchementId).orElseThrow();
        b.setMontantDevis(BigDecimal.valueOf(montant));
        b.setStatut("DEVIS_ENVOYE");
        return branchementRepository.save(b);
    }

    @Transactional
    public Branchement affecterEquipe(Long branchementId, Equipe equipe) {
        Branchement b = branchementRepository.findById(branchementId).orElseThrow();
        b.setEquipe(equipe);
        b.setStatut("TRAVAUX");
        b.setDateDebutTravaux(new Date());
        return branchementRepository.save(b);
    }

    @Transactional
    public Branchement miseEnService(Long branchementId) {
        Branchement b = branchementRepository.findById(branchementId).orElseThrow();
        b.setStatut("MISE_EN_SERVICE");
        b.setDateMiseEnService(new Date());
        notificationService.envoyerNotification(b.getClient(),
                "Mise en service",
                "Votre branchement a été mis en service.");
        return branchementRepository.save(b);
    }
}
