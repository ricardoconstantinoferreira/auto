package com.ferreira.auto.handler;

import com.ferreira.auto.dto.PrerentExceptionDto;
import com.ferreira.auto.exception.PrerentAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PrerentAlreadyExistsModelInCartExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    @ExceptionHandler(PrerentAlreadyExistsException.class)
    public PrerentExceptionDto handler(PrerentAlreadyExistsException e) {
        return new PrerentExceptionDto(
                e.getMessage(),
                e.getModelId()
        );
    }
}
