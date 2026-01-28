package com.ferreira.auto.handler;

import com.ferreira.auto.dto.AddressExceptionDto;
import com.ferreira.auto.exception.AddressAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AddressAlreadyExistsExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    @ExceptionHandler(AddressAlreadyExistsException.class)
    public AddressExceptionDto handler(AddressAlreadyExistsException e) {
        return new AddressExceptionDto(
                e.getMessage(),
                e.getZipcode()
        );
    }
}
