package com.example.moneytransferservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CardHolder {
    private final String cardSender;
    private final String cardValidTill;
    private final String cardCVV;
    private final String cardAddressee;
}
