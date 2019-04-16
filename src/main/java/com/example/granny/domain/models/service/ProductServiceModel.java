package com.example.granny.domain.models.service;

import com.example.granny.constants.GlobalConstants;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ProductServiceModel extends BaseServiceModel {

    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private LocalDate date;

    public ProductServiceModel() {
    }

    @NotNull(message = GlobalConstants.FIELD_IS_REQUIRED)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = GlobalConstants.FIELD_IS_REQUIRED)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull(message = GlobalConstants.FIELD_IS_REQUIRED)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
