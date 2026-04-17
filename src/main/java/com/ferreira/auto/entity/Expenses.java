package com.ferreira.auto.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "expenses")
public class Expenses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "expenses_type_id",
            referencedColumnName = "id"
    )
    private ExpensesType expensesType;

    @ManyToOne
    @JoinColumn(
            name = "model_id",
            referencedColumnName = "id"
    )
    private Model model;

    @Column(nullable = false)
    private Float value;

    @Column(nullable = false)
    private LocalDateTime dateExpenses;

    public Expenses() {
    }

    public Expenses(Long id, ExpensesType expensesType, Model model, Float value, LocalDateTime dateExpenses) {
        this.id = id;
        this.expensesType = expensesType;
        this.model = model;
        this.value = value;
        this.dateExpenses = dateExpenses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ExpensesType getExpensesType() {
        return expensesType;
    }

    public void setExpensesType(ExpensesType expensesType) {
        this.expensesType = expensesType;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public LocalDateTime getDateExpenses() {
        return dateExpenses;
    }

    public void setDateExpenses(LocalDateTime dateExpenses) {
        this.dateExpenses = dateExpenses;
    }
}
