package com.Somagep.repository;

import com.Somagep.entity.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface FactureRepository extends JpaRepository<Facture, Long> {
    List<Facture> findByClientId(Long clientId);
    List<Facture> findByClientIdAndStatut(Long clientId, String statut);
    @Query("SELECT f FROM Facture f WHERE f.dateEcheance < CURRENT_DATE AND f.statut = 'impayée'")
    List<Facture> findImpayeesEnRetard();
}
