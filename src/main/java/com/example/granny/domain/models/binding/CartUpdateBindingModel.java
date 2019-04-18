package com.example.granny.domain.models.binding;

import java.util.List;

public class CartUpdateBindingModel {

    private List<CartBindingModel> products;

    public CartUpdateBindingModel() {
    }

    public List<CartBindingModel> getProducts() {
        return products;
    }

    public void setProducts(List<CartBindingModel> products) {
        this.products = products;
    }
}
