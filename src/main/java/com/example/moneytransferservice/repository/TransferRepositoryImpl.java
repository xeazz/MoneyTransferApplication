package com.example.moneytransferservice.repository;

import com.example.moneytransferservice.model.TransferMoney;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class TransferRepositoryImpl implements TransferRepository {
    private final Map<UUID, TransferMoney> mapOperation = new ConcurrentHashMap<>();

    public UUID saveTransaction(TransferMoney transferMoney) {
        UUID operationId = UUID.randomUUID();
        mapOperation.put(operationId, transferMoney);
        return operationId;
    }
}
