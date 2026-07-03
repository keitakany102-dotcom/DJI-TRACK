package com.Somagep.controller.api;


import com.Somagep.entity.Branchement;
import com.Somagep.entity.Client;
import com.Somagep.entity.Equipe;
import com.Somagep.service.BranchementService;
import com.Somagep.service.ClientService;
import com.Somagep.service.EquipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/branchements")
public class BranchementRestController {

    @Autowired
    private BranchementService branchementService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private EquipeService equipeService;

    @PostMapping("/demande")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<?> demandeBranchement(@RequestParam Long clientId,
                                                @RequestParam String etudeTechnique) {
        Client client = clientService.findById(clientId).orElseThrow();
        Branchement b = branchementService.demandeBranchement(client, etudeTechnique);
        return ResponseEntity.ok(b);
    }

    @PutMapping("/valider-etude/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> validerEtude(@PathVariable Long id) {
        return ResponseEntity.ok(branchementService.validerEtude(id));
    }

    @PutMapping("/generer-devis/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> genererDevis(@PathVariable Long id, @RequestParam Double montant) {
        return ResponseEntity.ok(branchementService.genererDevis(id, montant));
    }

    @PutMapping("/affecter-equipe/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> affecterEquipe(@PathVariable Long id, @RequestParam Long equipeId) {
        Equipe equipe = equipeService.findById(equipeId).orElseThrow();
        return ResponseEntity.ok(branchementService.affecterEquipe(id, equipe));
    }

    @PutMapping("/mise-en-service/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','AGENT')")
    public ResponseEntity<?> miseEnService(@PathVariable Long id) {
        return ResponseEntity.ok(branchementService.miseEnService(id));
    }
}