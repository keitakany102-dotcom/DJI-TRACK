package com.Somagep.controller.api;

import com.Somagep.entity.Facture;
import com.Somagep.service.FactureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;

@RestController
@RequestMapping("/api/factures")
public class FactureRestController {

    @Autowired
    private FactureService factureService;

    @PostMapping("/generer")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> genererFacture(@RequestParam Long clientId,
                                            @RequestParam int mois,
                                            @RequestParam int annee) {
        Facture f = factureService.genererFacture(clientId, mois, annee);
        return ResponseEntity.ok(f);
    }

    @GetMapping("/pdf/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','AGENT','CLIENT')")
    public ResponseEntity<?> telechargerPDF(@PathVariable Long id) {
        Facture f = factureService.getFactureById(id);
        File file = new File(f.getPdfPath());
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }
        try {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + f.getNumeroFacture() + ".pdf\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}