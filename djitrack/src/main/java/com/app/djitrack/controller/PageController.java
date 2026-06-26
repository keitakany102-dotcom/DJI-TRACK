package com.app.djitrack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    @GetMapping("/dashboard/admin")
    public String dashboardAdmin() {
        return "dashboard/dashboard-admin";
    }

    @GetMapping("/dashboard/agent")
    public String dashboardAgent() {
        return "dashboard/dashboard-agent";
    }

    @GetMapping("/dashboard/abonne")
    public String dashboardAbonne() {
        return "dashboard/dashboard-abonne";
    }

    @GetMapping("/abonnes")
    public String abonnes() {
        return "abonnes/abonnes";
    }

    @GetMapping("/releves")
    public String releves() {
        return "releves/releves";
    }

    @GetMapping("/factures")
    public String factures() {
        return "factures/factures";
    }

    @GetMapping("/paiements")
    public String paiements() {
        return "paiements/paiements";
    }

    @GetMapping("/reclamations")
    public String reclamations() {
        return "reclamations/reclamations";
    }

    @GetMapping("/rapports")
    public String rapports() {
        return "rapports/rapports";
    }

    @GetMapping("/admin")
    public String admin() {
        return "dashboard/dashboard-admin";
    }
}