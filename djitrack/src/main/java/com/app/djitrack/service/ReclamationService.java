package com.app.djitrack.service;

import com.app.djitrack.entity.Reclamation;
import com.app.djitrack.repository.ReclamationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReclamationService {

    private final ReclamationRepository repository;

    public List<Reclamation> getAll() {
        return repository.findAll();
    }

    public Optional<Reclamation> getById(Long id) {
        return repository.findById(id);
    }

    public Reclamation save(Reclamation reclamation) {
        return repository.save(reclamation);
    }

    public Reclamation repondre(Long id, String reponse, String repondantNom) {
        Reclamation rec = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réclamation introuvable"));

        // Vérifier les permissions
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        boolean isAgent = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_AGENT"));
        boolean isAbonne = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ABONNE"));

        // Si c'est un abonné, vérifier que c'est sa propre réclamation
        if (isAbonne) {
            String userEmail = auth.getName();
            // Vérifier que la réclamation appartient à l'abonné connecté
            if (rec.getAbonne() == null ||
                    rec.getAbonne().getUtilisateur() == null ||
                    !rec.getAbonne().getUtilisateur().getEmail().equals(userEmail)) {
                throw new RuntimeException("Vous ne pouvez répondre qu'à vos propres réclamations");
            }
        }

        // Si c'est un admin ou un agent, ils peuvent répondre à toutes les réclamations
        // (aucune vérification supplémentaire nécessaire)

        rec.setReponse(reponse);
        rec.setRepondantNom(repondantNom);
        rec.setDateReponse(LocalDate.now());
        rec.setStatut("TRAITEE");
        return repository.save(rec);
    }

    public void deleteById(Long id) {
        // Vérifier les permissions avant de supprimer
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            throw new RuntimeException("Seul un administrateur peut supprimer une réclamation");
        }

        repository.deleteById(id);
    }
}