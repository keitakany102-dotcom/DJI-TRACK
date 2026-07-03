package com.Somagep.service;


import com.Somagep.entity.Client;
import com.Somagep.entity.Compteur;
import com.Somagep.entity.HistoriqueCompteur;
import com.Somagep.entity.User;
import com.Somagep.repository.CompteurRepository;
import com.Somagep.repository.HistoriqueCompteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class CompteurService {

    @Autowired
    private CompteurRepository compteurRepository;

    @Autowired
    private HistoriqueCompteurRepository historiqueRepository;

    @Transactional
    public Compteur installerCompteur(Client client, String numeroSerie, String type, String localisationGPS, User agent) {
        Compteur c = new Compteur();
        c.setClient(client);
        c.setNumeroSerie(numeroSerie);
        c.setType(type);
        c.setEtat("actif");
        c.setLocalisationGPS(localisationGPS);
        c.setDateInstallation(new Date());
        Compteur saved = compteurRepository.save(c);

        HistoriqueCompteur h = new HistoriqueCompteur();
        h.setCompteur(saved);
        h.setAction("INSTALLATION");
        h.setDateAction(new Date());
        h.setAgent(agent);
        h.setDescription("Installation initiale");
        historiqueRepository.save(h);

        return saved;
    }

    @Transactional
    public Compteur remplacerCompteur(Long ancienId, String nouveauNumero, String type, User agent) {
        Compteur ancien = compteurRepository.findById(ancienId).orElseThrow();
        ancien.setEtat("retiré");
        compteurRepository.save(ancien);

        Compteur nouveau = new Compteur();
        nouveau.setClient(ancien.getClient());
        nouveau.setNumeroSerie(nouveauNumero);
        nouveau.setType(type);
        nouveau.setEtat("actif");
        nouveau.setDateInstallation(new Date());
        Compteur saved = compteurRepository.save(nouveau);

        HistoriqueCompteur h = new HistoriqueCompteur();
        h.setCompteur(ancien);
        h.setAction("REMPLACEMENT");
        h.setDateAction(new Date());
        h.setAgent(agent);
        h.setDescription("Remplacé par " + nouveauNumero);
        historiqueRepository.save(h);

        return saved;
    }

    public List<Compteur> findByClient(Long clientId) {
        return compteurRepository.findByClientId(clientId);
    }
}