package com.example.granny.repository;

import com.example.granny.domain.entities.Location;
import com.example.granny.domain.entities.User;
import com.example.granny.domain.entities.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepsitory extends JpaRepository<Location, Integer> {

    @Query("SELECT l " +
            "FROM Location l" +
            " ORDER BY l.name")
    List<Location> findAll();
}


