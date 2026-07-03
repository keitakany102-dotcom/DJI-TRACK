package com.Somagep.repository;


import com.Somagep.entity.Reclamation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReclamationRepository extends JpaRepository<Reclamation, Long> {
    List<Reclamation> findByClientId(Long clientId);
    List<Reclamation> findByAgentId(Long agentId);
    List<Reclamation> findByStatut(String statut);
}