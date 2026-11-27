package fr.vince.miam.api.exception;

public class EmailDejaUtiliseException extends RuntimeException {
    public EmailDejaUtiliseException(String email) {
        super("L'e-mail " + email + " est déjà utilisé.");
    }
}