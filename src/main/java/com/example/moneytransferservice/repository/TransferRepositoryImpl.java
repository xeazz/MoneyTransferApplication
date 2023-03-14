package com.example.moneytransferservice.repository;

import com.example.moneytransferservice.model.CardHolder;
import com.example.moneytransferservice.model.TransferMoney;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TransferRepositoryImpl {

    private final Map<UUID, TransferMoney> mapOperation = new HashMap<>();

    public boolean saveTransaction (UUID operationId,TransferMoney transferMoney) {
        mapOperation.put(operationId, transferMoney);
        return true;
    }



}
