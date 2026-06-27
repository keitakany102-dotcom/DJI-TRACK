package com.app.djitrack.controller;

import com.app.djitrack.entity.ReleveCompteur;
import com.app.djitrack.service.ReleveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/releves")
@RequiredArgsConstructor
public class ReleveController {

    private final ReleveService service;

    @GetMapping
    public ResponseEntity<List<ReleveCompteur>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ReleveCompteur releve) {
        try {
            if (releve.getAbonne() == null || releve.getAbonne().getId() == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "L'ID de l'abonné est obligatoire"));
            }
            if (releve.getAncienneValeur() == null || releve.getNouvelleValeur() == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Les valeurs du compteur sont obligatoires"));
            }
            if (releve.getNouvelleValeur() < releve.getAncienneValeur()) {
                return ResponseEntity.badRequest().body(Map.of("error", "La nouvelle valeur doit être supérieure à l'ancienne valeur"));
            }
            ReleveCompteur saved = service.save(releve);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Erreur lors de la création: " + e.getMessage()));
        }
    }
}
