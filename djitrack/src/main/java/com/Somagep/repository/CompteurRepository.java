package com.Somagep.repository;

import com.Somagep.entity.Compteur;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CompteurRepository extends JpaRepository<Compteur, Long> {
    List<Compteur> findByClientId(Long clientId);
    Optional<Compteur> findByNumeroSerie(String numeroSerie);
}
