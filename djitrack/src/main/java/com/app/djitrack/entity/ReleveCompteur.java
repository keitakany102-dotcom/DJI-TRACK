package com.app.djitrack.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class ReleveCompteur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateReleve;

    private Double ancienneValeur;

    private Double nouvelleValeur;

    private Double consommation;

    @ManyToOne
    private Abonne abonne;
}