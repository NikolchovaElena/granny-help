package com.example.granny.repository;

import com.example.granny.domain.entities.User;
import com.example.granny.domain.entities.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Integer> {
    VerificationToken findByToken(String token);

//    @Query("SELECT u " +
//            "FROM User u" +
//            " ORDER BY u.firstName, u.lastName ASC")
  VerificationToken findByUser(User user);
}
