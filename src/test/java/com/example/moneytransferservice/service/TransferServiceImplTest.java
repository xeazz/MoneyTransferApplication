package com.example.moneytransferservice.service;

import com.example.moneytransferservice.exception.IncorrectInputDataException;
import com.example.moneytransferservice.exception.InternalServerErrorException;
import com.example.moneytransferservice.model.*;
import com.example.moneytransferservice.repository.TransferRepositoryImpl;
import com.example.moneytransferservice.validation.ValidationService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@Slf4j
public class TransferServiceImplTest {
    TransferServiceImpl service;
    @Mock
    TransferRepositoryImpl repository;
    @Mock
    ValidationService validationService;

    public TransferServiceImplTest() {
        MockitoAnnotations.openMocks(this);
        this.service = new TransferServiceImpl(repository, validationService);
    }

    @BeforeAll
    public static void initTransferServiceImplTest() {
        log.info("Running test");
    }

    @AfterAll
    public static void completeTransferServiceImplTest() {
        log.info("Test completed");
    }

    @Test
    @DisplayName("Checking for an throw IncorrectInputDataException when calling the transfer method")
    public void checkingThrowIncorrectInputDataException() {
        TransferMoney transferMoney = null;
        when(validationService.validateTransfer(transferMoney)).thenThrow(IncorrectInputDataException.class);
        assertThatThrownBy(() -> service.transfer(transferMoney)).isInstanceOf(IncorrectInputDataException.class);
    }

    @Test
    @DisplayName("Checking for an throw InternalServerErrorException when calling the transfer method")
    public void checkingThrowInternalServerErrorException() {
        TransferMoney transferMoney = null;
        when(validationService.validateTransfer(transferMoney)).thenThrow(InternalServerErrorException.class);
        assertThatThrownBy(() -> service.transfer(transferMoney)).isInstanceOf(InternalServerErrorException.class);
    }

    @Test
    @DisplayName("Checking the success of confirm operation by calling the confirmOperation method")
    public void checkingTheSuccessOfConfirmOperation() {
        TransferOperation transferOperation = new TransferOperation("1111", "0000");
        Mockito.when(validationService.validateConfirmOperation(transferOperation)).thenReturn(true);
        SuccessResponse successResponse = service.confirmOperation(transferOperation);
        assertNotEquals(UUID.randomUUID().toString(), successResponse.operationId());
    }

    @Test
    @DisplayName("Checking operation ID for null")
    public void checkingOperationIdForNull() {
        TransferOperation transferOperation = new TransferOperation("1111", "0000");
        Mockito.when(validationService.validateConfirmOperation(transferOperation)).thenReturn(true);
        SuccessResponse successResponse = service.confirmOperation(transferOperation);
        assertNotNull(successResponse.operationId());
    }

    @Test
    @DisplayName("Checking the success of transactions by calling the transfer method")
    public void checkingTheSuccessOfTransactions() {
        TransferMoney transferMoney = new TransferMoney("1", "1",
                "1", "1", new Amount(10, "rur"));
        UUID operationId = UUID.fromString("3e933b70-59e3-4b73-a386-f32a9a1cace3");
        Mockito.when(validationService.validateTransfer(transferMoney)).thenReturn(true);
        Mockito.when(repository.saveTransaction(Mockito.any(), eq(transferMoney))).thenReturn(operationId);
        SuccessResponse successResponse = service.transfer(transferMoney);
        assertNotNull(successResponse.operationId());
    }
}
