package com.example.granny.domain.models.service;

public abstract class BaseServiceModel {

    private Integer id;

    protected BaseServiceModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
