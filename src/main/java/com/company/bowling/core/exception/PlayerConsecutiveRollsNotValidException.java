package com.company.bowling.core.exception;

public class PlayerConsecutiveRollsNotValidException extends RuntimeException {
    public PlayerConsecutiveRollsNotValidException(String message, Throwable cause) {
        super(message, cause);
    }
}