package com.app.djitrack.repository;

import com.app.djitrack.entity.Role;
import com.app.djitrack.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    Optional<Utilisateur> findByEmail(String email);

    List<Utilisateur> findByRole(Role role);

    long countByRole(Role role);

    List<Utilisateur> findByNomContainingOrEmailContaining(String nom, String email);

    boolean existsByEmail(String email);
}