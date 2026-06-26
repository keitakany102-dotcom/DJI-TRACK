package com.app.djitrack.service;

import com.app.djitrack.entity.ReleveCompteur;
import com.app.djitrack.repository.ReleveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReleveService {

    private final ReleveRepository repository;

    public List<ReleveCompteur> getAll(){
        return repository.findAll();
    }

    public ReleveCompteur save(
            ReleveCompteur releve){

        releve.setConsommation(
                releve.getNouvelleValeur()
                        - releve.getAncienneValeur());

        return repository.save(releve);
    }
}
