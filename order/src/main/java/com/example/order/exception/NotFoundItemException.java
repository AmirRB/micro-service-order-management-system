package com.example.order.exception;

import org.springframework.http.HttpStatus;

public class NotFoundItemException extends ExceptionTemplate{


    public NotFoundItemException() {
        super("Item not available in inventory");
    }

    @Override
    public HttpStatus httpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
