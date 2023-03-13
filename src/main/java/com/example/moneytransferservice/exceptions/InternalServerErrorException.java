package com.example.moneytransferservice.exceptions;

import lombok.Getter;

@Getter
public class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException(String msg) {
        super(msg);
    }
}
