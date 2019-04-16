package com.example.granny.validation;

import com.example.granny.validation.annotations.ValidLastName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LastNameConstraintValidator implements ConstraintValidator<ValidLastName, String> {
    @Override
    public void initialize(ValidLastName constraintAnnotation) {
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        if (name == null) {
            return true;
        }

        if (name.length() < 1 || name.length() > 20) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ValidLastName.LAST_NAME_ERROR_MESSAGE)
                    .addConstraintViolation();
            return false;
        }

        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);

            if (Character.isDigit(c)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(ValidLastName.LAST_NAME_ONLY_LETTERS)
                        .addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}
