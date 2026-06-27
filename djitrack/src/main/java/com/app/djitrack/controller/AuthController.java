package com.app.djitrack.controller;

import com.app.djitrack.dto.LoginRequest;
import com.app.djitrack.dto.RegisterRequest;
import com.app.djitrack.dto.AuthResponse;
import com.app.djitrack.service.AuthService;
import com.app.djitrack.security.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final CustomUserDetailsService userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request,
                                      HttpServletRequest httpRequest,
                                      HttpServletResponse httpResponse) {
        try {
            AuthResponse authResponse = authService.register(request);
            // Créer une session Spring Security pour que la navigation fonctionne
            createSpringSecuritySession(authResponse.getEmail(), httpRequest);
            return ResponseEntity.ok(authResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request,
                                   HttpServletRequest httpRequest,
                                   HttpServletResponse httpResponse) {
        try {
            AuthResponse authResponse = authService.authenticate(request);
            // Créer une session Spring Security pour que la navigation fonctionne
            createSpringSecuritySession(authResponse.getEmail(), httpRequest);
            return ResponseEntity.ok(authResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private void createSpringSecuritySession(String email, HttpServletRequest request) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authToken);
        SecurityContextHolder.setContext(context);
        // Sauvegarder dans la session HTTP → Spring Security reconnaîtra l'utilisateur sur les pages suivantes
        request.getSession(true).setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context
        );
    }
}
