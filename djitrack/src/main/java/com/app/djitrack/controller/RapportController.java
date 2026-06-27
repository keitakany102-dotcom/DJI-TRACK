package com.app.djitrack.controller;

import com.app.djitrack.entity.Rapport;
import com.app.djitrack.service.RapportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rapports")
@RequiredArgsConstructor
public class RapportController {

    private final RapportService service;

    @GetMapping
    public List<Rapport> getAll(){
        return service.getAll();
    }

    @PostMapping("/generer")
    public ResponseEntity<?> generer(@RequestBody(required = false) Map<String, String> body){
        try {
            String type = (body != null) ? body.get("type") : null;
            Rapport rapport = service.genererRapport(type);
            return ResponseEntity.ok(rapport);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la génération: " + e.getMessage());
        }
    }
}
