package com.Somagep.entity;


import jakarta.persistence.*;
import java.util.Date;

@Entity
public class ReleveIndex {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double indexReleve;
    private Date dateReleve;
    private String photoUrl;
    private String geolocalisation;
    private String signatureAgent;
    private boolean valide = false;
    private Date dateValidation;

    @ManyToOne
    @JoinColumn(name = "compteur_id")
    private Compteur compteur;

    @ManyToOne
    @JoinColumn(name = "agent_id")
    private User agent;

    // Getters, setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getIndexReleve() {
        return indexReleve;
    }

    public void setIndexReleve(double indexReleve) {
        this.indexReleve = indexReleve;
    }

    public Date getDateReleve() {
        return dateReleve;
    }

    public void setDateReleve(Date dateReleve) {
        this.dateReleve = dateReleve;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getGeolocalisation() {
        return geolocalisation;
    }

    public void setGeolocalisation(String geolocalisation) {
        this.geolocalisation = geolocalisation;
    }

    public String getSignatureAgent() {
        return signatureAgent;
    }

    public void setSignatureAgent(String signatureAgent) {
        this.signatureAgent = signatureAgent;
    }

    public boolean isValide() {
        return valide;
    }

    public void setValide(boolean valide) {
        this.valide = valide;
    }

    public Date getDateValidation() {
        return dateValidation;
    }

    public void setDateValidation(Date dateValidation) {
        this.dateValidation = dateValidation;
    }

    public Compteur getCompteur() {
        return compteur;
    }

    public void setCompteur(Compteur compteur) {
        this.compteur = compteur;
    }

    public User getAgent() {
        return agent;
    }

    public void setAgent(User agent) {
        this.agent = agent;
    }
}