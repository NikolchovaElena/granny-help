package com.example.granny.repository;

import com.example.granny.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    int countByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<User> findById(Integer id);
}
