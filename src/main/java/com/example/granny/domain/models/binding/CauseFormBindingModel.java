package com.example.granny.domain.models.binding;

import com.example.granny.constants.GlobalConstants;
import com.example.granny.domain.entities.Comment;
import com.example.granny.domain.entities.Location;
import com.example.granny.domain.entities.User;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public class CauseFormBindingModel {

    private String title;
    private MultipartFile imageUrl;
    private String description;
    private Location location;

    public CauseFormBindingModel() {
    }

    @NotNull(message = GlobalConstants.FIELD_IS_REQUIRED)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MultipartFile getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(MultipartFile imageUrl) {
        this.imageUrl = imageUrl;
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
}
