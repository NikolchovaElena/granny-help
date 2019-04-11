package com.example.granny.repository;

import com.example.granny.domain.entities.Comment;
import com.example.granny.domain.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query("SELECT c " +
            "FROM Comment c " +
            "WHERE c.cause.id=:id " +
            "ORDER BY c.publishingDate DESC")
    List<Comment> findAll(@Param("id") Integer id);
}
