package com.example.granny.domain.models.binding;

import com.example.granny.validation.annotations.ValidFirstName;
import com.example.granny.validation.annotations.ValidLastName;
import org.springframework.web.multipart.MultipartFile;

public class UserEditBindingModel {

    private String firstName;
    private String lastName;
    private MultipartFile imageUrl;
    private String about;

    public UserEditBindingModel() {
    }

    @ValidFirstName
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @ValidLastName
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public MultipartFile getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(MultipartFile imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
