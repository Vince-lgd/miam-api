package fr.vince.miam.api.exception;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ArticleIntrouvableException extends RuntimeException {
    public ArticleIntrouvableException(UUID id) {
        super("Article introuvable avec l'identifiant " + id);
    }
}