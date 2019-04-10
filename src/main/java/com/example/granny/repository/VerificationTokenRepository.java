package com.example.granny.repository;

import com.example.granny.domain.entities.Cause;
import com.example.granny.domain.entities.User;
import com.example.granny.domain.entities.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Integer> {

    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);

    void deleteByExpiryDateLessThan(Date now);
}

