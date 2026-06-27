package com.app.djitrack.controller;

import com.app.djitrack.entity.Role;
import com.app.djitrack.entity.Utilisateur;
import com.app.djitrack.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    // ==================== PAGES HTML ====================

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalUsers", utilisateurRepository.count());
        model.addAttribute("admins", utilisateurRepository.countByRole(Role.ROLE_ADMIN));
        model.addAttribute("agents", utilisateurRepository.countByRole(Role.ROLE_AGENT));
        model.addAttribute("abonnes", utilisateurRepository.countByRole(Role.ROLE_ABONNE));
        return "admin/dashboard";
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", utilisateurRepository.findAll());
        model.addAttribute("roles", Role.values());
        return "admin/users";
    }

    @GetMapping("/users/create")
    public String createUserForm(Model model) {
        model.addAttribute("user", new Utilisateur());
        model.addAttribute("roles", Role.values());
        return "admin/user-form";
    }

    @GetMapping("/users/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        Utilisateur user = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "admin/user-form";
    }

    // ==================== API REST ====================

    @GetMapping("/api/dashboard")
    @ResponseBody
    public ResponseEntity<?> dashboardApi() {
        Map<String, Object> response = new HashMap<>();
        response.put("totalUsers", utilisateurRepository.count());
        response.put("admins", utilisateurRepository.countByRole(Role.ROLE_ADMIN));
        response.put("agents", utilisateurRepository.countByRole(Role.ROLE_AGENT));
        response.put("abonnes", utilisateurRepository.countByRole(Role.ROLE_ABONNE));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/users")
    @ResponseBody
    public ResponseEntity<List<Utilisateur>> getAllUsers() {
        return ResponseEntity.ok(utilisateurRepository.findAll());
    }

    @GetMapping("/api/users/{id}")
    @ResponseBody
    public ResponseEntity<Utilisateur> getUserById(@PathVariable Long id) {
        return utilisateurRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/api/users")
    @ResponseBody
    public ResponseEntity<?> createUser(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");
        String nom = request.get("nom");
        String telephone = request.get("telephone");
        String roleStr = request.get("role");

        if (email == null || password == null || nom == null || roleStr == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Tous les champs sont obligatoires"));
        }

        if (utilisateurRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Cet email existe déjà"));
        }

        Role role;
        try {
            String roleName = roleStr.toUpperCase();
            if (!roleName.startsWith("ROLE_")) {
                roleName = "ROLE_" + roleName;
            }
            role = Role.valueOf(roleName);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Rôle invalide"));
        }

        Utilisateur user = Utilisateur.builder()
                .nom(nom)
                .email(email)
                .telephone(telephone != null ? telephone : "")
                .motDePasse(passwordEncoder.encode(password))
                .role(role)
                .build();

        Utilisateur saved = utilisateurRepository.save(user);

        return ResponseEntity.ok(Map.of(
                "message", "Utilisateur créé avec succès",
                "user", saved
        ));
    }

    @PutMapping("/api/users/{id}")
    @ResponseBody
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody Map<String, String> request) {
        Utilisateur user = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (request.containsKey("nom")) {
            user.setNom(request.get("nom"));
        }
        if (request.containsKey("telephone")) {
            user.setTelephone(request.get("telephone"));
        }
        if (request.containsKey("password") && !request.get("password").isEmpty()) {
            user.setMotDePasse(passwordEncoder.encode(request.get("password")));
        }
        if (request.containsKey("role")) {
            try {
                String roleName = request.get("role").toUpperCase();
                if (!roleName.startsWith("ROLE_")) {
                    roleName = "ROLE_" + roleName;
                }
                user.setRole(Role.valueOf(roleName));
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(Map.of("error", "Rôle invalide"));
            }
        }

        Utilisateur updated = utilisateurRepository.save(user);
        return ResponseEntity.ok(Map.of(
                "message", "Utilisateur mis à jour avec succès",
                "user", updated
        ));
    }

    @DeleteMapping("/api/users/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (!utilisateurRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        utilisateurRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Utilisateur supprimé avec succès"));
    }

    @GetMapping("/api/users/search")
    @ResponseBody
    public ResponseEntity<List<Utilisateur>> searchUsers(@RequestParam String keyword) {
        return ResponseEntity.ok(utilisateurRepository.findByNomContainingOrEmailContaining(keyword, keyword));
    }
}