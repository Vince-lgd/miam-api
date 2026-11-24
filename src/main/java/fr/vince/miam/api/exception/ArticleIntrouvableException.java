package fr.vince.miam.api.exception;

import java.util.UUID;

public class ArticleIntrouvableException extends RuntimeException {
    public ArticleIntrouvableException(UUID id) {
        super("Article introuvable avec l'identifiant " + id);
    }
}