package com.example.granny.validation.api;

import com.example.granny.domain.models.service.UserServiceModel;

public interface UserValidationService {
    boolean isValid(UserServiceModel user);
}
