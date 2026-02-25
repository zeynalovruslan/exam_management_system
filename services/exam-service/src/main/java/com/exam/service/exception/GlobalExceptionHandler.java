package com.exam.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ConflictException.class)
    public ErrorResponse handleConflictException(ConflictException e) {
        return ErrorResponse.builder(e,
                ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage())).build();
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ErrorResponse handleUnauthorizedException(UnauthorizedException e) {
        return ErrorResponse.builder(e,
                ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage())).build();
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        final List<String> errorFields = new ArrayList<>();
        e.getBindingResult().getFieldErrors().forEach(error -> errorFields.add(error.getField() + ": " + error.getDefaultMessage()));
        return ErrorResponse.builder(e, ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, errorFields.toString())).build();

    }

    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse handleNotFoundException(NotFoundException e) {
        return ErrorResponse.builder(e,
                ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage())).build();
    }


}
