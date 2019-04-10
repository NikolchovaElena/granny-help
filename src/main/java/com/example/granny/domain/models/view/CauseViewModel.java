package com.example.granny.domain.models.view;

import com.example.granny.domain.entities.Comment;
import com.example.granny.domain.models.service.LocationServiceModel;
import com.example.granny.domain.models.service.UserServiceModel;

import java.time.LocalDate;
import java.util.List;

public class CauseViewModel {

    private Integer id;
    private String title;
    private String causeImgUrl;
    private String description;
    private LocationViewModel location;
    private LocalDate publishingDate;
    private UserViewModel author;

    public CauseViewModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCauseImgUrl() {
        return causeImgUrl;
    }

    public void setCauseImgUrl(String causeImgUrl) {
        this.causeImgUrl = causeImgUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocationViewModel getLocation() {
        return location;
    }

    public void setLocation(LocationViewModel location) {
        this.location = location;
    }

    public LocalDate getPublishingDate() {
        return publishingDate;
    }

    public void setPublishingDate(LocalDate publishingDate) {
        this.publishingDate = publishingDate;
    }

    public UserViewModel getAuthor() {
        return author;
    }

    public void setAuthor(UserViewModel author) {
        this.author = author;
    }
}
