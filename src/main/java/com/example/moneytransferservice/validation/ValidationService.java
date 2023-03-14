package com.example.moneytransferservice.validation;

import com.example.moneytransferservice.exceptions.IncorrectInputDataException;
import com.example.moneytransferservice.exceptions.InternalServerErrorException;
import com.example.moneytransferservice.model.TransferMoney;
import com.example.moneytransferservice.model.TransferOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Getter
@AllArgsConstructor
public class ValidationService {
    private static final String CODE = "0000";

    public void validateTransfer(TransferMoney transferMoney) {
//        if (transferMoney == null) {
//            log.error("Ошибка операции перевода!");
//            throw new InternalServerErrorException("Internal server error");
//        }
        if (transferMoney.getCardFromNumber() == null) {
            log.error("Номер карты отправителя указан неверно!");
            throw new IncorrectInputDataException("Номер карты отправителя указан неверно!");
        }
        if (transferMoney.getCardFromValidTill() == null) {
            log.error("Срок действия карты указан неверно!");
            throw new IncorrectInputDataException("Срок действия карты указан неверно!");
        }
        if (transferMoney.getCardFromCVV() == null) {
            log.error("CVV указан неверно!");
            throw new IncorrectInputDataException("CVV указан неверно!");
        }
        if (transferMoney.getCardToNumber() == null) {
            log.error("Номер карты получателя указан неверно!");
            throw new IncorrectInputDataException("Номер карты получателя указан неверно!");
        }
//        if (transferMoney.getTransferAmount() == null) {
//            log.error("Ошибка операции перевода!");
//            throw new InternalServerErrorException("Internal server error");
//        }
        if (transferMoney.getAmount().getValue() == 0) {
            log.error("Сумма перевода указана некорректно!");
            throw new IncorrectInputDataException("Номер карты получателя указан неверно!");
        }
    }

    public void validateConfirmOperation(TransferOperation transferOperation) {
        if (transferOperation == null) {
            log.error("Ошибка подтверждения транзакции!");
            throw new InternalServerErrorException("Error transfer, confirmation is null!");
        }
        if (transferOperation.getOperationId() == null) {
            log.error("Некорректный ID операции!");
            throw new IncorrectInputDataException("Operation ID is null!");
        }
        if (!transferOperation.getCode().equals(CODE)) {
            log.error("Неверный код подтверждения: {}", transferOperation.getCode());
            throw new InternalServerErrorException("Транзакция не была проведена!");
        }
    }
}
