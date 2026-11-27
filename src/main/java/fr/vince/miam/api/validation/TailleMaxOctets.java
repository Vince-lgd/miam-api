package fr.vince.miam.api.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TailleMaxOctetsValidator.class)
public @interface TailleMaxOctets {

    int value();

    String message() default "Ce champ est trop long";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}