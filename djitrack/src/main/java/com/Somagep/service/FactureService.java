package com.Somagep.service;


import com.Somagep.entity.*;
import com.Somagep.repository.*;
import com.Somagep.utils.PdfGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class FactureService {

    @Autowired
    private FactureRepository factureRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ReleveIndexRepository releveIndexRepository;

    @Autowired
    private TarifRepository tarifRepository;

    @Autowired
    private PdfGenerator pdfGenerator;

    @Autowired
    private NotificationService notificationService;

    @Transactional
    public Facture genererFacture(Long clientId, int mois, int annee) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));

        List<Compteur> compteurs = client.getCompteurs();
        if (compteurs.isEmpty()) {
            throw new RuntimeException("Aucun compteur pour ce client");
        }

        double consoTotale = 0;
        for (Compteur c : compteurs) {
            List<ReleveIndex> releves = releveIndexRepository.findByCompteurIdOrderByDateReleveDesc(c.getId());
            if (releves.size() >= 2) {
                double dernier = releves.get(0).getIndexReleve();
                double precedent = releves.get(1).getIndexReleve();
                consoTotale += (dernier - precedent);
            }
        }

        BigDecimal conso = BigDecimal.valueOf(consoTotale);
        BigDecimal montant = calculerMontant(client.getTypeClient(), conso);

        BigDecimal taxes = montant.multiply(BigDecimal.valueOf(0.18));
        BigDecimal montantTTC = montant.add(taxes);

        Facture facture = new Facture();
        facture.setNumeroFacture("FAC-" + System.currentTimeMillis());
        facture.setClient(client);
        facture.setDateEmission(new Date());
        facture.setDateEcheance(Date.from(LocalDate.now().plusDays(30).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        facture.setConsommation(conso);
        facture.setMontantTotal(montant);
        facture.setPenalites(BigDecimal.ZERO);
        facture.setMontantTTC(montantTTC);
        facture.setStatut("impayée");

        Facture saved = factureRepository.save(facture);

        byte[] pdfBytes = pdfGenerator.genererFacturePDF(saved);
        String pdfPath = savePdf(pdfBytes, saved.getId());
        saved.setPdfPath(pdfPath);
        factureRepository.save(saved);

        notificationService.envoyerNotification(client, "Nouvelle facture disponible", "Votre facture " + saved.getNumeroFacture() + " est disponible.");
        return saved;
    }

    private BigDecimal calculerMontant(TypeClient type, BigDecimal conso) {
        List<Tarif> tarifs = tarifRepository.findByTypeClient(type.name());
        BigDecimal total = BigDecimal.ZERO;
        double consoVal = conso.doubleValue();
        for (Tarif t : tarifs) {
            if (consoVal > t.getBorneInferieure()) {
                double quantite = Math.min(consoVal, t.getBorneSuperieure()) - t.getBorneInferieure();
                if (quantite > 0) {
                    total = total.add(t.getPrixParM3().multiply(BigDecimal.valueOf(quantite)));
                }
            }
        }
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    private String savePdf(byte[] pdfBytes, Long factureId) {
        // Simuler sauvegarde
        return "/pdf/facture_" + factureId + ".pdf";
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    public void genererFacturesMensuelles() {
        List<Client> clients = clientRepository.findAllActifs();
        LocalDate now = LocalDate.now();
        int mois = now.getMonthValue() - 1;
        int annee = now.getYear();
        if (mois == 0) { mois = 12; annee--; }
        for (Client client : clients) {
            try {
                genererFacture(client.getId(), mois, annee);
            } catch (Exception e) {
                // log
            }
        }
    }

    public Facture getFactureById(Long id) {
        return factureRepository.findById(id).orElseThrow(() -> new RuntimeException("Facture non trouvée"));
    }

    public List<Facture> getFacturesByClient(Long clientId) {
        return factureRepository.findByClientId(clientId);
    }

    public List<Facture> getFacturesByAllClients() {
        return factureRepository.findAll();
    }

    public long countImpayees() {
        return factureRepository.findAll().stream()
                .filter(f -> "impayée".equals(f.getStatut()))
                .count();
    }
}
