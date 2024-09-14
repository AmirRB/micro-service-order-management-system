package com.example.order.exception;

import org.springframework.http.HttpStatus;

public class InsufficientException extends ExceptionTemplate{

    public InsufficientException() {
        super("Insufficient inventory");
    }

    @Override
    public HttpStatus httpStatus() {
        return HttpStatus.CONFLICT;
    }
}
