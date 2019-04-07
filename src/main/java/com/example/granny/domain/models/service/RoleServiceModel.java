package com.example.granny.domain.models.service;

import com.example.granny.validation.annotations.ValidRoleAuthority;

public class RoleServiceModel extends BaseServiceModel {

    private String authority;

    public RoleServiceModel() {
    }

    @ValidRoleAuthority
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
