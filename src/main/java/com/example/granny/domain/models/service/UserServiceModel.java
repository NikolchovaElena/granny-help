package com.example.granny.domain.models.service;

import com.example.granny.constants.GlobalConstants;
import com.example.granny.validation.annotations.ValidFirstName;
import com.example.granny.validation.annotations.ValidLastName;
import com.example.granny.validation.annotations.ValidEmail;

import javax.validation.constraints.NotNull;
import java.util.Set;

public class UserServiceModel extends BaseServiceModel {

    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String imageUrl;
    private String about;
    private Set<RoleServiceModel> authorities;
    private Set<CauseServiceModel> pins;
    private AddressServiceModel billingDetails;

    public UserServiceModel() {
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

    @NotNull(message = GlobalConstants.FIELD_IS_REQUIRED)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ValidEmail
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Set<RoleServiceModel> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<RoleServiceModel> authorities) {
        this.authorities = authorities;
    }

    public Set<CauseServiceModel> getPins() {
        return pins;
    }

    public void setPins(Set<CauseServiceModel> pins) {
        this.pins = pins;
    }

    public AddressServiceModel getBillingDetails() {
        return billingDetails;
    }

    public void setBillingDetails(AddressServiceModel billingDetails) {
        this.billingDetails = billingDetails;
    }
}
