package com.ferreira.auto.dto;

public class AddressExceptionDto {

    private String message;

    private String zipcode;

    public AddressExceptionDto(String message, String zipcode) {
        this.message = message;
        this.zipcode = zipcode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
