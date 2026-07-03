package com.Somagep.service;

import com.Somagep.entity.Tarif;
import com.Somagep.repository.TarifRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TarifService {

    @Autowired
    private TarifRepository tarifRepository;

    public List<Tarif> findAll() {
        return tarifRepository.findAll();
    }

    public Optional<Tarif> findById(Long id) {
        return tarifRepository.findById(id);
    }

    public Tarif save(Tarif tarif) {
        return tarifRepository.save(tarif);
    }

    public void delete(Long id) {
        tarifRepository.deleteById(id);
    }
}
