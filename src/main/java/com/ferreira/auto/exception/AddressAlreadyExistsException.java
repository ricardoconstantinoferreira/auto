package com.ferreira.auto.exception;

public class AddressAlreadyExistsException extends RuntimeException{

    private final String zipcode;

    public AddressAlreadyExistsException(String message, String zipcode) {
        super(message);
        this.zipcode = zipcode;
    }

    public String getZipcode() {
        return zipcode;
    }
}
