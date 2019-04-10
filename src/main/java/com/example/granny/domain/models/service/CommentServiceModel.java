package com.example.granny.domain.models.service;

import com.example.granny.constants.GlobalConstants;
import com.example.granny.domain.entities.Cause;
import com.example.granny.domain.entities.User;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class CommentServiceModel extends BaseServiceModel {

    private LocalDateTime publishingDate;
    private String comment;
    private UserServiceModel author;
    private CauseServiceModel cause;

    public CommentServiceModel() {
    }

    public LocalDateTime getPublishingDate() {
        return publishingDate;
    }

    public void setPublishingDate(LocalDateTime publishingDate) {
        this.publishingDate = publishingDate;
    }

    @NotNull(message = GlobalConstants.FIELD_IS_REQUIRED)
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public CauseServiceModel getCause() {
        return cause;
    }

    public void setCause(CauseServiceModel cause) {
        this.cause = cause;
    }

    public UserServiceModel getAuthor() {
        return author;
    }

    public void setAuthor(UserServiceModel author) {
        this.author = author;
    }
}
