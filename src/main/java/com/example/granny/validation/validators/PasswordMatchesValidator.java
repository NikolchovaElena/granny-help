package com.example.granny.validation.validators;

import com.example.granny.domain.models.binding.UserRegisterBindingModel;
import com.example.granny.validation.annotations.PasswordMatches;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        UserRegisterBindingModel bindingModel = (UserRegisterBindingModel) obj;

        if (bindingModel.getPassword() == null && bindingModel.getConfirmPassword() == null) {
            return true;
        }

        return bindingModel.getPassword().equals(bindingModel.getConfirmPassword());
    }
}
