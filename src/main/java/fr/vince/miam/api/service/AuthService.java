package fr.vince.miam.api.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.vince.miam.api.dto.InscriptionRequest;
import fr.vince.miam.api.dto.UtilisateurResponse;
import fr.vince.miam.api.exception.EmailDejaUtiliseException;
import fr.vince.miam.api.model.Foyer;
import fr.vince.miam.api.model.Utilisateur;
import fr.vince.miam.api.repository.FoyerRepository;
import fr.vince.miam.api.repository.UtilisateurRepository;

@Service
public class AuthService {

    private final UtilisateurRepository utilisateurs;
    private final FoyerRepository foyers;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UtilisateurRepository utilisateurs, FoyerRepository foyers,
            PasswordEncoder passwordEncoder) {
        this.utilisateurs = utilisateurs;
        this.foyers = foyers;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UtilisateurResponse inscrire(InscriptionRequest demande) {
        String email = demande.email().trim().toLowerCase();
        if (utilisateurs.existsByEmail(email)) {
            throw new EmailDejaUtiliseException(email);
        }

        Foyer foyer = new Foyer();
        foyer.setNom(demande.nomFoyer().trim());
        foyers.save(foyer);

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail(email);
        utilisateur.setMotDePasse(passwordEncoder.encode(demande.motDePasse()));
        utilisateur.setFoyer(foyer);
        utilisateurs.save(utilisateur);

        return UtilisateurResponse.depuis(utilisateur);
    }
}