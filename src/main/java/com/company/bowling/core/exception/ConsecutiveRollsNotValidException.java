package com.company.bowling.core.exception;

public class ConsecutiveRollsNotValidException extends RuntimeException {
    public ConsecutiveRollsNotValidException(String message) {
        super(message);
    }
}