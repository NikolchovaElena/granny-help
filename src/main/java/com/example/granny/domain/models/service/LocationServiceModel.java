package com.example.granny.domain.models.service;

import com.example.granny.constants.GlobalConstants;

import javax.validation.constraints.NotNull;

public class LocationServiceModel extends BaseServiceModel {

    String name;

    public LocationServiceModel() {
    }

    @NotNull(message = GlobalConstants.FIELD_IS_REQUIRED)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
