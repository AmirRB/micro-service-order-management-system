package com.example.order.exception;

import org.springframework.http.HttpStatus;

public class NotFoundOrderException extends ExceptionTemplate{


    public NotFoundOrderException() {
        super("not found order");
    }

    @Override
    public HttpStatus httpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
