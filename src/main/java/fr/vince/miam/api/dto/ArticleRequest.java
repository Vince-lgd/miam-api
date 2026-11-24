package fr.vince.miam.api.dto;

import fr.vince.miam.api.model.Categorie;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record ArticleRequest(
        @NotBlank String nom,
        @Min(1) Integer quantite,
        Boolean achete,
        Categorie categorie) {

    public ArticleRequest {
        if (quantite == null)
            quantite = 1;
        if (achete == null)
            achete = false;
        if (categorie == null)
            categorie = Categorie.AUTRE;
    }
}