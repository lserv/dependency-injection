package io.lenur.di.exception;

public class DependencyException extends RuntimeException {
    public DependencyException(String message) {
        super(message);
    }

    public DependencyException() {
    }
}
