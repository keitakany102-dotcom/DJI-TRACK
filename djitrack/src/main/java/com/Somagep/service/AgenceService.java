package com.Somagep.service;

import com.Somagep.entity.Agence;
import com.Somagep.repository.AgenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AgenceService {

    @Autowired
    private AgenceRepository agenceRepository;

    public List<Agence> findAll() {
        return agenceRepository.findAll();
    }

    public Optional<Agence> findById(Long id) {
        return agenceRepository.findById(id);
    }

    public Agence save(Agence agence) {
        return agenceRepository.save(agence);
    }

    public void delete(Long id) {
        agenceRepository.deleteById(id);
    }
}
