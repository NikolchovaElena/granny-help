package com.example.granny.service;

import com.example.granny.domain.entities.User;
import com.example.granny.domain.entities.VerificationToken;
import com.example.granny.repository.AddressDetailsRepository;
import com.example.granny.repository.UserRepository;
import com.example.granny.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TaskService {

    private final VerificationTokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final AddressDetailsRepository addressRepository;

    @Autowired
    public TaskService(VerificationTokenRepository tokenRepository, UserRepository userRepository, AddressDetailsRepository addressRepository) {
        this.tokenRepository = tokenRepository;

        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    //Runs every 12 hours
    @Scheduled(fixedRate = 43200000)
   // @Scheduled(fixedRate = 300000)
    public void purgeExpiredTokens() {
        Date now = Date.from(Instant.now());
        List<VerificationToken> tokens = tokenRepository.findAllByExpiryDateLessThan(now);
        List<User> users = tokens.stream().map(t -> t.getUser()).collect(Collectors.toList());

        users.forEach(u -> addressRepository.delete(u.getBillingDetails()));
        tokenRepository.deleteAll(tokens);
        userRepository.deleteAll(users);

       // System.out.println("Yes");
    }
}
