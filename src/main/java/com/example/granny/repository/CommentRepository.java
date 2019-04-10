package com.example.granny.repository;

import com.example.granny.domain.entities.Comment;
import com.example.granny.domain.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query("SELECT c " +
            "FROM Comment c" +
            " ORDER BY c.publishingDate")
    List<Comment> findAll();
}
