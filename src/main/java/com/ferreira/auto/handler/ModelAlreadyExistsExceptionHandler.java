package com.ferreira.auto.handler;

import com.ferreira.auto.dto.ModelExceptionDto;
import com.ferreira.auto.exception.ModelAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ModelAlreadyExistsExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    @ExceptionHandler(ModelAlreadyExistsException.class)
    public ModelExceptionDto handler(ModelAlreadyExistsException e) {
        return new ModelExceptionDto(
                e.getMessage(),
                e.getDescription()
        );
    }
}
