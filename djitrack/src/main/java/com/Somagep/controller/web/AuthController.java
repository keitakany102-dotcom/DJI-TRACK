package com.Somagep.controller.web;

import com.Somagep.entity.Role;
import com.Somagep.entity.User;
import com.Somagep.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", new Role[]{Role.CLIENT});
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String email,
                           @RequestParam String nom,
                           @RequestParam String prenom,
                           @RequestParam String role,
                           Model model) {
        try {
            Role userRole = Role.valueOf(role);
            userService.registerUser(username, password, email, nom, prenom, userRole);
            return "redirect:/login?success";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("roles", new Role[]{Role.CLIENT});
            User u = new User();
            u.setUsername(username);
            u.setEmail(email);
            u.setNom(nom);
            u.setPrenom(prenom);
            model.addAttribute("user", u);
            return "register";
        }
    }
}
