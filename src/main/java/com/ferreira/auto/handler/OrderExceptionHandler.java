package com.ferreira.auto.handler;

import com.ferreira.auto.dto.OrderExceptionDto;
import com.ferreira.auto.exception.OrderWithoutStockException;
import com.ferreira.auto.exception.PartialOrderPlacedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class OrderExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    @ExceptionHandler(OrderWithoutStockException.class)
    public OrderExceptionDto handler(OrderWithoutStockException e) {
        return new OrderExceptionDto(
                e.getMessage(),
                e.getDescription()
        );
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    @ExceptionHandler(PartialOrderPlacedException.class)
    public OrderExceptionDto handler(PartialOrderPlacedException e) {
        return new OrderExceptionDto(
                e.getMessage(),
                e.getDescription()
        );
    }
}
