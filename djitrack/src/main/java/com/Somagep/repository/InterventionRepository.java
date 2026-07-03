package com.Somagep.repository;


import com.Somagep.entity.Intervention;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InterventionRepository extends JpaRepository<Intervention, Long> {
    List<Intervention> findByEquipeId(Long equipeId);
    List<Intervention> findByStatut(String statut);
}