package com.example.granny.service.api;

import com.example.granny.domain.models.binding.ProductBindingModel;
import com.example.granny.domain.models.service.ProductServiceModel;
import com.example.granny.domain.models.view.CartViewModel;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ProductService {
    ProductServiceModel create(ProductBindingModel model) throws IOException;

    List<ProductServiceModel> findAll();

    ProductServiceModel findById(Integer id);

    ProductServiceModel edit(Integer id, ProductBindingModel model) throws IOException;

    void delete(Integer id);

    List<ProductServiceModel> findFourRandomProducts(Integer id);

    List<CartViewModel> findAll(Map<Integer, Integer> products);

    BigDecimal findTotal(List<CartViewModel> products);
}
