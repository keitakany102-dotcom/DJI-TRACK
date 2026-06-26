package com.app.djitrack.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Consommation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate periodeDebut;

    private LocalDate periodeFin;

    private Double consommationTotale;

    private Double montantEstime;

    @ManyToOne
    private Abonne abonne;

    public Double calculerConsommation(){
        return consommationTotale;
    }
}