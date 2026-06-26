package com.app.djitrack.controller;

import com.app.djitrack.entity.Role;
import com.app.djitrack.entity.Utilisateur;
import com.app.djitrack.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    // ==================== DASHBOARD ====================
    @GetMapping("/dashboard")
    public ResponseEntity<?> dashboard() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Dashboard Administrateur");
        response.put("totalUsers", utilisateurRepository.count());
        response.put("admins", utilisateurRepository.countByRole(Role.ROLE_ADMIN));
        response.put("agents", utilisateurRepository.countByRole(Role.ROLE_AGENT));
        response.put("abonnes", utilisateurRepository.countByRole(Role.ROLE_ABONNE));
        return ResponseEntity.ok(response);
    }

    // ==================== LISTE DES UTILISATEURS ====================
    @GetMapping("/users")
    public ResponseEntity<List<Utilisateur>> getAllUsers() {
        return ResponseEntity.ok(utilisateurRepository.findAll());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Utilisateur> getUserById(@PathVariable Long id) {
        return utilisateurRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/users/role/{role}")
    public ResponseEntity<List<Utilisateur>> getUsersByRole(@PathVariable String role) {
        try {
            Role roleEnum = Role.valueOf("ROLE_" + role.toUpperCase());
            return ResponseEntity.ok(utilisateurRepository.findByRole(roleEnum));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // ==================== CRÉATION D'UTILISATEURS ====================
    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");
        String nom = request.get("nom");
        String telephone = request.get("telephone");
        String roleStr = request.get("role");

        // Vérification des champs obligatoires
        if (email == null || password == null || nom == null || roleStr == null) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Tous les champs sont obligatoires: email, password, nom, role"
            ));
        }

        // Vérification si l'email existe déjà
        if (utilisateurRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Cet email existe déjà"
            ));
        }

        // Validation du rôle
        Role role;
        try {
            String roleName = roleStr.toUpperCase();
            if (!roleName.startsWith("ROLE_")) {
                roleName = "ROLE_" + roleName;
            }
            role = Role.valueOf(roleName);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Rôle invalide. Utilisez: ADMIN, AGENT ou ABONNE"
            ));
        }

        // Création de l'utilisateur
        Utilisateur user = Utilisateur.builder()
                .nom(nom)
                .email(email)
                .telephone(telephone != null ? telephone : "")
                .motDePasse(passwordEncoder.encode(password))
                .role(role)
                .build();

        Utilisateur saved = utilisateurRepository.save(user);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Utilisateur créé avec succès");
        response.put("user", Map.of(
                "id", saved.getId(),
                "nom", saved.getNom(),
                "email", saved.getEmail(),
                "telephone", saved.getTelephone(),
                "role", saved.getRole().name()
        ));

        return ResponseEntity.ok(response);
    }

    // ==================== CRÉATION D'UN ADMIN ====================
    @PostMapping("/create-admin")
    public ResponseEntity<?> createAdmin(@RequestBody Map<String, String> request) {
        request.put("role", "ADMIN");
        return createUser(request);
    }

    // ==================== CRÉATION D'UN AGENT ====================
    @PostMapping("/create-agent")
    public ResponseEntity<?> createAgent(@RequestBody Map<String, String> request) {
        request.put("role", "AGENT");
        return createUser(request);
    }

    // ==================== CRÉATION D'UN ABONNÉ ====================
    @PostMapping("/create-abonne")
    public ResponseEntity<?> createAbonne(@RequestBody Map<String, String> request) {
        request.put("role", "ABONNE");
        return createUser(request);
    }

    // ==================== MISE À JOUR D'UN UTILISATEUR ====================
    @PutMapping("/users/{id}")
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
                return ResponseEntity.badRequest().body(Map.of(
                        "error", "Rôle invalide. Utilisez: ADMIN, AGENT ou ABONNE"
                ));
            }
        }

        Utilisateur updated = utilisateurRepository.save(user);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Utilisateur mis à jour avec succès");
        response.put("user", updated);

        return ResponseEntity.ok(response);
    }

    // ==================== SUPPRESSION D'UN UTILISATEUR ====================
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (!utilisateurRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        utilisateurRepository.deleteById(id);

        return ResponseEntity.ok(Map.of(
                "message", "Utilisateur supprimé avec succès",
                "id", id
        ));
    }

    // ==================== STATISTIQUES ====================
    @GetMapping("/stats")
    public ResponseEntity<?> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", utilisateurRepository.count());
        stats.put("admins", utilisateurRepository.countByRole(Role.ROLE_ADMIN));
        stats.put("agents", utilisateurRepository.countByRole(Role.ROLE_AGENT));
        stats.put("abonnes", utilisateurRepository.countByRole(Role.ROLE_ABONNE));

        return ResponseEntity.ok(stats);
    }

    // ==================== RECHERCHE D'UTILISATEURS ====================
    @GetMapping("/users/search")
    public ResponseEntity<List<Utilisateur>> searchUsers(@RequestParam String keyword) {
        return ResponseEntity.ok(utilisateurRepository.findByNomContainingOrEmailContaining(keyword, keyword));
    }
}