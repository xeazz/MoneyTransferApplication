package com.example.moneytransferservice.repository;

import com.example.moneytransferservice.model.Amount;
import com.example.moneytransferservice.model.TransferMoney;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class TransferRepositoryImplTest {
    TransferRepositoryImpl repository;

    @BeforeAll
    public static void initTransferRepository() {
        log.info("Running test");
    }

    @AfterAll
    public static void completeTransferRepository() {
        log.info("Test completed");
    }

    @BeforeEach
    public void initEachTestFourth() {
        repository = new TransferRepositoryImpl();
        log.info(String.format("%s создан", TransferRepositoryImpl.class));
    }

    @AfterEach
    public void afterEachTestFourth() {
        repository = null;
        log.info(String.format("%s обнулен", TransferRepositoryImpl.class));
    }

    @Test
    @DisplayName("Checking the saveTransactions method")
    public void checkingTheSuccessOfTransactionsMethod() {
        TransferMoney transferMoney = new TransferMoney("1", "1", "1", "1", new Amount(100, "RUR"));
        UUID operationId = repository.saveTransaction(UUID.fromString("3e933b70-59e3-4b73-a386-f32a9a1cace3"), transferMoney);
        assertEquals(UUID.fromString("3e933b70-59e3-4b73-a386-f32a9a1cace3"), operationId);
    }

    @Test
    @DisplayName("Checking the saveTransactions method for null")
    public void checkingTheSuccessOfTransactionsMethodForNull() {
        TransferMoney transferMoney = new TransferMoney("1", "1", "1", "1", new Amount(100, "RUR"));
        UUID operationId = repository.saveTransaction(UUID.fromString("3e933b70-59e3-4b73-a386-f32a9a1cace3"), transferMoney);
        assertNotNull(operationId);
    }
}
