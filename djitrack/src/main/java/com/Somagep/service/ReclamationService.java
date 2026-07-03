package com.Somagep.service;


import com.Somagep.entity.Client;
import com.Somagep.entity.Reclamation;
import com.Somagep.entity.User;
import com.Somagep.repository.ReclamationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ReclamationService {

    @Autowired
    private ReclamationRepository reclamationRepository;

    @Autowired
    private NotificationService notificationService;

    @Transactional
    public Reclamation creerReclamation(Client client, String type, String description, String priorite) {
        Reclamation r = new Reclamation();
        r.setClient(client);
        r.setType(type);
        r.setDescription(description);
        r.setPriorite(priorite);
        r.setDateCreation(new Date());
        r.setStatut("ouverte");
        return reclamationRepository.save(r);
    }

    @Transactional
    public Reclamation assignerAgent(Long reclamationId, User agent) {
        Reclamation r = reclamationRepository.findById(reclamationId)
                .orElseThrow(() -> new RuntimeException("Réclamation non trouvée"));
        r.setAgent(agent);
        r.setStatut("en cours");
        return reclamationRepository.save(r);
    }

    @Transactional
    public Reclamation traiterReclamation(Long reclamationId, String commentaire, String photos) {
        Reclamation r = reclamationRepository.findById(reclamationId)
                .orElseThrow(() -> new RuntimeException("Réclamation non trouvée"));
        r.setCommentaires(commentaire);
        r.setPhotos(photos);
        r.setStatut("traitée");
        r.setDateCloture(new Date());
        Reclamation saved = reclamationRepository.save(r);
        notificationService.envoyerNotification(r.getClient(),
                "Réclamation traitée",
                "Votre réclamation du " + r.getDateCreation() + " a été traitée.");
        return saved;
    }

    public List<Reclamation> getByClient(Long clientId) {
        return reclamationRepository.findByClientId(clientId);
    }

    public List<Reclamation> getByAgent(Long agentId) {
        return reclamationRepository.findByAgentId(agentId);
    }

    public long countOuvertes() {
        return reclamationRepository.findByStatut("ouverte").size();
    }
}
