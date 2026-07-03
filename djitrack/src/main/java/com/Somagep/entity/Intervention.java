package com.Somagep.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Intervention {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date datePlanifiee;
    private Date dateRealisation;
    private String description;
    private String statut;
    private String localisationGPS;
    private String photoAvant;
    private String photoApres;
    private String rapport;
    private String signatureAgent;

    @ManyToOne
    @JoinColumn(name = "equipe_id")
    private Equipe equipe;

    // Getters, setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDatePlanifiee() {
        return datePlanifiee;
    }

    public void setDatePlanifiee(Date datePlanifiee) {
        this.datePlanifiee = datePlanifiee;
    }

    public Date getDateRealisation() {
        return dateRealisation;
    }

    public void setDateRealisation(Date dateRealisation) {
        this.dateRealisation = dateRealisation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getLocalisationGPS() {
        return localisationGPS;
    }

    public void setLocalisationGPS(String localisationGPS) {
        this.localisationGPS = localisationGPS;
    }

    public String getPhotoAvant() {
        return photoAvant;
    }

    public void setPhotoAvant(String photoAvant) {
        this.photoAvant = photoAvant;
    }

    public String getPhotoApres() {
        return photoApres;
    }

    public void setPhotoApres(String photoApres) {
        this.photoApres = photoApres;
    }

    public String getRapport() {
        return rapport;
    }

    public void setRapport(String rapport) {
        this.rapport = rapport;
    }

    public String getSignatureAgent() {
        return signatureAgent;
    }

    public void setSignatureAgent(String signatureAgent) {
        this.signatureAgent = signatureAgent;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }
}