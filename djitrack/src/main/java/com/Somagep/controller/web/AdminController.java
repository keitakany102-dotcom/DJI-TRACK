package com.Somagep.controller.web;

import com.Somagep.entity.Agence;
import com.Somagep.entity.Client;
import com.Somagep.entity.Role;
import com.Somagep.entity.Tarif;
import com.Somagep.entity.User;
import com.Somagep.service.AgenceService;
import com.Somagep.service.ClientService;
import com.Somagep.service.FactureService;
import com.Somagep.service.ReclamationService;
import com.Somagep.service.TarifService;
import com.Somagep.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired private ClientService clientService;
    @Autowired private UserService userService;
    @Autowired private FactureService factureService;
    @Autowired private ReclamationService reclamationService;
    @Autowired private AgenceService agenceService;
    @Autowired private TarifService tarifService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("nbAbonnes", clientService.findAll().size());
        model.addAttribute("nbFactures", factureService.getFacturesByAllClients().size());
        model.addAttribute("nbImpayees", factureService.countImpayees());
        model.addAttribute("nbReclamations", reclamationService.countOuvertes());
        return "admin/dashboard";
    }

    // --- Clients ---
    @GetMapping("/clients")
    public String listClients(@RequestParam(required = false) String keyword, Model model) {
        if (keyword != null && !keyword.isEmpty()) {
            model.addAttribute("clients", clientService.searchClients(keyword));
        } else {
            model.addAttribute("clients", clientService.findAll());
        }
        return "admin/clients/list";
    }

    @GetMapping("/clients/create")
    public String showCreateForm(Model model) {
        model.addAttribute("client", new Client());
        return "admin/clients/form";
    }

    @PostMapping("/clients/save")
    public String saveClient(@ModelAttribute Client client,
                             @RequestParam String username,
                             @RequestParam String password,
                             Model model) {
        try {
            clientService.createClient(client, username, password);
            return "redirect:/admin/clients";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "admin/clients/form";
        }
    }

    @GetMapping("/clients/delete/{id}")
    public String deleteClient(@PathVariable Long id) {
        Client client = clientService.findById(id).orElseThrow();
        client.setActif(false);
        clientService.save(client);
        return "redirect:/admin/clients";
    }

    // --- Utilisateurs ---
    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/users/list";
    }

    @GetMapping("/users/create")
    public String showUserCreate(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", Role.values());
        return "admin/users/form";
    }

    @PostMapping("/users/save")
    public String saveUser(@ModelAttribute User user,
                           @RequestParam String password,
                           @RequestParam String role,
                           Model model) {
        try {
            Role userRole = Role.valueOf(role);
            userService.registerUser(user.getUsername(), password, user.getEmail(), user.getNom(), user.getPrenom(), userRole);
            return "redirect:/admin/users";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("roles", Role.values());
            return "admin/users/form";
        }
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        User user = userService.findById(id).orElseThrow();
        user.setEnabled(false);
        userService.save(user);
        return "redirect:/admin/users";
    }

    // --- Agences ---
    @GetMapping("/agences")
    public String listAgences(Model model) {
        model.addAttribute("agences", agenceService.findAll());
        return "admin/agences/list";
    }

    @GetMapping("/agences/create")
    public String showAgenceCreate(Model model) {
        model.addAttribute("agence", new Agence());
        return "admin/agences/form";
    }

    @PostMapping("/agences/save")
    public String saveAgence(@ModelAttribute Agence agence) {
        agenceService.save(agence);
        return "redirect:/admin/agences";
    }

    @GetMapping("/agences/delete/{id}")
    public String deleteAgence(@PathVariable Long id) {
        agenceService.delete(id);
        return "redirect:/admin/agences";
    }

    // --- Tarifs ---
    @GetMapping("/tarifs")
    public String listTarifs(Model model) {
        model.addAttribute("tarifs", tarifService.findAll());
        return "admin/tarifs/list";
    }

    @GetMapping("/tarifs/create")
    public String showTarifCreate(Model model) {
        model.addAttribute("tarif", new Tarif());
        return "admin/tarifs/form";
    }

    @PostMapping("/tarifs/save")
    public String saveTarif(@ModelAttribute Tarif tarif) {
        tarifService.save(tarif);
        return "redirect:/admin/tarifs";
    }

    @GetMapping("/tarifs/delete/{id}")
    public String deleteTarif(@PathVariable Long id) {
        tarifService.delete(id);
        return "redirect:/admin/tarifs";
    }

    // --- Rapports ---
    @GetMapping("/rapports")
    public String rapports(Model model) {
        return "admin/rapports/index";
    }
}
