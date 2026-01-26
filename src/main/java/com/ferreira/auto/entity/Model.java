package com.ferreira.auto.entity;

import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@Entity
@Table(name = "model")
public class Model extends RepresentationModel<Model> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(
            name = "carmaker_id",
            referencedColumnName = "id"
    )
    private Carmaker carmaker;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private String image;

    public Model() {}

    public Model(Long id, String description, Carmaker carmaker, int year, String image) {
        this.id = id;
        this.description = description;
        this.carmaker = carmaker;
        this.year = year;
        this.image = image;
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

    public Carmaker getCarmaker() {
        return carmaker;
    }

    public void setCarmaker(Carmaker carmaker) {
        this.carmaker = carmaker;
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
}
