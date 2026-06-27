package com.app.djitrack.controller;

import com.app.djitrack.dto.PaiementRequest;
import com.app.djitrack.entity.Paiement;
import com.app.djitrack.repository.PaiementRepository;
import com.app.djitrack.service.PaiementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paiements")
@RequiredArgsConstructor
public class PaiementController {

    private final PaiementService service;
    private final PaiementRepository paiementRepository;

    // GET ALL - Historique de tous les paiements
    @GetMapping
    public ResponseEntity<List<Paiement>> getAll() {
        return ResponseEntity.ok(paiementRepository.findAll());
    }

    @PostMapping("/orange-money")
    public ResponseEntity<?> payerOrange(
            @RequestBody PaiementRequest request) {

        return ResponseEntity.ok(
                service.payerOrange(request));
    }

    @PostMapping("/moov-money")
    public ResponseEntity<?> payerMoov(
            @RequestBody PaiementRequest request) {

        return ResponseEntity.ok(
                service.payerMoov(request));
    }
}
