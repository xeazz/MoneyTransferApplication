package com.example.moneytransferservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TransferAmount {
    private final Long value;
    private final String currency;
}


