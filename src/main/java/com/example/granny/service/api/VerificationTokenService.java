package com.example.granny.service.api;

import com.example.granny.domain.entities.User;
import com.example.granny.domain.entities.VerificationToken;

public interface VerificationTokenService {

    void createVerificationToken(User user, String token);

    VerificationToken getVerificationToken(String token);

    void delete(User user);
}
