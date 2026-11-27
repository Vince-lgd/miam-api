package fr.vince.miam.api.dto;

import fr.vince.miam.api.validation.TailleMaxOctets;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record InscriptionRequest(

        @NotBlank(message = "L'e-mail est obligatoire") @Email(message = "L'e-mail n'est pas valide") @Size(max = 254, message = "L'e-mail est trop long") String email,

        @NotBlank(message = "Le mot de passe est obligatoire") @Size(min = 12, message = "Le mot de passe doit contenir au moins 12 caractères") @TailleMaxOctets(value = 72, message = "Le mot de passe est trop long") String motDePasse,

        @NotBlank(message = "Le nom du foyer est obligatoire") @Size(max = 50, message = "Le nom du foyer ne doit pas dépasser 50 caractères") String nomFoyer) {
}