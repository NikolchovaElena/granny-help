package com.example.granny.repository;

import com.example.granny.domain.entities.Cause;
import com.example.granny.domain.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CauseRepository extends JpaRepository<Cause, Integer> {

    @Query("SELECT c " +
            "FROM Cause c " +
            "WHERE c.approved = true " +
            "ORDER BY c.publishingDate")
    List<Cause> findAll();

    @Query("SELECT c " +
            "FROM Cause c " +
            "WHERE c.approved = true " +
            "AND c.author.id=:id " +
            "ORDER BY c.publishingDate")
    List<Cause> findAllApprovedByAuthorId(@Param("id") Integer id);

    @Query("SELECT c " +
            "FROM Cause c " +
            "WHERE c.approved = false " +
            "AND c.author.id=:id " +
            "ORDER BY c.publishingDate")
    List<Cause> findAllPendingByAuthorId(@Param("id") Integer id);

    @Query("SELECT c " +
            "FROM Cause c " +
            "WHERE c.approved = false " +
            "ORDER BY c.publishingDate")
    List<Cause> findAllPending();
}
