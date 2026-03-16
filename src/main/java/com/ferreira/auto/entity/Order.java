package com.ferreira.auto.entity;

import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order extends RepresentationModel<Order> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "customer_id",
            referencedColumnName = "id"
    )
    private Customer customer;

    @Column(nullable = false)
    private LocalDateTime dateOrder;

    @Column(nullable = false)
    private float totalPrice;

    @Column(nullable = false)
    private StatusOrder statusOrder;

    @Column(nullable = false)
    private float interestValuePayment;

    public Order() {}

    public Order(Long id, Customer customer, LocalDateTime dateOrder,
                 float totalPrice, StatusOrder statusOrder, float interestValuePayment) {
        this.id = id;
        this.customer = customer;
        this.dateOrder = dateOrder;
        this.totalPrice = totalPrice;
        this.statusOrder = statusOrder;
        this.interestValuePayment = interestValuePayment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDateTime getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(LocalDateTime dateOrder) {
        this.dateOrder = dateOrder;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public StatusOrder getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(StatusOrder statusOrder) {
        this.statusOrder = statusOrder;
    }

    public float getInterestValuePayment() {
        return interestValuePayment;
    }

    public void setInterestValuePayment(float interestValuePayment) {
        this.interestValuePayment = interestValuePayment;
    }
}
