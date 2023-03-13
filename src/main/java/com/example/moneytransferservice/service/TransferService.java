package com.example.moneytransferservice.service;

import com.example.moneytransferservice.exceptions.IncorrectInputDataException;
import com.example.moneytransferservice.exceptions.InternalServerErrorException;
import com.example.moneytransferservice.model.SuccessResponse;
import com.example.moneytransferservice.model.TransferMoney;
import com.example.moneytransferservice.model.TransferOperation;
import com.example.moneytransferservice.repository.TransferRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TransferService {
    private final TransferRepository repository;
    private static final String code = "0000";

    public TransferService(TransferRepository repository) {
        this.repository = repository;
    }

    public SuccessResponse transfer(TransferMoney transferMoney) {
        if (!repository.verifyCardFromNumber(transferMoney)) {
            log.error("Номер карты отправителя указан неверно: {}", transferMoney.getCardFromNumber());
            throw new IncorrectInputDataException("Номер карты отправителя указан неверно");
        }
        if (!repository.verifyCardValidTill(transferMoney)) {
            log.error("Срок действия карты указан неверно: {}", transferMoney.getCardFromValidTill());
            throw new IncorrectInputDataException("Срок действия карты указан неверно");
        }
        if (!repository.verifyCardCVV(transferMoney)) {
            log.error("CVV указан неверно: {}", transferMoney.getCardFromCVV());
            throw new IncorrectInputDataException("CVV указан неверно");
        }
        if (!repository.verifyCardToNumber(transferMoney)) {
            log.error("Номер карты получателя указан неверно: {}", transferMoney.getCardToNumber());
            throw new IncorrectInputDataException("Номер карты получателя указан неверно");
        }
        if (repository.add(transferMoney)) {
            log.info("Запрос на перевод денежных средств направлен. Код операции: {}", repository.getOperationId().toString());
            return new SuccessResponse(repository.getOperationId().toString());
        } else {
            log.info("Internal Server Error");
            throw new InternalServerErrorException("Internal Server Error");
        }
    }

    public SuccessResponse confirmOperation(TransferOperation operation) {
        if (!operation.getCode().equals(code)) {
            log.error("Неверный код подтверждения: {}", operation.getCode());
            throw new InternalServerErrorException("Транзакция не была проведена");
        } else {
            log.info("Транзакция успешно проведена! Код операции: {}", repository.getOperationId().toString());
            return new SuccessResponse(repository.getOperationId().toString());
        }
    }
}
