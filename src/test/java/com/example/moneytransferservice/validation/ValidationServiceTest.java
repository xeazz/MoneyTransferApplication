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
    @DisplayName("If the money transfer request is successful, it should return true")
    public void allCardDetailsAreEnteredCorrectly() {
        TransferMoney transferMoney = new TransferMoney("1", "1",
                "1", "1", new Amount(100, "RUR"));
        assertTrue(validationService.validateTransfer(transferMoney));
    }

    @Test
    @DisplayName("An empty sender card should throw IncorrectInputDataException")
    public void senderCardIsNull() {
        TransferMoney transferMoney = new TransferMoney(null, "1",
                "1", "1", new Amount(100, "RUR"));
        assertThatThrownBy(() -> validationService.validateTransfer(transferMoney))
                .isInstanceOf(IncorrectInputDataException.class)
                .hasMessage("Номер карты отправителя указан неверно!");
    }

    @Test
    @DisplayName("An empty expiration date should throw IncorrectInputDataException")
    public void validTillIsNull() {
        TransferMoney transferMoney = new TransferMoney("1", null,
                "1", "1", new Amount(100, "RUR"));
        assertThatThrownBy(() -> validationService.validateTransfer(transferMoney))
                .isInstanceOf(IncorrectInputDataException.class)
                .hasMessage("Срок действия карты указан неверно!");
    }

    @Test
    @DisplayName("An empty CVV should throw IncorrectInputDataException")
    public void cardCvvIsNull() {
        TransferMoney transferMoney = new TransferMoney("1", "1",
                null, "1", new Amount(100, "RUR"));
        assertThatThrownBy(() -> validationService.validateTransfer(transferMoney))
                .isInstanceOf(IncorrectInputDataException.class)
                .hasMessage("CVV указан неверно!");
    }

    @Test
    @DisplayName("An empty recipient card should throw IncorrectInputDataException")
    public void recipientCardIsNull() {
        TransferMoney transferMoney = new TransferMoney("1", "1",
                "1", null, new Amount(100, "RUR"));
        assertThatThrownBy(() -> validationService.validateTransfer(transferMoney))
                .isInstanceOf(IncorrectInputDataException.class)
                .hasMessage("Номер карты получателя указан неверно!");
    }

    @Test
    @DisplayName("If transfer amount value = 0, then should throw IncorrectInputDataException")
    public void transferAmountValueIsIncorrect() {
        TransferMoney transferMoney = new TransferMoney("1", "1",
                "1", "1", new Amount(0, "RUR"));
        assertThatThrownBy(() -> validationService.validateTransfer(transferMoney))
                .isInstanceOf(IncorrectInputDataException.class)
                .hasMessage("Сумма перевода указана некорректно!");
    }

    @Test
    @DisplayName("If request on transfer money = null, then should throw InternalServerErrorException")
    public void transferMoneyIsNull() {
        TransferMoney transferMoney = null;
        assertThatThrownBy(() -> validationService.validateTransfer(transferMoney))
                .isInstanceOf(InternalServerErrorException.class)
                .hasMessage("Internal server error");
    }

    @Test
    @DisplayName("If amount transfer = null, then should throw InternalServerErrorException")
    public void amountIsNull() {
        TransferMoney transferMoney = new TransferMoney("1", "1",
                "1", "1", null);
        assertThatThrownBy(() -> validationService.validateTransfer(transferMoney))
                .isInstanceOf(InternalServerErrorException.class)
                .hasMessage("Internal server error");
    }


    @Test
    @DisplayName("If the transaction was successful, it should return true")
    public void successfulTransactionConfirmation() {
        TransferOperation transferOperation = new TransferOperation("111", "0000");
        assertTrue(validationService.validateConfirmOperation(transferOperation));
    }

    @Test
    @DisplayName("An empty operation id should throw IncorrectInputDataException")
    public void operationIdIsNull() {
        TransferOperation transferOperation = new TransferOperation(null, "0000");
        assertThatThrownBy(() -> validationService.validateConfirmOperation(transferOperation))
                .isInstanceOf(IncorrectInputDataException.class)
                .hasMessage("Operation ID is null!");
    }

    @Test
    @DisplayName("An empty o transfer operation confirm should throw InternalServerErrorException")
    public void transferOperationIsNull() {
        TransferOperation transferOperation = null;
        assertThatThrownBy(() -> validationService.validateConfirmOperation(transferOperation))
                .isInstanceOf(InternalServerErrorException.class)
                .hasMessage("Error transfer, confirmation is null!");
    }

    @Test
    @DisplayName("An incorrect validation code should throw InternalServerErrorException")
    public void invalidConfirmationCode() {
        TransferOperation transferOperation = new TransferOperation("111", "0001");
        assertThatThrownBy(() -> validationService.validateConfirmOperation(transferOperation))
                .isInstanceOf(InternalServerErrorException.class)
                .hasMessage("Транзакция не была проведена!");
    }

}
