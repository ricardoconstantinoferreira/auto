package com.ferreira.auto.dto;

public class ModelDto {

    private Long id;

    private String description;

    private Long carmakerId;

    private int year;

    private String image;

    private boolean active;

    public ModelDto() {
    }

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

    public Long getCarmakerId() {
        return carmakerId;
    }

    public void setCarmakerId(Long carmakerId) {
        this.carmakerId = carmakerId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
