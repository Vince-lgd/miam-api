package fr.vince.miam.api.exception;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ArticleIntrouvableException.class)
    public ProblemDetail articleIntrouvable(ArticleIntrouvableException e) {
        ProblemDetail probleme = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        probleme.setTitle("Article introuvable");
        return probleme;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail donneesInvalides(MethodArgumentNotValidException e) {
        Map<String, String> erreurs = new HashMap<>();
        for (FieldError erreur : e.getBindingResult().getFieldErrors()) {
            erreurs.put(erreur.getField(), erreur.getDefaultMessage());
        }

        ProblemDetail probleme = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, "Certaines données envoyées sont invalides.");
        probleme.setTitle("Données invalides");
        probleme.setProperty("erreurs", erreurs);
        return probleme;
    }

    @ExceptionHandler(EmailDejaUtiliseException.class)
    public ProblemDetail emailDejaUtilise(EmailDejaUtiliseException e) {
        ProblemDetail probleme = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());
        probleme.setTitle("E-mail déjà utilisé");
        return probleme;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail conflitEnBase(DataIntegrityViolationException e) {
        log.warn("Contrainte d'intégrité violée en base de données");
        ProblemDetail probleme = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT, "Cette opération entre en conflit avec des données existantes.");
        probleme.setTitle("Conflit");
        return probleme;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail requeteIllisible(HttpMessageNotReadableException e) {
        ProblemDetail probleme = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, "Le corps de la requête est illisible ou contient une valeur inconnue.");
        probleme.setTitle("Requête illisible");
        return probleme;
    }
}