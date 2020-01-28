package com.company.bowling.command.exception;

public class MalformedPlayerInputRoll extends IllegalArgumentException {
    public MalformedPlayerInputRoll(String message) {
        super(message);
    }
}
