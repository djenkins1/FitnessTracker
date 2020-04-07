package com.djenkins.fitness.error;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class FitnessWeekErrorHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(FitnessWeekErrorHandler.class);
    
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleTodoNotFoundException(EntityNotFoundException ex) {
        LOGGER.error( "Could not find element" , ex );
    }
}
