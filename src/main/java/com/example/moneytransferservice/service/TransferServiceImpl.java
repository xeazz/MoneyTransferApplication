package com.example.moneytransferservice.service;

import com.example.moneytransferservice.exception.InternalServerErrorException;
import com.example.moneytransferservice.model.SuccessResponse;
import com.example.moneytransferservice.model.TransferMoney;
import com.example.moneytransferservice.model.TransferOperation;
import com.example.moneytransferservice.repository.TransferRepository;
import com.example.moneytransferservice.validation.ValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class TransferServiceImpl implements TransferService {
    private final TransferRepository repository;
    private final ValidationService validationService;

    public TransferServiceImpl(TransferRepository repository, ValidationService validationService) {
        this.repository = repository;
        this.validationService = validationService;
    }

    public SuccessResponse transfer(TransferMoney transferMoney) {
        if (!validationService.validateTransfer(transferMoney)) {
            log.info("Ошибка добавления транзакции!");
            throw new InternalServerErrorException("Internal Server Error");
        }
        UUID operationId = repository.saveTransaction(transferMoney);
        log.info("Запрос на перевод денежных средств направлен. Код операции: {}", operationId.toString());
        return new SuccessResponse(operationId.toString());
    }

    public SuccessResponse confirmOperation(TransferOperation operation) {
        if (!validationService.validateConfirmOperation(operation)) {
            log.info("Ошибка транзакции!");
            throw new InternalServerErrorException("Internal Server Error");
        }
        log.info("Транзакция успешно проведена! Код операции: {}", operation.operationId());
        return new SuccessResponse(operation.operationId());
    }
}
