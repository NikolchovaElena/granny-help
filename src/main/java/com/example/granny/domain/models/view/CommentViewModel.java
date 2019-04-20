package com.example.granny.domain.models.view;

import com.example.granny.domain.models.service.CauseServiceModel;
import com.example.granny.domain.models.service.UserServiceModel;

import java.time.LocalDateTime;

public class CommentViewModel {

    private Integer id;
    private String publishingDate;
    private String comment;
    private String authorName;

    public CommentViewModel() {
    }

    public CommentViewModel(Integer id, String publishingDate, String comment, String authorName) {
        this.id = id;
        this.publishingDate = publishingDate;
        this.comment = comment;
        this.authorName = authorName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPublishingDate() {
        return publishingDate;
    }

    public void setPublishingDate(String publishingDate) {
        this.publishingDate = publishingDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
