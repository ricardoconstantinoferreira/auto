package com.ferreira.auto.dto;

public class CarmakerDto {
    private Long id;

    private String description;

    public CarmakerDto() {}

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
