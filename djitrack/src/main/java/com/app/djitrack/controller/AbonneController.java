package com.app.djitrack.controller;

import com.app.djitrack.entity.Abonne;
import com.app.djitrack.service.AbonneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/abonnes")
@RequiredArgsConstructor
public class AbonneController {

    private final AbonneService service;

    @GetMapping
    public ResponseEntity<List<Abonne>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Abonne abonne) {
        try {
            Abonne saved = service.save(abonne);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Erreur lors de la création: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Abonné supprimé avec succès"));
    }
}
