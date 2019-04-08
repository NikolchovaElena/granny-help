package com.example.granny.domain.models.view;

import com.example.granny.domain.entities.Role;

public class RoleViewModel {

    private Integer id;
    private String authority;

    public RoleViewModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
