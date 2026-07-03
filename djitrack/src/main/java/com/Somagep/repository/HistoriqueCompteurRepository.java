package com.Somagep.repository;

import com.Somagep.entity.HistoriqueCompteur;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HistoriqueCompteurRepository extends JpaRepository<HistoriqueCompteur, Long> {
    List<HistoriqueCompteur> findByCompteurId(Long compteurId);
}
