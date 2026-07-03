package com.Somagep.repository;

import com.Somagep.entity.Agence;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AgenceRepository extends JpaRepository<Agence, Long> {
    List<Agence> findByNomContaining(String nom);
}
