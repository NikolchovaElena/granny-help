package com.example.granny.domain.models.view;

import java.math.BigDecimal;

public class OrderedItemViewModel {
    private ProductAllViewModel product;
    private int quantity;
    private BigDecimal sum;

    public OrderedItemViewModel() {
    }

    public OrderedItemViewModel(ProductAllViewModel product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        setSum();
    }

    public ProductAllViewModel getProduct() {
        return product;
    }

    public void setProduct(ProductAllViewModel product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSum() {
        return sum;
    }

    private void setSum() {
        this.sum = this.product.getPrice().multiply(new BigDecimal(quantity));
    }

}
