package com.tutor.assesment.resources.exception;

import com.tutor.assesment.exceptions.InvalidInputException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.WeakHashMap;

@Slf4j
@RestControllerAdvice
public class TutorRestControllerExceptionHandlers {


    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<Object> handleInvalidProductException(final IllegalArgumentException ex) {
        log.error("Invalid Input Exception: {}", ex.getMessage());
        Map<String,String> exceptions = new WeakHashMap<>();
        exceptions.put("reason", ex.getMessage());
        return new ResponseEntity<Object>(exceptions, HttpStatus.BAD_REQUEST);
    }
}
