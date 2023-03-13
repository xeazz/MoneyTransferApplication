package com.example.moneytransferservice.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class SuccessResponse {
    private String operationId;

    public SuccessResponse() {

    }
}

