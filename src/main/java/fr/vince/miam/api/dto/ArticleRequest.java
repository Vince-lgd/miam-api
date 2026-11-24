package fr.vince.miam.api.dto;

import fr.vince.miam.api.model.Categorie;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record ArticleRequest(
        @NotBlank(message = "Le nom de l'article est obligatoire") String nom,
        @Min(value = 1, message = "La quantité doit être d'au moins 1") Integer quantite,
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