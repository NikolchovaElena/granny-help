package com.example.granny.service.api;

import com.example.granny.domain.entities.User;

import java.util.Locale;

public interface EventService {
    void initiateRegisterVerificationEvent(String email, Locale locale, String appUrl);

}