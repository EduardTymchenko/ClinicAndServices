package com.example.clinic.controllers;

import com.example.clinic.exeption.ResourceNotCreatedException;
import com.example.clinic.exeption.ResourceNotFoundException;
import com.example.clinic.exeption.ResourceNotUpdateException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({
            ResourceNotFoundException.class,
            ResourceNotCreatedException.class,
            ResourceNotUpdateException.class})
    @ResponseBody
    public ErrorInfo handResourceNotFound(Exception e) {
        return new ErrorInfo(e.getMessage());
    }
}
