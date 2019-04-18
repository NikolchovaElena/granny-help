package com.example.granny.domain.models.binding;

public class CartBindingModel {

    private Integer id;
    private int quantity;

    public CartBindingModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
