package com.Somagep.controller.api;


import com.Somagep.entity.Client;
import com.Somagep.entity.Reclamation;
import com.Somagep.entity.User;
import com.Somagep.service.ClientService;
import com.Somagep.service.ReclamationService;
import com.Somagep.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reclamations")
public class ReclamationRestController {

    @Autowired
    private ReclamationService reclamationService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private UserService userService;

    @PostMapping("/client")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<?> creerReclamation(@RequestParam Long clientId,
                                              @RequestParam String type,
                                              @RequestParam String description,
                                              @RequestParam String priorite) {
        Client client = clientService.findById(clientId).orElseThrow();
        Reclamation r = reclamationService.creerReclamation(client, type, description, priorite);
        return ResponseEntity.ok(r);
    }

    @PutMapping("/assigner/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> assignerAgent(@PathVariable Long id, @RequestParam Long agentId) {
        User agent = userService.findById(agentId).orElseThrow();
        return ResponseEntity.ok(reclamationService.assignerAgent(id, agent));
    }

    @PutMapping("/traiter/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','AGENT')")
    public ResponseEntity<?> traiterReclamation(@PathVariable Long id,
                                                @RequestParam String commentaire,
                                                @RequestParam(required = false) String photos) {
        return ResponseEntity.ok(reclamationService.traiterReclamation(id, commentaire, photos));
    }

    @GetMapping("/client/{clientId}")
    @PreAuthorize("hasAnyRole('ADMIN','AGENT','CLIENT')")
    public ResponseEntity<?> getByClient(@PathVariable Long clientId) {
        return ResponseEntity.ok(reclamationService.getByClient(clientId));
    }

    @GetMapping("/agent/{agentId}")
    @PreAuthorize("hasAnyRole('ADMIN','AGENT')")
    public ResponseEntity<?> getByAgent(@PathVariable Long agentId) {
        return ResponseEntity.ok(reclamationService.getByAgent(agentId));
    }
}