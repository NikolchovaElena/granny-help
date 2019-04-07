package com.example.granny.domain.models.binding;

import com.example.granny.constants.GlobalConstants;
import com.example.granny.validation.annotations.ValidEmail;
import com.example.granny.validation.annotations.ValidFirstName;
import com.example.granny.validation.annotations.ValidLastName;
import com.example.granny.validation.annotations.ValidPassword;

import javax.validation.constraints.NotNull;

public class UserRegisterBindingModel {

    private String firstName;
    private String lastName;
    private String password;
    private String confirmPassword;
    private String email;

    public UserRegisterBindingModel() {
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

    @ValidPassword
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotNull(message = GlobalConstants.FIELD_IS_REQUIRED)
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @ValidEmail
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
