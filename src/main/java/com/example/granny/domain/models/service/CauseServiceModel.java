package com.example.granny.domain.models.service;

import com.example.granny.constants.GlobalConstants;
import com.example.granny.domain.entities.Comment;
import com.example.granny.domain.entities.Location;
import com.example.granny.domain.entities.User;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public class CauseServiceModel extends BaseServiceModel {
    private String title;
    private String imageUrl;
    private String description;
    private LocationServiceModel location;
    private LocalDate publishingDate;
    private UserServiceModel author;
    private List<Comment> comments;
    private boolean isApproved;

    public CauseServiceModel() {
    }

    @NotNull(message = GlobalConstants.FIELD_IS_REQUIRED)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String causeImgUrl) {
        this.imageUrl = causeImgUrl;
    }

    @NotNull(message = GlobalConstants.FIELD_IS_REQUIRED)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull(message = GlobalConstants.FIELD_IS_REQUIRED)
    public LocationServiceModel getLocation() {
        return location;
    }

    public void setLocation(LocationServiceModel location) {
        this.location = location;
    }

    public LocalDate getPublishingDate() {
        return publishingDate;
    }

    public void setPublishingDate(LocalDate publishingDate) {
        this.publishingDate = publishingDate;
    }

    public UserServiceModel getAuthor() {
        return author;
    }

    public void setAuthor(UserServiceModel author) {
        this.author = author;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }
}
