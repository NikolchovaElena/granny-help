package com.example.granny.validation.validators;

import com.example.granny.validation.annotations.ValidPassword;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {
    @Override
    public void initialize(ValidPassword constraintAnnotation) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return true;
        }

        if (password.length() < 6 || password.length() > 15) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ValidPassword.PASSWORD_LENGTH_ERROR_MESSAGE)
                    .addConstraintViolation();
            return false;
        }

        boolean foundNumber = false;
        boolean foundAlphaCharacter = false;
        boolean foundUpperCaseCharacter = false;

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);

            if (!Character.isLetterOrDigit(c)) {
                return false;
            }

            if (Character.isUpperCase(c)) {
                foundUpperCaseCharacter = true;
            }
            if (Character.isAlphabetic(c)) {
                foundAlphaCharacter = true;
            } else if (Character.isDigit(c)) {
                foundNumber = true;
            }
        }
        return foundNumber && foundAlphaCharacter && foundUpperCaseCharacter;
    }
}