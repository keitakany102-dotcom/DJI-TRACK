package com.app.djitrack.repository;

import com.app.djitrack.entity.Rapport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RapportRepository
        extends JpaRepository<Rapport, Long> {
}