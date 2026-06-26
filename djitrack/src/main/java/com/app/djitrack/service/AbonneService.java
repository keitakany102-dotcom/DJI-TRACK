package com.app.djitrack.service;

import com.app.djitrack.entity.Abonne;
import com.app.djitrack.repository.AbonneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AbonneService {

    private final AbonneRepository repository;

    public List<Abonne> findAll() {
        return repository.findAll();
    }

    public Abonne save(Abonne abonne) {
        return repository.save(abonne);
    }
}