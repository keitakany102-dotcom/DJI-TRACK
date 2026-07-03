package com.Somagep.repository;


import com.Somagep.entity.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {
    List<Paiement> findByFactureClientId(Long clientId);
}
