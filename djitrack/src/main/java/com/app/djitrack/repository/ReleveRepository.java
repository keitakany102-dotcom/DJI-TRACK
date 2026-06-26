package com.app.djitrack.repository;

import com.app.djitrack.entity.ReleveCompteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReleveRepository
        extends JpaRepository<ReleveCompteur, Long> {
}
