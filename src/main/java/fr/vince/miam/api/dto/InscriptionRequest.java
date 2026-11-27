package fr.vince.miam.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record InscriptionRequest(
        @NotBlank(message = "L'e-mail est obligatoire") @Email(message = "L'e-mail n'est pas valide") String email,

        @NotBlank(message = "Le mot de passe est obligatoire") @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères") String motDePasse,

        @NotBlank(message = "Le nom du foyer est obligatoire") String nomFoyer) {
}