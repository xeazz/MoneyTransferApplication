package com.example.moneytransferservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TransferOperation {
    private final String operationId;
    private final String code;

    @Override
    public String toString() {
        return "TransferOperation{" +
                "operationId='" + operationId + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
