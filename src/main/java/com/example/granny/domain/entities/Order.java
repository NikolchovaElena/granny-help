package com.example.granny.domain.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    private String number;
    private String shippingDetails;
    private User customer;
    private LocalDate placedOn;
    private BigDecimal priceToPay;
    private String notes;
    private boolean status;
    private List<OrderedItem> items;

    public Order() {
    }

    @PrePersist
    private void prePersist() {
        placedOn = LocalDate.now();
        number = UUID.randomUUID().toString();
        status = false;
    }

    @Column(nullable = false)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @ManyToOne(targetEntity = User.class)
    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    @Column(nullable = false)
    public String getShippingDetails() {
        return shippingDetails;
    }

    public void setShippingDetails(String shippingDetails) {
        this.shippingDetails = shippingDetails;
    }

    @Column(nullable = false)
    public LocalDate getPlacedOn() {
        return placedOn;
    }

    public void setPlacedOn(LocalDate placedOn) {
        this.placedOn = placedOn;
    }

    @Column(nullable = false)
    public BigDecimal getPriceToPay() {
        return priceToPay;
    }

    public void setPriceToPay(BigDecimal priceToPay) {
        this.priceToPay = priceToPay;
    }

    @Column(nullable = false)
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @OneToMany(mappedBy = "order")
    public List<OrderedItem> getItems() {
        return items;
    }

    public void setItems(List<OrderedItem> items) {
        this.items = items;
    }

    @Column
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
