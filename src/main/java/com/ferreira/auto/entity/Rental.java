package com.ferreira.auto.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@Entity
@Table(name = "rental")
public class Rental extends RepresentationModel<Rental> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "qtde_days_rent")
    private Long qtdeDaysRent;

    @Column(name = "percentage_interest")
    private Long percentageInterest;

    public Rental() {
    }

    public Rental(Long id, Long qtdeDaysRent, Long percentageInterest) {
        this.id = id;
        this.qtdeDaysRent = qtdeDaysRent;
        this.percentageInterest = percentageInterest;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQtdeDaysRent() {
        return qtdeDaysRent;
    }

    public void setQtdeDaysRent(Long qtdeDaysRent) {
        this.qtdeDaysRent = qtdeDaysRent;
    }

    public Long getPercentageInterest() {
        return percentageInterest;
    }

    public void setPercentageInterest(Long percentageInterest) {
        this.percentageInterest = percentageInterest;
    }
}
