package com.example.granny.validation;


import com.example.granny.domain.models.service.UserServiceModel;
import com.example.granny.validation.api.UserValidationService;
import org.springframework.stereotype.Component;

@Component
public class UserValidationServiceImpl implements UserValidationService {
    @Override
    public boolean isValid(UserServiceModel user) {
        return user != null;
    }
}
