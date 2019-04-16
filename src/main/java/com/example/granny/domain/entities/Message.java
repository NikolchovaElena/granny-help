package com.example.granny.domain.entities;

import com.example.granny.validation.annotations.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "messages")
public class Message extends BaseEntity {

    String firstName;
    String lastName;
    String email;
    String phoneNumber;
    String subject;
    String message;
    private LocalDate date;

    public Message() {
    }

    @PrePersist
    public void prePersist() {
        date = LocalDate.now();
    }

    @ValidFirstName
    @Column(nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @ValidLastName
    @Column(nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @ValidEmail
    @Column(nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @ValidPhoneNumber
    @Column
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @ValidMessageSubject
    @Column(nullable = false)
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Column(nullable = false, columnDefinition = "TEXT")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
