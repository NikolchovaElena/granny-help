package com.example.granny.service;

import com.example.granny.domain.entities.User;
import com.example.granny.domain.entities.VerificationToken;
import com.example.granny.repository.VerificationTokenRepository;
import com.example.granny.service.api.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional(rollbackOn = Exception.class)
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private VerificationTokenRepository tokenRepository;

    @Autowired
    public VerificationTokenServiceImpl(VerificationTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void createVerificationToken(User user, String token) {
        VerificationToken verificationToken = new VerificationToken(user, token);
        tokenRepository.save(verificationToken);
    }

    @Override
    public void delete(User user) {
        VerificationToken token = tokenRepository.findByUser(user);

        if (token != null) {
            tokenRepository.delete(token);
        }
    }

    @Override
    public VerificationToken findBy(String token) {
        return tokenRepository.findByToken(token);
    }
}
