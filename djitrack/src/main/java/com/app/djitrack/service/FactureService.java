package com.app.djitrack.service;

import com.app.djitrack.entity.Facture;
import com.app.djitrack.repository.FactureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FactureService {

    private final FactureRepository factureRepository;

    public List<Facture> findAll() {
        return factureRepository.findAll();
    }

    public Optional<Facture> getById(Long id) {
        return factureRepository.findById(id);
    }

    public Facture save(Facture facture) {
        return factureRepository.save(facture);
    }

    public void deleteById(Long id) {
        factureRepository.deleteById(id);
    }

    public List<Facture> findByStatut(String statut) {
        return factureRepository.findByStatut(statut);
    }

    public List<Facture> findByAbonneId(Long abonneId) {
        return factureRepository.findByAbonneId(abonneId);
    }

    public List<Facture> findByDateEmission(String date) {
        return factureRepository.findByDateEmission(date);
    }

    public long count() {
        return factureRepository.count();
    }

    public long countByStatut(String statut) {
        return factureRepository.countByStatut(statut);
    }
}