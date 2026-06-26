package com.app.djitrack.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Facture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String numero;

    @Column(nullable = false)
    private Double montantTotal;

    @Column(nullable = false)
    private LocalDate dateEmission;

    private String statut; // PAYEE, EN ATTENTE, IMPAYEE

    @ManyToOne
    @JoinColumn(name = "abonne_id")
    private Abonne abonne;
}