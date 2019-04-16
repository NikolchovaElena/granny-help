package com.example.granny.domain.models.view;

import org.springframework.web.multipart.MultipartFile;

public class UserViewModel {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String imageUrl;
    private String about;

    public UserViewModel() {
    }

    public UserViewModel(String firstName, String lastName, String imageUrl, String about) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageUrl = imageUrl;
        this.about = about;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
