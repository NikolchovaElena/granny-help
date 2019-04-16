package com.example.granny.service.api;

import com.example.granny.domain.models.binding.ProductBindingModel;
import com.example.granny.domain.models.service.ProductServiceModel;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    ProductServiceModel create(ProductBindingModel model) throws IOException;

   List<ProductServiceModel> findAll();

    ProductServiceModel findById(Integer id);

    ProductServiceModel edit(Integer id, ProductBindingModel model) throws IOException;

    void delete(Integer id);
}
