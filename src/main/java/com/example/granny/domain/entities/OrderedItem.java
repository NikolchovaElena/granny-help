package com.example.granny.domain.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ordered_item")
public class OrderedItem extends BaseEntity {

    private Product product;
    private int quantity;
    private BigDecimal sum;
    private Order order;

    public OrderedItem() {
    }

    @OneToOne
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Column(nullable = false)
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Column(nullable = false)
    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    @ManyToOne
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
