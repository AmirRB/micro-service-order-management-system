package com.example.inventory.exception;

import org.springframework.http.HttpStatus;

public class NotFoundInventoryException extends ExceptionTemplate{


    public NotFoundInventoryException() {
        super("Not found inventory");
    }

    @Override
    public HttpStatus httpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
