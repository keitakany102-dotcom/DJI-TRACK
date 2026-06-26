package com.app.djitrack.repository;

import com.app.djitrack.entity.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FactureRepository extends JpaRepository<Facture, Long> {

    List<Facture> findByStatut(String statut);

    List<Facture> findByAbonneId(Long abonneId);

    List<Facture> findByDateEmission(String dateEmission);

    long countByStatut(String statut);

    @Query("SELECT f FROM Facture f WHERE f.numero LIKE %:keyword% OR f.abonne.utilisateur.nom LIKE %:keyword%")
    List<Facture> search(@Param("keyword") String keyword);
}