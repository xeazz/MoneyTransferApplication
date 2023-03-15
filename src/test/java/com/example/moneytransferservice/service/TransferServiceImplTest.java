package com.example.moneytransferservice.service;

import com.example.moneytransferservice.exceptions.IncorrectInputDataException;
import com.example.moneytransferservice.exceptions.InternalServerErrorException;
import com.example.moneytransferservice.model.*;
import com.example.moneytransferservice.repository.TransferRepositoryImpl;
import com.example.moneytransferservice.validation.ValidationService;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

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
        System.out.println("Running test");
    }

    @AfterAll
    public static void completeTransferServiceImplTest() {
        System.out.println("Test completed");
    }

    @Test
    public void transferServiceTestFirst_IncorrectInputDataException() {
        TransferMoney transferMoney = null;
        when(validationService.validateTransfer(transferMoney)).thenThrow(IncorrectInputDataException.class);
        assertThatThrownBy(() -> service.transfer(transferMoney)).isInstanceOf(IncorrectInputDataException.class);
    }

    @Test
    public void transferServiceTestFirst_InternalServerErrorException() {
        TransferMoney transferMoney = null;
        when(validationService.validateTransfer(transferMoney)).thenThrow(InternalServerErrorException.class);
        assertThatThrownBy(() -> service.transfer(transferMoney)).isInstanceOf(InternalServerErrorException.class);
    }


    @Test
    public void transferServiceTestFirst_SuccessResponse() {
        TransferOperation transferOperation = new TransferOperation("1111", "0000");
        service.generateOperationId();
        Mockito.when(validationService.validateConfirmOperation(transferOperation)).thenReturn(true);
        SuccessResponse successResponse = service.confirmOperation(transferOperation);
        assertThat(successResponse).isExactlyInstanceOf(SuccessResponse.class);
    }

    @Test
    public void transferServiceTestSecond_SuccessResponse() {
        TransferMoney transferMoney = new TransferMoney("1", "1",
                "1", "1", new Amount(10, "rur"));
        Mockito.when(validationService.validateTransfer(transferMoney)).thenReturn(true);
        when(repository.saveTransaction(service.generateOperationId(), transferMoney)).thenReturn(true);
        SuccessResponse successResponse = service.transfer(transferMoney);
        assertThat(successResponse).isExactlyInstanceOf(SuccessResponse.class);
    }
}
