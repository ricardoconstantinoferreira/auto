package com.ferreira.auto.handler;

import com.ferreira.auto.dto.CarmakerExceptionDto;
import com.ferreira.auto.dto.CustomerExceptionDto;
import com.ferreira.auto.exception.CarmakerAlreadyExistsException;
import com.ferreira.auto.exception.CustomerAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CarmakerAlreadyExistsExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    @ExceptionHandler(CarmakerAlreadyExistsException.class)
    public CarmakerExceptionDto handler(CarmakerAlreadyExistsException e) {
        return new CarmakerExceptionDto(
                e.getMessage(),
                e.getDescription()
        );
    }
}
