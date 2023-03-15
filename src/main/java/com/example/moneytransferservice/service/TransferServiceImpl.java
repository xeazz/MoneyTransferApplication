package com.example.moneytransferservice.service;

import com.example.moneytransferservice.exceptions.InternalServerErrorException;
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
    private UUID operationId;

    public TransferServiceImpl(TransferRepository repository, ValidationService validationService) {
        this.repository = repository;
        this.validationService = validationService;
    }

    public SuccessResponse transfer(TransferMoney transferMoney) {
        if (validationService.validateTransfer(transferMoney)) {
            repository.saveTransaction(generateOperationId(), transferMoney);
            log.info("Запрос на перевод денежных средств направлен. Код операции: {}", getOperationId().toString());
            return new SuccessResponse(getOperationId().toString());
        } else {
            log.info("Ошибка добавления транзакции!");
            throw new InternalServerErrorException("Internal Server Error");
        }
    }


    public SuccessResponse confirmOperation(TransferOperation operation) {
        operation.setOperationId(getOperationId().toString());
        if (validationService.validateConfirmOperation(operation)) {
            log.info("Транзакция успешно проведена! Код операции: {}", getOperationId().toString());
            return new SuccessResponse(getOperationId().toString());
        } else {
            log.info("Ошибка транзакции!");
            throw new InternalServerErrorException("Internal Server Error");
        }
    }

    UUID generateOperationId() {
        return this.operationId = UUID.randomUUID();
    }

    public UUID getOperationId() {
        return operationId;
    }
}