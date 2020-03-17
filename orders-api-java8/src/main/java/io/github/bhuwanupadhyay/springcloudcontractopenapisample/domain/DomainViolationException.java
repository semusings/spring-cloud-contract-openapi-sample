package io.github.bhuwanupadhyay.springcloudcontractopenapisample.domain;

public class DomainViolationException extends RuntimeException {

    private final String violator;
    private final String message;

    public DomainViolationException(String violator, String message) {
        this.violator = violator;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getViolator() {
        return violator;
    }
}
