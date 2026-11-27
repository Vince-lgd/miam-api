package fr.vince.miam.api.dto;

import fr.vince.miam.api.model.Categorie;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ArticleRequest(

        @NotBlank(message = "Le nom de l'article est obligatoire") @Size(max = 100, message = "Le nom de l'article ne doit pas dépasser 100 caractères") String nom,

        @Min(value = 1, message = "La quantité doit être d'au moins 1") @Max(value = 99, message = "La quantité ne doit pas dépasser 99") Integer quantite,

        Boolean achete,

        Categorie categorie) {

    public ArticleRequest {
        if (quantite == null) {
            quantite = 1;
        }
        if (achete == null) {
            achete = false;
        }
        if (categorie == null) {
            categorie = Categorie.AUTRE;
        }
    }
}