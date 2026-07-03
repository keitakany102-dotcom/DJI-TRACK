package com.Somagep.controller.api;

import com.Somagep.entity.Paiement;
import com.Somagep.entity.User;
import com.Somagep.service.PaiementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/paiements")
public class PaiementRestController {

    @Autowired
    private PaiementService paiementService;

    @PostMapping("/effectuer")
    @PreAuthorize("hasAnyRole('ADMIN','AGENT','CLIENT')")
    public ResponseEntity<?> effectuerPaiement(@RequestParam Long factureId,
                                               @RequestParam BigDecimal montant,
                                               @RequestParam String mode,
                                               Authentication auth) {
        User agent = (User) auth.getPrincipal();
        Paiement p = paiementService.effectuerPaiement(factureId, montant, mode, agent);
        return ResponseEntity.ok(p);
    }
}
