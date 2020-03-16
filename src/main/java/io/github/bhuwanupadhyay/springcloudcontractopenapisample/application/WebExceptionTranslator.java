package io.github.bhuwanupadhyay.springcloudcontractopenapisample.application;

import io.github.bhuwanupadhyay.springcloudcontractopenapisample.application.OrderHandler.ErrorResource;
import io.github.bhuwanupadhyay.springcloudcontractopenapisample.domain.DomainViolationException;
import io.github.bhuwanupadhyay.springcloudcontractopenapisample.domain.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WebExceptionTranslator {

    @ExceptionHandler(DomainViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleDomainViolationException(DomainViolationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResource(ex.getViolator(), ex.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

}