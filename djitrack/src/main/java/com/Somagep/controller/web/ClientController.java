package com.Somagep.controller.web;

import com.Somagep.config.UserPrincipal;
import com.Somagep.entity.Client;
import com.Somagep.entity.User;
import com.Somagep.repository.UserRepository;
import com.Somagep.service.ClientService;
import com.Somagep.service.FactureService;
import com.Somagep.service.ReclamationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private FactureService factureService;

    @Autowired
    private ReclamationService reclamationService;

    @Autowired
    private UserRepository userRepository;

    private User getUser(Authentication auth) {

        if (auth == null || !(auth.getPrincipal() instanceof UserPrincipal)) {
            return null;
        }

        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();

        return userRepository.findById(principal.getId()).orElse(null);
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication auth, Model model) {

        User user = getUser(auth);

        if (user == null) {
            return "redirect:/login";
        }

        Optional<Client> optionalClient = clientService.findByUser(user);

        if (optionalClient.isEmpty()) {
            model.addAttribute("error", "Aucun client associé à cet utilisateur.");
            return "error";
        }

        Client client = optionalClient.get();

        model.addAttribute("client", client);
        model.addAttribute("factures", factureService.getFacturesByClient(client.getId()));
        model.addAttribute("reclamations", reclamationService.getByClient(client.getId()));

        return "client/dashboard";
    }

    @GetMapping("/factures")
    public String factures(Authentication auth, Model model) {

        User user = getUser(auth);

        if (user == null) {
            return "redirect:/login";
        }

        Optional<Client> optionalClient = clientService.findByUser(user);

        if (optionalClient.isEmpty()) {
            model.addAttribute("error", "Aucun client trouvé.");
            return "error";
        }

        Client client = optionalClient.get();

        model.addAttribute("factures", factureService.getFacturesByClient(client.getId()));

        return "client/factures";
    }

    @GetMapping("/reclamations/new")
    public String newReclamation() {
        return "client/reclamation_form";
    }

    @PostMapping("/reclamations/save")
    public String saveReclamation(Authentication auth,
                                  @RequestParam String type,
                                  @RequestParam String description,
                                  @RequestParam String priorite) {

        User user = getUser(auth);

        if (user == null) {
            return "redirect:/login";
        }

        Optional<Client> optionalClient = clientService.findByUser(user);

        if (optionalClient.isEmpty()) {
            return "redirect:/client/dashboard";
        }

        reclamationService.creerReclamation(
                optionalClient.get(),
                type,
                description,
                priorite
        );

        return "redirect:/client/dashboard";
    }

    @GetMapping("/branchement/new")
    public String newBranchement() {
        return "client/branchement_form";
    }

    @GetMapping("/profil")
    public String profil(Authentication auth, Model model) {

        User user = getUser(auth);

        if (user == null) {
            return "redirect:/login";
        }

        Optional<Client> optionalClient = clientService.findByUser(user);

        if (optionalClient.isEmpty()) {
            model.addAttribute("error", "Client introuvable.");
            return "error";
        }

        model.addAttribute("client", optionalClient.get());

        return "client/profil";
    }
}