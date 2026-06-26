package com.app.djitrack.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectionController {

    @GetMapping("/dashboard")
    public String redirectBasedOnRole() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated()) {
            for (GrantedAuthority authority : auth.getAuthorities()) {
                String role = authority.getAuthority();

                if (role.equals("ROLE_ADMIN")) {
                    return "redirect:/dashboard/admin";
                } else if (role.equals("ROLE_AGENT")) {
                    return "redirect:/dashboard/agent";
                } else if (role.equals("ROLE_ABONNE")) {
                    return "redirect:/dashboard/abonne";
                }
            }
        }

        // Par défaut, rediriger vers la page de login
        return "redirect:/login";
    }
}