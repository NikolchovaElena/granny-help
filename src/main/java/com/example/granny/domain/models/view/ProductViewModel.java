package com.example.granny.domain.models.view;

import com.example.granny.constants.GlobalConstants;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ProductViewModel {

    private Integer id;
    private String name;
    private BigDecimal price;
    private String description;
    private MultipartFile imageUrl;

    public ProductViewModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NotNull(message = GlobalConstants.FIELD_IS_REQUIRED)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = GlobalConstants.FIELD_IS_REQUIRED)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @NotNull(message = GlobalConstants.FIELD_IS_REQUIRED)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(MultipartFile imageUrl) {
        this.imageUrl = imageUrl;
    }
}

