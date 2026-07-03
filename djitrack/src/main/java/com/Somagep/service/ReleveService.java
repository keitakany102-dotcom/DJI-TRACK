package com.Somagep.service;


import com.Somagep.entity.Compteur;
import com.Somagep.entity.ReleveIndex;
import com.Somagep.entity.User;
import com.Somagep.repository.CompteurRepository;
import com.Somagep.repository.ReleveIndexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

@Service
public class ReleveService {

    @Autowired
    private ReleveIndexRepository releveIndexRepository;

    @Autowired
    private CompteurRepository compteurRepository;

    @Transactional
    public ReleveIndex saveReleve(Long compteurId, double index, MultipartFile photo, String geolocalisation, User agent) {
        Compteur compteur = compteurRepository.findById(compteurId)
                .orElseThrow(() -> new RuntimeException("Compteur non trouvé"));

        String photoUrl = null;
        if (photo != null && !photo.isEmpty()) {
            try {
                String uploadDir = "uploads/releves/";
                Files.createDirectories(Paths.get(uploadDir));
                String fileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename();
                Path filePath = Paths.get(uploadDir + fileName);
                Files.write(filePath, photo.getBytes());
                photoUrl = filePath.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ReleveIndex releve = new ReleveIndex();
        releve.setCompteur(compteur);
        releve.setIndexReleve(index);
        releve.setDateReleve(new Date());
        releve.setPhotoUrl(photoUrl);
        releve.setGeolocalisation(geolocalisation);
        releve.setAgent(agent);
        releve.setValide(false);

        compteur.setDateDernierReleve(new Date());
        compteurRepository.save(compteur);

        return releveIndexRepository.save(releve);
    }

    public List<ReleveIndex> getRelevesByCompteur(Long compteurId) {
        return releveIndexRepository.findByCompteurIdOrderByDateReleveDesc(compteurId);
    }

    public List<ReleveIndex> findAll() {
        return releveIndexRepository.findAll();
    }

    public List<ReleveIndex> findByAgent(Long agentId) {
        return releveIndexRepository.findByAgentId(agentId);
    }
}