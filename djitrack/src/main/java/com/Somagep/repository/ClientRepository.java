package com.Somagep.repository;

import com.Somagep.entity.Client;
import com.Somagep.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByNumeroClient(String numero);
    Optional<Client> findByUser(User user);
    List<Client> findByNomContainingOrPrenomContaining(String nom, String prenom);
    @Query("SELECT c FROM Client c WHERE c.actif = true")
    List<Client> findAllActifs();
}