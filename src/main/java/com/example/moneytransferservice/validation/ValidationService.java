package com.example.moneytransferservice.validation;

import com.example.moneytransferservice.exception.IncorrectInputDataException;
import com.example.moneytransferservice.exception.InternalServerErrorException;
import com.example.moneytransferservice.model.TransferMoney;
import com.example.moneytransferservice.model.TransferOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@AllArgsConstructor
public class ValidationService {
    private static final String VARIFICATION_CODE = "0000";

    public boolean validateTransfer(TransferMoney transferMoney) {

        if (transferMoney == null) {
            log.error("Ошибка операции перевода!");
            throw new InternalServerErrorException("Internal server error");
        }
        if (transferMoney.cardFromNumber() == null) {
            log.error("Номер карты отправителя указан неверно!");
            throw new IncorrectInputDataException("Номер карты отправителя указан неверно!");
        }
        if (transferMoney.cardFromValidTill() == null) {
            log.error("Срок действия карты указан неверно!");
            throw new IncorrectInputDataException("Срок действия карты указан неверно!");
        }
        if (transferMoney.cardFromCVV() == null) {
            log.error("CVV указан неверно!");
            throw new IncorrectInputDataException("CVV указан неверно!");
        }
        if (transferMoney.cardToNumber() == null) {
            log.error("Номер карты получателя указан неверно!");
            throw new IncorrectInputDataException("Номер карты получателя указан неверно!");
        }
        if (transferMoney.amount() == null) {
            log.error("Ошибка операции перевода!");
            throw new InternalServerErrorException("Internal server error");
        }
        if (transferMoney.amount().value() == 0) {
            log.error("Сумма перевода указана некорректно!");
            throw new IncorrectInputDataException("Сумма перевода указана некорректно!");
        }
        return true;
    }

    public boolean validateConfirmOperation(TransferOperation transferOperation) {
        if (transferOperation == null) {
            log.error("Ошибка подтверждения транзакции!");
            throw new InternalServerErrorException("Error transfer, confirmation is null!");
        }
        if (transferOperation.operationId() == null) {
            log.error("Некорректный ID операции!");
            throw new IncorrectInputDataException("Operation ID is null!");
        }
        if (!transferOperation.code().equals(VARIFICATION_CODE)) {
            log.error("Неверный код подтверждения: {}", transferOperation.code());
            throw new InternalServerErrorException("Транзакция не была проведена!");
        }
        return true;
    }
}
