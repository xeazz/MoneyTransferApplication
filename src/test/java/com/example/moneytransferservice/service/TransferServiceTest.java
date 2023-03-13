package com.example.moneytransferservice.service;

import com.example.moneytransferservice.exceptions.IncorrectInputDataException;
import com.example.moneytransferservice.exceptions.InternalServerErrorException;
import com.example.moneytransferservice.model.ErrorResponse;
import com.example.moneytransferservice.model.SuccessResponse;
import com.example.moneytransferservice.model.TransferMoney;
import com.example.moneytransferservice.model.TransferOperation;
import com.example.moneytransferservice.repository.TransferRepository;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

public class TransferServiceTest {
    TransferService service;
    @Mock
    TransferRepository repository;

    public TransferServiceTest() {
        MockitoAnnotations.openMocks(this);
        this.service = new TransferService(repository);
    }

    @Test
    public void transferTestFirst_False() {
        TransferMoney transferMoney = new TransferMoney("1", "1",
                "1", "1", null);
        when(repository.verifyCardFromNumber(transferMoney)).thenReturn(true);
        when(repository.verifyCardValidTill(transferMoney)).thenReturn(true);
        when(repository.verifyCardCVV(transferMoney)).thenReturn(true);
        when(repository.verifyCardToNumber(transferMoney)).thenReturn(true);
        assertThatThrownBy(() -> service.transfer(transferMoney)).isInstanceOf(InternalServerErrorException.class);
    }

    @Test
    public void transferTestSecond_False() {
        TransferMoney transferMoney = new TransferMoney("1", "1",
                "1", "1", null);
        when(repository.verifyCardFromNumber(transferMoney)).thenReturn(true);
        when(repository.verifyCardValidTill(transferMoney)).thenReturn(false);
        when(repository.verifyCardCVV(transferMoney)).thenReturn(true);
        when(repository.verifyCardToNumber(transferMoney)).thenReturn(true);
        assertThatThrownBy(() -> service.transfer(transferMoney)).isInstanceOf(IncorrectInputDataException.class);
    }

    @Test
    public void transferTestThird_False() {
        TransferMoney transferMoney = new TransferMoney("1", "1",
                "1", "1", null);
        when(repository.verifyCardFromNumber(transferMoney)).thenReturn(true);
        when(repository.verifyCardValidTill(transferMoney)).thenReturn(true);
        when(repository.verifyCardCVV(transferMoney)).thenReturn(false);
        when(repository.verifyCardToNumber(transferMoney)).thenReturn(true);
        assertThatThrownBy(() -> service.transfer(transferMoney)).isInstanceOf(IncorrectInputDataException.class);
    }

    @Test
    public void transferTestFourth_False() {
        TransferMoney transferMoney = new TransferMoney("1", "1",
                "1", "1", null);
        when(repository.verifyCardFromNumber(transferMoney)).thenReturn(true);
        when(repository.verifyCardValidTill(transferMoney)).thenReturn(true);
        when(repository.verifyCardCVV(transferMoney)).thenReturn(true);
        when(repository.verifyCardToNumber(transferMoney)).thenReturn(false);
        assertThatThrownBy(() -> service.transfer(transferMoney)).isInstanceOf(IncorrectInputDataException.class);
    }

    @Test
    public void transferTestFifth_False() {
        TransferMoney transferMoney = new TransferMoney("1", "1",
                "1", "1", null);
        when(repository.verifyCardFromNumber(transferMoney)).thenReturn(false);
        when(repository.verifyCardValidTill(transferMoney)).thenReturn(true);
        when(repository.verifyCardCVV(transferMoney)).thenReturn(true);
        when(repository.verifyCardToNumber(transferMoney)).thenReturn(true);
        assertThatThrownBy(() -> service.transfer(transferMoney)).isInstanceOf(IncorrectInputDataException.class);
    }

    @Test
    public void transferTestSuccess() {
        TransferMoney transferMoney = new TransferMoney("1", "1",
                "1", "1", null);
        when(repository.verifyCardFromNumber(transferMoney)).thenReturn(true);
        when(repository.verifyCardValidTill(transferMoney)).thenReturn(true);
        when(repository.verifyCardCVV(transferMoney)).thenReturn(true);
        when(repository.verifyCardToNumber(transferMoney)).thenReturn(true);
        when(repository.add(transferMoney)).thenReturn(true);
        when(repository.getOperationId()).thenReturn(UUID.randomUUID());
        SuccessResponse SuccessResponse = service.transfer(transferMoney);
        assertThat(SuccessResponse).isExactlyInstanceOf(SuccessResponse.class);
    }

    @Test
    public void transferTestError() {
        TransferMoney transferMoney = new TransferMoney("1", "1",
                "1", "1", null);
        when(repository.verifyCardFromNumber(transferMoney)).thenReturn(true);
        when(repository.verifyCardValidTill(transferMoney)).thenReturn(true);
        when(repository.verifyCardCVV(transferMoney)).thenReturn(true);
        when(repository.verifyCardToNumber(transferMoney)).thenReturn(true);
        when(repository.add(transferMoney)).thenReturn(true);
        when(repository.getOperationId()).thenReturn(UUID.randomUUID());
        SuccessResponse SuccessResponse = service.transfer(transferMoney);
        assertThat(SuccessResponse).isNotExactlyInstanceOf(ErrorResponse.class);
    }

    @Test
    public void confirmOperationTest_Error() {
        TransferOperation transferOperation = new TransferOperation("1111", "0009");
        when(repository.getOperationId()).thenReturn(UUID.randomUUID());
        assertThatThrownBy(() -> service.confirmOperation(transferOperation)).isInstanceOf(InternalServerErrorException.class);
    }

    @Test
    public void confirmOperationTest_Success() {
        TransferOperation transferOperation = new TransferOperation("1111", "0000");
        when(repository.getOperationId()).thenReturn(UUID.randomUUID());
        SuccessResponse SuccessResponse = service.confirmOperation(transferOperation);
        assertThat(SuccessResponse).isExactlyInstanceOf(SuccessResponse.class);
    }
}
