package com.Somagep.service;


import com.Somagep.entity.Facture;
import com.Somagep.entity.Paiement;
import com.Somagep.entity.User;
import com.Somagep.repository.PaiementRepository;
import com.Somagep.repository.FactureRepository;
import com.Somagep.utils.QrCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Service
public class PaiementService {

    @Autowired
    private PaiementRepository paiementRepository;

    @Autowired
    private FactureRepository factureRepository;

    @Autowired
    private QrCodeGenerator qrCodeGenerator;

    @Autowired
    private NotificationService notificationService;

    @Transactional
    public Paiement effectuerPaiement(Long factureId, BigDecimal montantPaye, String mode, User agent) {
        Facture facture = factureRepository.findById(factureId)
                .orElseThrow(() -> new RuntimeException("Facture non trouvée"));

        if (montantPaye.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Montant invalide");
        }

        Paiement paiement = new Paiement();
        paiement.setFacture(facture);
        paiement.setMontant(montantPaye);
        paiement.setMode(mode);
        paiement.setDatePaiement(new Date());
        paiement.setReference("PAY-" + UUID.randomUUID().toString().substring(0, 8));
        paiement.setStatut("validé");
        paiement.setAgent(agent);
        paiement.setDateValidation(new Date());

        String qrData = "Paiement " + paiement.getReference() + " - Facture " + facture.getNumeroFacture();
        String qrCodePath = qrCodeGenerator.generateQrCode(qrData, paiement.getReference());
        paiement.setQrCode(qrCodePath);

        Paiement saved = paiementRepository.save(paiement);

        BigDecimal totalPaye = facture.getMontantPaye().add(montantPaye);
        facture.setMontantPaye(totalPaye);
        if (totalPaye.compareTo(facture.getMontantTTC()) >= 0) {
            facture.setStatut("payée");
        } else {
            facture.setStatut("partielle");
        }
        factureRepository.save(facture);

        notificationService.envoyerNotification(facture.getClient(),
                "Paiement reçu",
                "Votre paiement de " + montantPaye + " pour la facture " + facture.getNumeroFacture() + " a été validé.");
        return saved;
    }
}