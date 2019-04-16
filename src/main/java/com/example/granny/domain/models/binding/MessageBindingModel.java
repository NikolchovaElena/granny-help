package com.example.granny.domain.models.binding;

import com.example.granny.constants.GlobalConstants;
import com.example.granny.validation.annotations.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class MessageBindingModel {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String subject;
    private String message;

    public MessageBindingModel() {
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

    @ValidMessageSubject
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @NotNull(message = GlobalConstants.FIELD_IS_REQUIRED)
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
