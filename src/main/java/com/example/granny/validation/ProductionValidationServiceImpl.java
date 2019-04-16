package com.example.granny.validation;

import com.example.granny.domain.entities.Product;
import com.example.granny.domain.models.binding.ProductBindingModel;
import com.example.granny.domain.models.service.ProductServiceModel;
import com.example.granny.validation.api.ProductValidationService;
import org.springframework.stereotype.Component;


@Component
public class ProductionValidationServiceImpl implements ProductValidationService {

    @Override
    public boolean isValid(Product product) {
        return product != null;
    }

    @Override
    public boolean isValid(ProductBindingModel product) {
        return product != null;
    }

    @Override
    public boolean isValid(ProductServiceModel product) {
        return product != null;
    }
}
