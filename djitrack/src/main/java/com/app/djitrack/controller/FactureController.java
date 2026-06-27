package com.app.djitrack.controller;

import com.app.djitrack.entity.Facture;
import com.app.djitrack.service.FactureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/factures")
@RequiredArgsConstructor
public class FactureController {

    private final FactureService factureService;

    // ==================== GET ALL (tous les rôles authentifiés) ====================
    @GetMapping
    public ResponseEntity<List<Facture>> findAll() {
        return ResponseEntity.ok(factureService.findAll());
    }

    // ==================== GET BY ID (tous les rôles authentifiés) ====================
    @GetMapping("/{id}")
    public ResponseEntity<Facture> getOne(@PathVariable Long id) {
        return factureService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ==================== GET BY ABONNE (abonné voit ses propres factures) ====================
    @GetMapping("/abonne/{abonneId}")
    public ResponseEntity<List<Facture>> getByAbonne(@PathVariable Long abonneId) {
        return ResponseEntity.ok(factureService.findByAbonneId(abonneId));
    }

    // ==================== CREATE (Admin et Agent seulement) ====================
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    public ResponseEntity<?> create(@RequestBody Facture facture) {
        try {
            if (facture.getNumero() == null || facture.getNumero().isEmpty()) {
                return ResponseEntity.badRequest().body("Le numéro de facture est obligatoire");
            }
            if (facture.getMontantTotal() == null || facture.getMontantTotal() <= 0) {
                return ResponseEntity.badRequest().body("Le montant total doit être supérieur à 0");
            }
            if (facture.getAbonne() == null || facture.getAbonne().getId() == null) {
                return ResponseEntity.badRequest().body("L'ID de l'abonné est obligatoire");
            }

            Facture saved = factureService.save(facture);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la création: " + e.getMessage());
        }
    }

    // ==================== UPDATE (Admin et Agent seulement) ====================
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Facture facture) {
        try {
            if (factureService.getById(id).isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            facture.setId(id);

            if (facture.getNumero() == null || facture.getNumero().isEmpty()) {
                return ResponseEntity.badRequest().body("Le numéro de facture est obligatoire");
            }
            if (facture.getMontantTotal() == null || facture.getMontantTotal() <= 0) {
                return ResponseEntity.badRequest().body("Le montant total doit être supérieur à 0");
            }
            if (facture.getAbonne() == null || facture.getAbonne().getId() == null) {
                return ResponseEntity.badRequest().body("L'ID de l'abonné est obligatoire");
            }

            Facture updated = factureService.save(facture);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la mise à jour: " + e.getMessage());
        }
    }

    // ==================== DELETE (Admin seulement) ====================
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            if (factureService.getById(id).isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            factureService.deleteById(id);
            return ResponseEntity.ok("Facture supprimée avec succès");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la suppression: " + e.getMessage());
        }
    }

    // ==================== GET BY STATUT ====================
    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<Facture>> getByStatut(@PathVariable String statut) {
        return ResponseEntity.ok(factureService.findByStatut(statut));
    }

    // ==================== GET BY DATE ====================
    @GetMapping("/date")
    public ResponseEntity<List<Facture>> getByDate(@RequestParam String date) {
        return ResponseEntity.ok(factureService.findByDateEmission(date));
    }

    // ==================== STATISTIQUES ====================
    @GetMapping("/stats")
    public ResponseEntity<?> getStats() {
        try {
            long total = factureService.count();
            long payees = factureService.countByStatut("PAYEE");
            long enAttente = factureService.countByStatut("EN ATTENTE");
            long impayees = factureService.countByStatut("IMPAYEE");

            return ResponseEntity.ok(new StatsResponse(total, payees, enAttente, impayees));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors du calcul des statistiques");
        }
    }

    private static class StatsResponse {
        public long total;
        public long payees;
        public long enAttente;
        public long impayees;

        public StatsResponse(long total, long payees, long enAttente, long impayees) {
            this.total = total;
            this.payees = payees;
            this.enAttente = enAttente;
            this.impayees = impayees;
        }
    }
}
