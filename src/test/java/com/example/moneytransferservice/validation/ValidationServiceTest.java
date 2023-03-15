package com.example.moneytransferservice.validation;

import com.example.moneytransferservice.exceptions.IncorrectInputDataException;
import com.example.moneytransferservice.exceptions.InternalServerErrorException;
import com.example.moneytransferservice.model.Amount;
import com.example.moneytransferservice.model.TransferMoney;
import com.example.moneytransferservice.model.TransferOperation;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

public class ValidationServiceTest {
    ValidationService validationService;

    @BeforeAll
    public static void initValidationService() {
        System.out.println("Running test");
    }

    @AfterAll
    public static void completeValidationService() {
        System.out.println("Test completed");
    }

    @BeforeEach
    public void initEachTestFourth() {

        validationService = new ValidationService();
        System.out.println(ValidationService.class + " создан");
    }

    @AfterEach
    public void afterEachTestFourth() {
        validationService = null;
        System.out.println(ValidationService.class + " обнулен");
    }

    @Test
    public void transferTestFirst_True() {
        TransferMoney transferMoney = new TransferMoney("1", "1",
                "1", "1", new Amount(100, "RUR"));
        assertTrue(validationService.validateTransfer(transferMoney));
    }

    @Test
    public void validateTransferTestSecond_True() {
        TransferMoney transferMoney = new TransferMoney(null, "1",
                "1", "1", new Amount(100, "RUR"));
        assertThatThrownBy(() -> validationService.validateTransfer(transferMoney))
                .isInstanceOf(IncorrectInputDataException.class)
                .hasMessage("Номер карты отправителя указан неверно!");
    }

    @Test
    public void validateTransferTestThird_IncorrectInputDataException() {
        TransferMoney transferMoney = new TransferMoney("1", null,
                "1", "1", new Amount(100, "RUR"));
        assertThatThrownBy(() -> validationService.validateTransfer(transferMoney))
                .isInstanceOf(IncorrectInputDataException.class)
                .hasMessage("Срок действия карты указан неверно!");
    }

    @Test
    public void validateTransferTestFourth_IncorrectInputDataException() {
        TransferMoney transferMoney = new TransferMoney("1", "1",
                null, "1", new Amount(100, "RUR"));
        assertThatThrownBy(() -> validationService.validateTransfer(transferMoney))
                .isInstanceOf(IncorrectInputDataException.class)
                .hasMessage("CVV указан неверно!");
    }

    @Test
    public void validateTransferTestFifth_IncorrectInputDataException() {
        TransferMoney transferMoney = new TransferMoney("1", "1",
                null, "1", new Amount(100, "RUR"));
        assertThatThrownBy(() -> validationService.validateTransfer(transferMoney))
                .isInstanceOf(IncorrectInputDataException.class)
                .hasMessage("CVV указан неверно!");
    }

    @Test
    public void validateTransferTestSeventh_IncorrectInputDataException() {
        TransferMoney transferMoney = new TransferMoney("1", "1",
                "1", "1", new Amount(0, "RUR"));
        assertThatThrownBy(() -> validationService.validateTransfer(transferMoney))
                .isInstanceOf(IncorrectInputDataException.class)
                .hasMessage("Сумма перевода указана некорректно!");
    }

    @Test
    public void validateTransferTestFirst_InternalServerErrorException() {
        TransferMoney transferMoney = new TransferMoney("1", "1",
                "1", "1", null);
        assertThatThrownBy(() -> validationService.validateTransfer(transferMoney))
                .isInstanceOf(InternalServerErrorException.class)
                .hasMessage("Internal server error");
    }

    @Test
    public void validateTransferTestSecond_InternalServerErrorException() {
        TransferMoney transferMoney = null;
        assertThatThrownBy(() -> validationService.validateTransfer(transferMoney))
                .isInstanceOf(InternalServerErrorException.class)
                .hasMessage("Internal server error");
    }

    @Test
    public void validateConfirmOperationTestFirst_True() {
        TransferOperation transferOperation = new TransferOperation("111", "0000");
        assertTrue(validationService.validateConfirmOperation(transferOperation));
    }

    @Test
    public void validateConfirmOperationTestFirst_IncorrectInputDataException() {
        TransferOperation transferOperation = new TransferOperation(null, "0000");
        assertThatThrownBy(() -> validationService.validateConfirmOperation(transferOperation))
                .isInstanceOf(IncorrectInputDataException.class)
                .hasMessage("Operation ID is null!");
    }

    @Test
    public void validateConfirmOperationTestFirst_InternalServerErrorException() {
        TransferOperation transferOperation = null;
        assertThatThrownBy(() -> validationService.validateConfirmOperation(transferOperation))
                .isInstanceOf(InternalServerErrorException.class)
                .hasMessage("Error transfer, confirmation is null!");
    }

    @Test
    public void validateConfirmOperationTestSecond_InternalServerErrorException() {
        TransferOperation transferOperation = new TransferOperation("111", "0001");
        assertThatThrownBy(() -> validationService.validateConfirmOperation(transferOperation))
                .isInstanceOf(InternalServerErrorException.class)
                .hasMessage("Транзакция не была проведена!");
    }

}
