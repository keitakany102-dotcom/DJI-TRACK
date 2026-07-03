package com.Somagep.controller.web;

import com.Somagep.config.UserPrincipal;
import com.Somagep.entity.User;
import com.Somagep.repository.UserRepository;
import com.Somagep.service.CompteurService;
import com.Somagep.service.ReleveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/agent")
public class AgentController {

    @Autowired private ReleveService releveService;
    @Autowired private CompteurService compteurService;
    @Autowired private UserRepository userRepository;

    private User getUser(Authentication auth) {
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
        return userRepository.findById(principal.getId()).orElseThrow();
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication auth, Model model) {
        User agent = getUser(auth);
        model.addAttribute("releves", releveService.findByAgent(agent.getId()));
        model.addAttribute("relevesCount", releveService.findByAgent(agent.getId()).size());
        model.addAttribute("interventionsCount", 0);
        model.addAttribute("paiementsCount", 0);
        return "agent/dashboard";
    }

    @GetMapping("/releves")
    public String listReleves(Authentication auth, Model model) {
        User agent = getUser(auth);
        model.addAttribute("releves", releveService.findByAgent(agent.getId()));
        return "agent/releves/list";
    }

    @GetMapping("/releves/new/{compteurId}")
    public String showReleveForm(@PathVariable Long compteurId, Model model) {
        model.addAttribute("compteurId", compteurId);
        return "agent/releves/form";
    }

    @PostMapping("/releves/save")
    public String saveReleve(@RequestParam Long compteurId,
                             @RequestParam double index,
                             @RequestParam MultipartFile photo,
                             @RequestParam String geolocalisation,
                             Authentication auth) {
        User agent = getUser(auth);
        releveService.saveReleve(compteurId, index, photo, geolocalisation, agent);
        return "redirect:/agent/releves";
    }

    @GetMapping("/interventions")
    public String listInterventions(Model model) {
        model.addAttribute("interventions", java.util.Collections.emptyList());
        return "agent/interventions/list";
    }

    @GetMapping("/paiements")
    public String listPaiements(Model model) {
        model.addAttribute("paiements", java.util.Collections.emptyList());
        return "agent/paiements/list";
    }
}
