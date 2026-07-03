package com.Somagep.entity;


import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Compteur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String numeroSerie;

    private String type;
    private String etat;
    private String localisationGPS;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    private Date dateInstallation;
    private Date dateDernierReleve;

    @OneToMany(mappedBy = "compteur")
    private List<ReleveIndex> releves;

    @OneToMany(mappedBy = "compteur")
    private List<HistoriqueCompteur> historiques;

    // Getters, setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getLocalisationGPS() {
        return localisationGPS;
    }

    public void setLocalisationGPS(String localisationGPS) {
        this.localisationGPS = localisationGPS;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Date getDateInstallation() {
        return dateInstallation;
    }

    public void setDateInstallation(Date dateInstallation) {
        this.dateInstallation = dateInstallation;
    }

    public Date getDateDernierReleve() {
        return dateDernierReleve;
    }

    public void setDateDernierReleve(Date dateDernierReleve) {
        this.dateDernierReleve = dateDernierReleve;
    }

    public List<ReleveIndex> getReleves() {
        return releves;
    }

    public void setReleves(List<ReleveIndex> releves) {
        this.releves = releves;
    }

    public List<HistoriqueCompteur> getHistoriques() {
        return historiques;
    }

    public void setHistoriques(List<HistoriqueCompteur> historiques) {
        this.historiques = historiques;
    }
}