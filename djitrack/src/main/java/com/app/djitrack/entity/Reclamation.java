package com.app.djitrack.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Reclamation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String objet;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String statut; // EN ATTENTE, TRAITEE, REJETEE

    private LocalDate dateDepot;

    @Column(columnDefinition = "TEXT")
    private String reponse; // Réponse de l'agent/admin

    private String repondantNom; // Nom de l'agent/admin qui a répondu

    private LocalDate dateReponse;

    @ManyToOne
    private Abonne abonne;
}
