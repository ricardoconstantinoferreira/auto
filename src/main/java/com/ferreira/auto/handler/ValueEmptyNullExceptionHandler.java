package com.ferreira.auto.handler;

import com.ferreira.auto.dto.ValueEmptyNullDto;
import com.ferreira.auto.exception.ValueEmptyNullException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ValueEmptyNullExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    @ExceptionHandler(ValueEmptyNullException.class)
    public ValueEmptyNullDto handler(ValueEmptyNullException e) {
        return new ValueEmptyNullDto(
                e.getMessage(),
                e.getDescription()
        );
    }
}
