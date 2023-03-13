package com.example.moneytransferservice.repository;

import com.example.moneytransferservice.model.CardHolder;
import com.example.moneytransferservice.model.TransferAmount;
import com.example.moneytransferservice.model.TransferMoney;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TransferRepository {

    private final Map<UUID, TransferMoney> mapOperation = new HashMap<>();
    private UUID operationId;
    private final List<CardHolder> listCard;

    public TransferRepository() {
        this.listCard = Arrays.asList(
                new CardHolder("4875130166305242", "05/26", "123", "4832137002407281"),
                new CardHolder("4875130166305234", "02/24", "111", "4832137002407290"),
                new CardHolder("4875130166305255", "23/23", "565", "4832137002407233")
        );
    }

    public boolean verifyCardFromNumber(TransferMoney transferMoney) {
        return listCard.stream().anyMatch(x -> x.getCardSender().equals(transferMoney.getCardFromNumber()));
    }

    public boolean verifyCardValidTill(TransferMoney transferMoney) {
        return listCard.stream().anyMatch(x -> x.getCardValidTill().equals(transferMoney.getCardFromValidTill()));
    }

    public boolean verifyCardCVV(TransferMoney transferMoney) {
        return listCard.stream().anyMatch(x -> x.getCardCVV().equals(transferMoney.getCardFromCVV()));
    }

    public boolean verifyCardToNumber(TransferMoney transferMoney) {
        return listCard.stream().anyMatch(x -> x.getCardAddressee().equals(transferMoney.getCardToNumber()));
    }

    public boolean add(TransferMoney transferMoney) {
        mapOperation.put(generateOperationId(), transferMoney);
        return true;
    }

    public UUID getOperationId() {
        return operationId;
    }

    UUID generateOperationId() {
        return this.operationId = UUID.randomUUID();
    }
}
