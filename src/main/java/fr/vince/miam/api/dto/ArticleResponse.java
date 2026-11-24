package fr.vince.miam.api.dto;

import java.util.UUID;

import fr.vince.miam.api.model.Article;
import fr.vince.miam.api.model.Categorie;

public record ArticleResponse(
        UUID id,
        String nom,
        int quantite,
        boolean achete,
        Categorie categorie) {

    public static ArticleResponse depuis(Article article) {
        return new ArticleResponse(
                article.getId(),
                article.getNom(),
                article.getQuantite(),
                article.isAchete(),
                article.getCategorie());
    }
}