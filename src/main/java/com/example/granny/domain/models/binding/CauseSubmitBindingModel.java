package com.example.granny.domain.models.binding;

import com.example.granny.constants.GlobalConstants;
import com.example.granny.domain.entities.Comment;
import com.example.granny.domain.entities.Location;
import com.example.granny.domain.entities.User;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public class CauseSubmitBindingModel {

    private String title;
    private MultipartFile causeImgUrl;
    private String description;
    private Location location;
    private User author;

    public CauseSubmitBindingModel() {
    }

    @NotNull(message = GlobalConstants.FIELD_IS_REQUIRED)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MultipartFile getCauseImgUrl() {
        return causeImgUrl;
    }

    public void setCauseImgUrl(MultipartFile causeImgUrl) {
        this.causeImgUrl = causeImgUrl;
    }

    @NotNull(message = GlobalConstants.FIELD_IS_REQUIRED)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull(message = GlobalConstants.FIELD_IS_REQUIRED)
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
