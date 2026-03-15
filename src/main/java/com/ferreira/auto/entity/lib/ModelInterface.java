package com.ferreira.auto.entity.lib;

public interface ModelInterface {
    Long getId();
    String getDescription();
    String getImage();
    String getYear();
    Long getCarmakerId();
    Long getCategoryId();
    boolean getActive();
    String getPrice();
    String getDescriptionCategory();
    String getDescriptionCarmaker();
    Integer getQtde();

}
