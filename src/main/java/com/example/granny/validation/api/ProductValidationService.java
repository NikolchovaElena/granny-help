package com.example.granny.validation.api;

import com.example.granny.domain.entities.Product;
import com.example.granny.domain.models.binding.ProductBindingModel;
import com.example.granny.domain.models.service.ProductServiceModel;

public interface ProductValidationService {
    boolean isValid(Product product);

    boolean isValid(ProductBindingModel product);

    boolean isValid(ProductServiceModel product);
}
