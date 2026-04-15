package com.ferreira.auto.dto;

public class ExpensesTypeDto {

    private Long id;

    private String description;

    public ExpensesTypeDto() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
