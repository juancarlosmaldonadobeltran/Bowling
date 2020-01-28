package com.company.bowling.core.exception;

public class IncompleteGameException extends RuntimeException {
    public IncompleteGameException(String message) {
        super(message);
    }
}
