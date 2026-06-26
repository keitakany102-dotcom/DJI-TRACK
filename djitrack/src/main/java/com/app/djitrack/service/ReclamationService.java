package com.app.djitrack.service;

import com.app.djitrack.entity.Reclamation;
import com.app.djitrack.repository.ReclamationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReclamationService {

    private final ReclamationRepository repository;

    public List<Reclamation> getAll(){
        return repository.findAll();
    }

    public Reclamation save(
            Reclamation reclamation){

        return repository.save(reclamation);
    }
}
