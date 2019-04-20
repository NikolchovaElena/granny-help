package com.example.granny.domain.models.view;

import com.example.granny.domain.entities.Role;

import java.util.List;
import java.util.Set;

public class UserShowViewModel {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private Set<String> authorities;

    public UserShowViewModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
