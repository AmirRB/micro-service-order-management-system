package com.example.order.exception;

import org.springframework.http.HttpStatus;

public abstract class ExceptionTemplate extends RuntimeException {
    public ExceptionTemplate(String message) {
        super(message);
    }
    public abstract HttpStatus httpStatus();
}
