package com.app.djitrack.config;

import com.app.djitrack.entity.Role;
import com.app.djitrack.entity.Utilisateur;
import com.app.djitrack.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        // ========== CRÉATION DU COMPTE ADMIN ==========
        if (utilisateurRepository.findByEmail("admin@djitrack.com").isEmpty()) {
            Utilisateur admin = Utilisateur.builder()
                    .nom("Administrateur")
                    .email("admin@djitrack.com")
                    .telephone("+225 00 00 00 00")
                    .motDePasse(passwordEncoder.encode("admin123"))
                    .role(Role.ROLE_ADMIN)
                    .build();
            utilisateurRepository.save(admin);
            System.out.println("✅ Admin créé: admin@djitrack.com / admin123");
        }

        // ========== CRÉATION DU COMPTE AGENT ==========
        if (utilisateurRepository.findByEmail("agent@djitrack.com").isEmpty()) {
            Utilisateur agent = Utilisateur.builder()
                    .nom("Agent Commercial")
                    .email("agent@djitrack.com")
                    .telephone("+225 11 11 11 11")
                    .motDePasse(passwordEncoder.encode("agent123"))
                    .role(Role.ROLE_AGENT)
                    .build();
            utilisateurRepository.save(agent);
            System.out.println("✅ Agent créé: agent@djitrack.com / agent123");
        }

        // ========== CRÉATION DU COMPTE ABONNÉ ==========
        if (utilisateurRepository.findByEmail("abonne@djitrack.com").isEmpty()) {
            Utilisateur abonne = Utilisateur.builder()
                    .nom("Abonné Test")
                    .email("abonne@djitrack.com")
                    .telephone("+225 22 22 22 22")
                    .motDePasse(passwordEncoder.encode("abonne123"))
                    .role(Role.ROLE_ABONNE)
                    .build();
            utilisateurRepository.save(abonne);
            System.out.println("✅ Abonné créé: abonne@djitrack.com / abonne123");
        }

        // ========== CRÉATION DE COMPTES SUPPLÉMENTAIRES ==========
        // Ajouter d'autres agents
        if (utilisateurRepository.findByEmail("agent2@djitrack.com").isEmpty()) {
            Utilisateur agent2 = Utilisateur.builder()
                    .nom("Agent Jean")
                    .email("agent2@djitrack.com")
                    .telephone("+225 33 33 33 33")
                    .motDePasse(passwordEncoder.encode("agent123"))
                    .role(Role.ROLE_AGENT)
                    .build();
            utilisateurRepository.save(agent2);
            System.out.println("✅ Agent2 créé: agent2@djitrack.com / agent123");
        }

        // Ajouter d'autres abonnés
        if (utilisateurRepository.findByEmail("abonne2@djitrack.com").isEmpty()) {
            Utilisateur abonne2 = Utilisateur.builder()
                    .nom("Abonné Marie")
                    .email("abonne2@djitrack.com")
                    .telephone("+225 44 44 44 44")
                    .motDePasse(passwordEncoder.encode("abonne123"))
                    .role(Role.ROLE_ABONNE)
                    .build();
            utilisateurRepository.save(abonne2);
            System.out.println("✅ Abonné2 créé: abonne2@djitrack.com / abonne123");
        }

        // Afficher le résumé des comptes créés
        System.out.println("\n========================================");
        System.out.println("🔑 COMPTES DE TEST DISPONIBLES :");
        System.out.println("========================================");
        System.out.println("👑 ADMIN : admin@djitrack.com / admin123");
        System.out.println("👤 AGENT : agent@djitrack.com / agent123");
        System.out.println("👤 AGENT2: agent2@djitrack.com / agent123");
        System.out.println("👥 ABONNE: abonne@djitrack.com / abonne123");
        System.out.println("👥 ABONNE2: abonne2@djitrack.com / abonne123");
        System.out.println("========================================\n");
    }
}