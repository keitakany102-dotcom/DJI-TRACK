package com.app.djitrack.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Abonnement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroAbonnement;

    private LocalDate dateDebut;

    private String statut;

    @OneToOne
    private ProfilConso profilConso;

    public void activer(){
        this.statut = "ACTIF";
    }

    public void desactiver(){
        this.statut = "INACTIF";
    }
}