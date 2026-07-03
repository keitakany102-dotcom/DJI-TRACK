package com.Somagep.repository;


import com.Somagep.entity.Branchement;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BranchementRepository extends JpaRepository<Branchement, Long> {
    List<Branchement> findByClientId(Long clientId);
    List<Branchement> findByStatut(String statut);
}