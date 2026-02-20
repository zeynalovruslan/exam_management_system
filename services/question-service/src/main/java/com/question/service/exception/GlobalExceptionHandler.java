package com.question.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        final List<String> errorFields = new ArrayList<>();
        e.getBindingResult().getFieldErrors().forEach(error -> errorFields.add(error.getField() + ": " + error.getDefaultMessage()));
        return ErrorResponse.builder(e, ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, errorFields.toString())).build();

    }


    @ExceptionHandler(BadRequestException.class)
    public ErrorResponse handleBadRequestException(BadRequestException e) {
        return ErrorResponse.builder(e,
                ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage())).build();
    }

    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse handleNotFoundException(NotFoundException e) {
        return ErrorResponse.builder(e,
                ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage())).build();
    }
}
