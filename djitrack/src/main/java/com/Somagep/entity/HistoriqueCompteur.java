package com.Somagep.entity;


import jakarta.persistence.*;
import java.util.Date;

@Entity
public class HistoriqueCompteur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String action;
    private Date dateAction;
    private String description;
    private String ancienEtat;
    private String nouvelEtat;

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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Date getDateAction() {
        return dateAction;
    }

    public void setDateAction(Date dateAction) {
        this.dateAction = dateAction;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAncienEtat() {
        return ancienEtat;
    }

    public void setAncienEtat(String ancienEtat) {
        this.ancienEtat = ancienEtat;
    }

    public String getNouvelEtat() {
        return nouvelEtat;
    }

    public void setNouvelEtat(String nouvelEtat) {
        this.nouvelEtat = nouvelEtat;
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