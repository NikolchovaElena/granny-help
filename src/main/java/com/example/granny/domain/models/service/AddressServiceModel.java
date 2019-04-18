package com.example.granny.domain.models.service;

import com.example.granny.constants.GlobalConstants;
import com.example.granny.validation.annotations.ValidEmail;
import com.example.granny.validation.annotations.ValidFirstName;
import com.example.granny.validation.annotations.ValidLastName;
import com.example.granny.validation.annotations.ValidPhoneNumber;

import javax.validation.constraints.NotNull;

public class AddressServiceModel extends BaseServiceModel {
    private String firstName;
    private String lastName;
    private String address;
    private String country;
    private String zip;
    private String email;
    private String phoneNumber;
    private String notes;

    public AddressServiceModel() {
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
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @NotNull(message = GlobalConstants.FIELD_IS_REQUIRED)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @NotNull(message = GlobalConstants.FIELD_IS_REQUIRED)
    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @ValidEmail
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @ValidPhoneNumber
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
