package com.Somagep.repository;


import com.Somagep.entity.Tarif;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TarifRepository extends JpaRepository<Tarif, Long> {
    List<Tarif> findByTypeClient(String typeClient);
}