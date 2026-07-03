package com.Somagep.service;


import com.Somagep.entity.Coupure;
import com.Somagep.repository.CoupureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CoupureService {

    @Autowired
    private CoupureRepository coupureRepository;

    public List<Coupure> getAll() {
        return coupureRepository.findAll();
    }

    public Coupure getById(Long id) {
        return coupureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coupure non trouvée"));
    }

    @Transactional
    public Coupure save(Coupure coupure) {
        return coupureRepository.save(coupure);
    }

    @Transactional
    public void delete(Long id) {
        coupureRepository.deleteById(id);
    }
}
