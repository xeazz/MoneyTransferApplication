package com.example.moneytransferservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Amount {
    private int value;
    private String currency;
}


