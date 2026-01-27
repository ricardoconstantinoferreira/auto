package com.ferreira.auto.handler;

import com.ferreira.auto.dto.CustomerExceptionDto;
import com.ferreira.auto.exception.CustomerAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CustomerAlreadyExistsExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public CustomerExceptionDto handler(CustomerAlreadyExistsException e) {
        return new CustomerExceptionDto(
                e.getMessage(),
                e.getEmail()
        );
    }
}
