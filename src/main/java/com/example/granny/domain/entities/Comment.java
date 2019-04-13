package com.example.granny.domain.entities;

import com.example.granny.constants.GlobalConstants;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class Comment extends BaseEntity {

    private LocalDateTime publishingDate;
    private String comment;
    private User author;
    private Cause cause;

    public Comment() {
    }

    public Comment(String comment, User author, Cause cause) {
        this.comment = comment;
        this.author = author;
        this.cause = cause;
    }

    @PrePersist
    public void prePersist() {
        publishingDate = LocalDateTime.now();
    }

    @Column(nullable = false)
    public LocalDateTime getPublishingDate() {
        return publishingDate;
    }

    public void setPublishingDate(LocalDateTime publishingDate) {
        this.publishingDate = publishingDate;
    }

    @Column(nullable = false)
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @ManyToOne
    public Cause getCause() {
        return cause;
    }

    public void setCause(Cause cause) {
        this.cause = cause;
    }

    @ManyToOne(targetEntity = User.class)
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
