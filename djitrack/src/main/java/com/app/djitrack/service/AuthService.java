package com.app.djitrack.service;

import com.app.djitrack.config.JwtService;
import com.app.djitrack.dto.AuthResponse;
import com.app.djitrack.dto.LoginRequest;
import com.app.djitrack.dto.RegisterRequest;
import com.app.djitrack.entity.Role;
import com.app.djitrack.entity.Utilisateur;
import com.app.djitrack.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UtilisateurRepository repository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {
        Utilisateur user = Utilisateur.builder()
                .nom(request.getNom())
                .email(request.getEmail())
                .telephone(request.getTelephone())
                .motDePasse(encoder.encode(request.getPassword()))
                .role(Role.ROLE_ABONNE)
                .build();

        repository.save(user);
        String token = jwtService.generateToken(user.getEmail());
        return new AuthResponse(token);
    }

    public AuthResponse authenticate(LoginRequest request) {
        Utilisateur user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Email ou mot de passe incorrect"));

        if (!encoder.matches(request.getPassword(), user.getMotDePasse())) {
            throw new RuntimeException("Email ou mot de passe incorrect");
        }

        String token = jwtService.generateToken(user.getEmail());
        return new AuthResponse(token);
    }
}