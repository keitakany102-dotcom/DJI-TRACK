package com.app.djitrack.service;

import com.app.djitrack.entity.Rapport;
import com.app.djitrack.repository.RapportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RapportService {

    private final RapportRepository repository;

    public List<Rapport> getAll(){
        return repository.findAll();
    }

    public Rapport genererRapport(){

        Rapport rapport = new Rapport();

        rapport.setType("STATISTIQUE");
        rapport.setDateGeneration(LocalDate.now());
        rapport.setContenu("Rapport généré automatiquement");

        return repository.save(rapport);
    }
}