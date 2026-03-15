package com.ferreira.auto.handler;

import com.ferreira.auto.dto.StockExceptionDto;
import com.ferreira.auto.exception.StockNoAvailableException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class StockNoAvailableExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    @ExceptionHandler(StockNoAvailableException.class)
    public StockExceptionDto handler(StockNoAvailableException e) {
        return new StockExceptionDto(
                e.getMessage(),
                e.getModelId()
        );
    }
}
