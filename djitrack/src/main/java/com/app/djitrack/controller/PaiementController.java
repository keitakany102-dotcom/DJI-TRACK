package com.app.djitrack.controller;

import com.app.djitrack.dto.PaiementRequest;
import com.app.djitrack.service.PaiementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paiements")
@RequiredArgsConstructor
public class PaiementController {

    private final PaiementService service;

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