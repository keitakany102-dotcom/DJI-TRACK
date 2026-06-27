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
        return genererRapport("STATISTIQUE");
    }

    public Rapport genererRapport(String type){
        Rapport rapport = new Rapport();
        String rapportType = (type != null && !type.isEmpty()) ? type : "STATISTIQUE";
        rapport.setType(rapportType);
        rapport.setDateGeneration(LocalDate.now());
        rapport.setContenu("Rapport " + rapportType + " généré le " + LocalDate.now());
        return repository.save(rapport);
    }
}
