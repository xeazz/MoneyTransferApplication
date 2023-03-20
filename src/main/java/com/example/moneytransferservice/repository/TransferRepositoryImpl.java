package com.example.moneytransferservice.repository;

import com.example.moneytransferservice.model.TransferMoney;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Slf4j
public class TransferRepositoryImpl implements TransferRepository {
    private final Map<UUID, TransferMoney> mapOperation = new ConcurrentHashMap<>();

    public UUID saveTransaction(UUID operationId, TransferMoney transferMoney) {
        mapOperation.putIfAbsent(operationId, transferMoney);
        log.info(String.format("Объект: %s, добавлен с номером операции: %s", transferMoney, operationId));
        return operationId;
    }
}
