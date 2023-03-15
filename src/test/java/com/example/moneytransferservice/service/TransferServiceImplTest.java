package com.example.moneytransferservice.service;

import com.example.moneytransferservice.exceptions.IncorrectInputDataException;
import com.example.moneytransferservice.exceptions.InternalServerErrorException;
import com.example.moneytransferservice.model.*;
import com.example.moneytransferservice.repository.TransferRepository;

import com.example.moneytransferservice.validation.ValidationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

public class TransferServiceImplTest {
    TransferServiceImpl service;
    @Mock
    TransferRepository repository;
    @Mock
    ValidationService validationService;

    public TransferServiceImplTest() {
        MockitoAnnotations.openMocks(this);
        this.service = new TransferServiceImpl(repository, validationService);
    }

    @Test
    public void transferTestFirst_False() {
        TransferMoney transferMoney = new TransferMoney("", "1",
                "1", "1", new Amount(100, "ru"));
        Mockito.doThrow(IncorrectInputDataException.class).when(validationService).validateTransfer(transferMoney);
        assertThatThrownBy(() -> service.transfer(transferMoney)).isInstanceOf(IncorrectInputDataException.class);
    }

    @Test
    public void transferTestSecond_False() {
        TransferMoney transferMoney = new TransferMoney("1", "1",
                "1", "1", new Amount(100, "ru"));
        Mockito.doThrow(InternalServerErrorException.class).when(validationService).validateTransfer(transferMoney);
        assertThatThrownBy(() -> service.transfer(transferMoney)).isInstanceOf(InternalServerErrorException.class);
    }

    @Test
    public void transferTestThird_False() {
        TransferMoney transferMoney = new TransferMoney("1", "1",
                "1", "1", new Amount(100, "rus"));
        when(repository.saveTransaction(service.generateOperationId(), transferMoney)).thenReturn(true);
        Mockito.doNothing().when(validationService).validateTransfer(transferMoney);
        SuccessResponse SuccessResponse = service.transfer(transferMoney);
        assertThat(SuccessResponse).isExactlyInstanceOf(SuccessResponse.class);
    }

    @Test
    public void transferTestFourth_False() {
        TransferOperation transferOperation = new TransferOperation("1111", "0000");
        service.generateOperationId();
        Mockito.doNothing().when(validationService).validateConfirmOperation(transferOperation);
        SuccessResponse SuccessResponse = service.confirmOperation(transferOperation);
        assertThat(SuccessResponse).isExactlyInstanceOf(SuccessResponse.class);
    }

}
