package com.cdac.feedback.configs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;



@ControllerAdvice
@RestController

public class ExceptionHelper {

    private  final Logger logger = LoggerFactory.getLogger(ExceptionHelper.class);

    

    @ExceptionHandler(value = { RuntimeException.class })

    public ResponseEntity<Object> handleInvalidInputException(RuntimeException ex) {
    	
   //	ex.printStackTrace();
    	
    return new ResponseEntity<Object>(ex.getMessage(),HttpStatus.REQUEST_TIMEOUT);

    }

    

    @ExceptionHandler(value = { Unauthorized.class })

    public ResponseEntity<Object> handleUnauthorizedException(Unauthorized ex) {

    	

        return new ResponseEntity<Object>(ex.getMessage(),HttpStatus.UNAUTHORIZED);

    }

    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    	
       Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<Set<String>> handleConstraintViolation(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();

    Set<String> messages = new HashSet<>(constraintViolations.size());
    messages.addAll(constraintViolations.stream()
            .map(constraintViolation -> String.format("%s value '%s' %s", constraintViolation.getPropertyPath(),
                    constraintViolation.getInvalidValue(), constraintViolation.getMessage()))
            .collect(Collectors.toList()));

    return new ResponseEntity<>(messages, HttpStatus.BAD_REQUEST);
    }
    

    @ExceptionHandler(value = { Exception.class })

    public ResponseEntity<Object> handleException(Exception ex) {

    	

    	   return new ResponseEntity<Object>(ex.getMessage(),HttpStatus.REQUEST_TIMEOUT);

    }

}