package com.example.moneytransferservice.exception;

public class IncorrectInputDataException extends RuntimeException {
    public IncorrectInputDataException(String msg) {
        super(msg);
    }
}
