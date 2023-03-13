package com.example.moneytransferservice.repository;

import com.example.moneytransferservice.model.TransferMoney;
import org.junit.jupiter.api.*;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TransferRepositoryTest {
    TransferRepository repository;
    TransferMoney transferMoney;

    @BeforeAll
    public static void initRepository() {
        System.out.println("Running test");
    }

    @AfterAll
    public static void completeRepository() {
        System.out.println("Test completed");
    }

    @BeforeEach
    public void initEachTestFourth() {

        repository = new TransferRepository();
        transferMoney = new TransferMoney("4875130166305241", "05/26",
                "1223", "4832137002407281", null);
        System.out.println(TransferRepository.class + " и " + TransferMoney.class + " созданы");
    }

    @AfterEach
    public void afterEachTestFourth() {
        repository = null;
        System.out.println(TransferRepository.class + " обнулен");
    }

    @Test
    public void verifyCardFromNumberTest() {
        boolean expected = false;
        boolean result = repository.verifyCardFromNumber(transferMoney);
        assertEquals(expected, result);
    }

    @Test
    public void verifyCardValidTillTest() {
        boolean expected = true;
        boolean result = repository.verifyCardValidTill(transferMoney);
        assertEquals(expected, result);
    }

    @Test
    public void verifyCardCVVTest() {
        boolean expected = false;
        boolean result = repository.verifyCardCVV(transferMoney);
        assertEquals(expected, result);
    }

    @Test
    public void verifyCardToNumberTest() {
        boolean expected = true;
        boolean result = repository.verifyCardToNumber(transferMoney);
        assertEquals(expected, result);
    }

    @Test
    public void addTest() {
        boolean expected = true;
        boolean result = repository.add(transferMoney);
        assertEquals(expected, result);
    }
}
