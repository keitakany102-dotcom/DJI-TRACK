package com.Somagep.entity;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Branchement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dateDemande;
    private String statut;
    private String etudeTechnique;
    private BigDecimal montantDevis;
    private Date dateAcceptationDevis;
    private Date dateMiseEnService;
    private Date dateDebutTravaux;
    private Date dateFinTravaux;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

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

    public Date getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(Date dateDemande) {
        this.dateDemande = dateDemande;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getEtudeTechnique() {
        return etudeTechnique;
    }

    public void setEtudeTechnique(String etudeTechnique) {
        this.etudeTechnique = etudeTechnique;
    }

    public BigDecimal getMontantDevis() {
        return montantDevis;
    }

    public void setMontantDevis(BigDecimal montantDevis) {
        this.montantDevis = montantDevis;
    }

    public Date getDateAcceptationDevis() {
        return dateAcceptationDevis;
    }

    public void setDateAcceptationDevis(Date dateAcceptationDevis) {
        this.dateAcceptationDevis = dateAcceptationDevis;
    }

    public Date getDateMiseEnService() {
        return dateMiseEnService;
    }

    public void setDateMiseEnService(Date dateMiseEnService) {
        this.dateMiseEnService = dateMiseEnService;
    }

    public Date getDateDebutTravaux() {
        return dateDebutTravaux;
    }

    public void setDateDebutTravaux(Date dateDebutTravaux) {
        this.dateDebutTravaux = dateDebutTravaux;
    }

    public Date getDateFinTravaux() {
        return dateFinTravaux;
    }

    public void setDateFinTravaux(Date dateFinTravaux) {
        this.dateFinTravaux = dateFinTravaux;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }
}
