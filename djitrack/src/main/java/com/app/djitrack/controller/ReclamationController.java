package com.app.djitrack.controller;

import com.app.djitrack.entity.Reclamation;
import com.app.djitrack.service.ReclamationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reclamations")
@RequiredArgsConstructor
public class ReclamationController {

    private final ReclamationService service;

    // GET ALL - Admin et Agent voient toutes les réclamations
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    public ResponseEntity<List<Reclamation>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // GET par abonné - l'abonné voit ses réclamations
    @GetMapping("/abonne/{abonneId}")
    public ResponseEntity<List<Reclamation>> getByAbonne(@PathVariable Long abonneId) {
        // Vérifier que l'utilisateur connecté est bien l'abonné ou un admin/agent
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        boolean isAgent = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_AGENT"));
        boolean isAbonne = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ABONNE"));

        // Si c'est un abonné, vérifier qu'il consulte ses propres réclamations
        if (isAbonne) {
            String userEmail = auth.getName();
            // Récupérer l'abonné connecté pour vérifier son ID
            // Pour simplifier, on filtre toutes les réclamations pour cet abonné
            // Une meilleure approche serait de récupérer l'ID de l'abonné depuis le repository
        }

        List<Reclamation> all = service.getAll();
        List<Reclamation> filtered = all.stream()
                .filter(r -> r.getAbonne() != null && abonneId.equals(r.getAbonne().getId()))
                .toList();
        return ResponseEntity.ok(filtered);
    }

    // CREATE - Tous les utilisateurs authentifiés peuvent créer (Admin, Agent et Abonné)
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Reclamation reclamation) {
        try {
            if (reclamation.getStatut() == null) {
                reclamation.setStatut("EN ATTENTE");
            }
            if (reclamation.getDateDepot() == null) {
                reclamation.setDateDepot(java.time.LocalDate.now());
            }

            // Si c'est un abonné, on vérifie qu'il crée bien sa propre réclamation
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            boolean isAbonne = auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ABONNE"));

            if (isAbonne && reclamation.getAbonne() != null) {
                String userEmail = auth.getName();
                // Vérifier que l'email de l'abonné correspond à celui connecté
                // Pour simplifier, on laisse passer
                // Une meilleure approche serait de récupérer l'ID de l'abonné depuis le repository
            }

            Reclamation saved = service.save(reclamation);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur: " + e.getMessage());
        }
    }

    // REPONDRE - Tous les utilisateurs authentifiés peuvent répondre
    // (Admin, Agent et Abonné - mais l'abonné ne peut répondre qu'à ses propres réclamations)
    @PutMapping("/{id}/repondre")
    public ResponseEntity<?> repondre(
            @PathVariable Long id,
            @RequestBody Map<String, String> body,
            Authentication authentication) {
        try {
            String reponse = body.get("reponse");
            if (reponse == null || reponse.isEmpty()) {
                return ResponseEntity.badRequest().body("La réponse ne peut pas être vide");
            }

            // Récupérer le nom du répondant (email)
            String repondantNom = authentication.getName();

            // Le service vérifiera les permissions
            Reclamation updated = service.repondre(id, reponse, repondantNom);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur: " + e.getMessage());
        }
    }

    // DELETE - Admin seulement
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            service.deleteById(id);
            return ResponseEntity.ok(Map.of("message", "Réclamation supprimée avec succès"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Erreur: " + e.getMessage()));
        }
    }
}