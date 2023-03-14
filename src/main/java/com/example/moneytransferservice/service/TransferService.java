package com.example.moneytransferservice.service;

import com.example.moneytransferservice.model.SuccessResponse;
import com.example.moneytransferservice.model.TransferMoney;
import com.example.moneytransferservice.model.TransferOperation;

public interface TransferService {
    SuccessResponse transfer(TransferMoney transferMoney);
    SuccessResponse confirmOperation(TransferOperation operation);
}
