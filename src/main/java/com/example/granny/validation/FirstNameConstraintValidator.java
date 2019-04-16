package com.example.granny.validation;

import com.example.granny.validation.annotations.ValidFirstName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FirstNameConstraintValidator implements ConstraintValidator<ValidFirstName, String> {

    @Override
    public void initialize(ValidFirstName constraintAnnotation) {
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        if (name == null) {
            return true;
        }

        if (name.length() < 1 || name.length() > 20) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ValidFirstName.FIRST_NAME_LENGTH_ERROR_MESSAGE)
                    .addConstraintViolation();
            return false;
        }

        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);

            if (!Character.isAlphabetic(c)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(ValidFirstName.FIRST_NAME_ONLY_LETTERS)
                        .addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}
