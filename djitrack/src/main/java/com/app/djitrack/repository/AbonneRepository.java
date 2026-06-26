package com.app.djitrack.repository;

import com.app.djitrack.entity.Abonne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbonneRepository extends JpaRepository<Abonne, Long> {

}