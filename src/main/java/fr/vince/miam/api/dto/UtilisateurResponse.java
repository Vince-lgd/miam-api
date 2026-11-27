package fr.vince.miam.api.dto;

import java.util.UUID;

import fr.vince.miam.api.model.Utilisateur;

public record UtilisateurResponse(UUID id, String email, String nomFoyer) {

    public static UtilisateurResponse depuis(Utilisateur utilisateur) {
        return new UtilisateurResponse(
                utilisateur.getId(),
                utilisateur.getEmail(),
                utilisateur.getFoyer().getNom());
    }
}