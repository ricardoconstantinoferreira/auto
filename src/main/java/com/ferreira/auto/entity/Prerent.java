package com.ferreira.auto.entity;

import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@Entity
@Table(name = "prerent")
public class Prerent extends RepresentationModel<Prerent> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "customer_id",
            referencedColumnName = "id"
    )
    private Customer customer;

    @ManyToOne
    @JoinColumn(
            name = "model_id",
            referencedColumnName = "id"
    )
    private Model model;

    @Column(nullable = false)
    private StatusPrerent status;

    public Prerent() {}

    public Prerent(Long id, Customer customer, Model model, StatusPrerent status) {
        this.id = id;
        this.customer = customer;
        this.model = model;
        this.status = status;
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

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public StatusPrerent getStatus() {
        return status;
    }

    public void setStatus(StatusPrerent status) {
        this.status = status;
    }
}
