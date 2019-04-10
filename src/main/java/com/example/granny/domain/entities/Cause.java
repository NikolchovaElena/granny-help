package com.example.granny.domain.entities;

import com.example.granny.constants.GlobalConstants;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "causes")
public class Cause extends BaseEntity {

    private String title;
    private String causeImgUrl;
    private String description;
    private Location location;
    private LocalDate publishingDate;
    private User author;
    private List<Comment> comments;
    private boolean isApproved;

    public Cause() {
    }

    @PrePersist
    public void prePersist() {
        if (causeImgUrl == null) {
            causeImgUrl = GlobalConstants.CAUSE_DEFAULT_IMG;
        }
        publishingDate = LocalDate.now();
    }

    @Column(nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(nullable = false)
    public String getCauseImgUrl() {
        return causeImgUrl;
    }

    public void setCauseImgUrl(String causeImgUrl) {
        this.causeImgUrl = causeImgUrl;
    }

    @Column(nullable = false, columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToOne
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Column(nullable = false)
    public LocalDate getPublishingDate() {
        return publishingDate;
    }

    public void setPublishingDate(LocalDate publishingDate) {
        this.publishingDate = publishingDate;
    }

    @ManyToOne(targetEntity = User.class)
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Column
    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    @OneToMany
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
