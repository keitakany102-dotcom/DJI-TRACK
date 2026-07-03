package com.Somagep.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Tarif {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String typeClient;
    private double borneInferieure;
    private double borneSuperieure;
    private BigDecimal prixParM3;

    // Getters, setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeClient() {
        return typeClient;
    }

    public void setTypeClient(String typeClient) {
        this.typeClient = typeClient;
    }

    public double getBorneInferieure() {
        return borneInferieure;
    }

    public void setBorneInferieure(double borneInferieure) {
        this.borneInferieure = borneInferieure;
    }

    public double getBorneSuperieure() {
        return borneSuperieure;
    }

    public void setBorneSuperieure(double borneSuperieure) {
        this.borneSuperieure = borneSuperieure;
    }

    public BigDecimal getPrixParM3() {
        return prixParM3;
    }

    public void setPrixParM3(BigDecimal prixParM3) {
        this.prixParM3 = prixParM3;
    }
}
