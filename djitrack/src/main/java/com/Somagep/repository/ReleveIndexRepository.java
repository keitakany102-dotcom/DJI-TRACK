package com.Somagep.repository;


import com.Somagep.entity.ReleveIndex;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReleveIndexRepository extends JpaRepository<ReleveIndex, Long> {
    List<ReleveIndex> findByCompteurIdOrderByDateReleveDesc(Long compteurId);
    List<ReleveIndex> findByAgentId(Long agentId);
}