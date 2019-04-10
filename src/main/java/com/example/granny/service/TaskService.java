package com.example.granny.service;

import com.example.granny.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Date;

@Service
@Transactional
public class TaskService {

    private final VerificationTokenRepository tokenRepository;

    @Autowired
    public TaskService(VerificationTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Scheduled(cron = "${purge.cron.expression}")
    public void purgeExpiredTokens() {
        Date now = Date.from(Instant.now());
        tokenRepository.deleteByExpiryDateLessThan(now);
    }
}
