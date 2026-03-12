package com.ferreira.auto.handler;

import com.ferreira.auto.dto.CategoryExceptionDto;
import com.ferreira.auto.exception.CategoryAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CategoryAlreadyExistsExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public CategoryExceptionDto handler(CategoryAlreadyExistsException e) {
        return new CategoryExceptionDto(
                e.getMessage(),
                e.getDescription()
        );
    }
}
