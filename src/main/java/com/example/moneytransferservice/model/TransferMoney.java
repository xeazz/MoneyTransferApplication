package com.example.moneytransferservice.model;

public record TransferMoney(String cardFromNumber, String cardFromValidTill, String cardFromCVV, String cardToNumber,
                            Amount amount) {

}
