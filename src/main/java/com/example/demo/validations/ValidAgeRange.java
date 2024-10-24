package com.example.demo.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = AgeRangeValidator.class)
@Target({ ElementType.TYPE })  // Анотацията ще се прилага на ниво клас
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAgeRange {
    String message() default "Минималната възраст не може да бъде по-голяма от максималната възраст";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

