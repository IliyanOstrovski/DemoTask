package com.example.demo.validations;

import com.example.demo.dto.PersonSearchDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AgeRangeValidator implements ConstraintValidator<ValidAgeRange, PersonSearchDTO> {

    @Override
    public boolean isValid(PersonSearchDTO ageValidationExample, ConstraintValidatorContext context) {
        // Проверява дали minAge е по-малка или равна на maxAge
        if (ageValidationExample.getMaxAge() != null) {
            return ageValidationExample.getMinAge() <= ageValidationExample.getMaxAge();
        }
        return true; //
    }
}
