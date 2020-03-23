package io.github.bhuwanupadhyay.ordersapijava8.application;

import io.github.bhuwanupadhyay.ordersapijava8.domain.DomainViolationException;
import io.github.bhuwanupadhyay.ordersapijava8.domain.EntityNotFoundException;
import io.github.bhuwanupadhyay.ordersapijava8.openapi.ErrorResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@RestControllerAdvice
public class WebExceptionTranslator {

    @ExceptionHandler(DomainViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleDomainViolationException(DomainViolationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Arrays.asList(new ErrorResource().errorId(ex.getViolator()).message(ex.getMessage())));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

}