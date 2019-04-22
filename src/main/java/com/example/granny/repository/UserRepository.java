package com.example.granny.repository;

import com.example.granny.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    int countByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<User> findById(Integer id);

    @Query("SELECT u " +
            "FROM User u " +
            "WHERE u.enabled = 1 " +
            "ORDER BY u.firstName, u.lastName ")
    List<User> findAll();

    @Query(value = "SELECT * " +
            "FROM users u " +
            "WHERE u.enabled = 1 " +
            "ORDER BY RAND() " +
            "LIMIT 5", nativeQuery = true)
    List<User> findFiveRandomUsers();
}
