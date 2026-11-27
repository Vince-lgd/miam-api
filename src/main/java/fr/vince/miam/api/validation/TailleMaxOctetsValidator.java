package fr.vince.miam.api.validation;

import java.nio.charset.StandardCharsets;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TailleMaxOctetsValidator implements ConstraintValidator<TailleMaxOctets, String> {

    private int max;

    @Override
    public void initialize(TailleMaxOctets annotation) {
        this.max = annotation.value();
    }

    @Override
    public boolean isValid(String valeur, ConstraintValidatorContext contexte) {
        if (valeur == null) {
            return true;
        }
        return valeur.getBytes(StandardCharsets.UTF_8).length <= max;
    }
}