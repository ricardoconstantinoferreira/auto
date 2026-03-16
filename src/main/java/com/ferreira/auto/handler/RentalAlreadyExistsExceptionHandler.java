package com.ferreira.auto.handler;

import com.ferreira.auto.dto.RentalExceptionDto;
import com.ferreira.auto.exception.RentalAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RentalAlreadyExistsExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    @ExceptionHandler(RentalAlreadyExistsException.class)
    public RentalExceptionDto handler(RentalAlreadyExistsException e) {
        return new RentalExceptionDto(
                e.getMessage(),
                e.getDescription()
        );
    }
}
