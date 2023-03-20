package com.example.moneytransferservice.repository;

import com.example.moneytransferservice.model.TransferMoney;

import java.util.UUID;

public interface TransferRepository {
    UUID saveTransaction(TransferMoney transferMoney);
}
