package com.example.order.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExceptionTemplate.class)
    public ResponseEntity<ErrorDetails> handleStudentNotFoundException(ExceptionTemplate ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, ex.httpStatus());
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorDetails> handleStudentNotFoundException(Exception ex, WebRequest request) throws JsonProcessingException {
        ObjectMapper objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        ErrorDetails error = objectMapper.readValue(((FeignException)ex).contentUTF8(), ErrorDetails.class);
        return new ResponseEntity(error, HttpStatus.valueOf(((FeignException)ex).status()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex, WebRequest request) {
        if (ex instanceof NoResourceFoundException) {
            ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
            return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        } else {
            ex.printStackTrace();
            ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), "internal server error", request.getDescription(false));
            return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}