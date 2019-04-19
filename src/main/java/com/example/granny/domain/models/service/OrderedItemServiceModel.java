package com.example.granny.domain.models.service;

public class OrderedItemServiceModel {

    private ProductServiceModel product;
    private int quantity;

    public OrderedItemServiceModel() {
    }

    public OrderedItemServiceModel(ProductServiceModel product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public ProductServiceModel getProduct() {
        return product;
    }

    public void setProduct(ProductServiceModel product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
