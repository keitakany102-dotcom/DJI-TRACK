package com.Somagep.service;

import com.Somagep.entity.Equipe;
import com.Somagep.repository.EquipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EquipeService {

    @Autowired
    private EquipeRepository equipeRepository;

    public List<Equipe> findAll() {
        return equipeRepository.findAll();
    }

    public Optional<Equipe> findById(Long id) {
        return equipeRepository.findById(id);
    }

    public Equipe save(Equipe equipe) {
        return equipeRepository.save(equipe);
    }
}
