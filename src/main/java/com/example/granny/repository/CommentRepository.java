package com.example.granny.repository;

import com.example.granny.domain.entities.Comment;
import com.example.granny.domain.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query("SELECT c " +
            "FROM Comment c " +
            "WHERE c.cause.id=:id " +
            "ORDER BY c.publishingDate DESC")
    List<Comment> findAll(@Param("id") Integer id);

    @Modifying
    @Transactional
    @Query("DELETE FROM Comment c where c.id =:id")
    void delete(@Param("id") Integer id);

}
