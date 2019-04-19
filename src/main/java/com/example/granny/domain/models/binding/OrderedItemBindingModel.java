package com.example.granny.domain.models.binding;

import com.example.granny.domain.entities.Product;

public class OrderedItemBindingModel {

    private int productId;
    private int quantity;

    public OrderedItemBindingModel() {
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
