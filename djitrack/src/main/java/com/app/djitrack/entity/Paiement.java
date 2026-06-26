package com.app.djitrack.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double montant;

    private String reference;

    private String mode;

    private String statut;

    private LocalDate datePaiement;

    @ManyToOne
    @JoinColumn(name = "facture_id")
    private Facture facture;
}