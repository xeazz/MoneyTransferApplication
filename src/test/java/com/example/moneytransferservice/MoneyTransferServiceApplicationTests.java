package com.example.moneytransferservice;

import com.example.moneytransferservice.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;

import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MoneyTransferServiceApplicationTests {
    @Autowired
    TestRestTemplate restTemplate;
    @Container
    private static final GenericContainer<?> appFirst = new GenericContainer<>("myapp:latest")
            .withExposedPorts(5500);

    @BeforeAll
    public static void setUp() {
        appFirst.start();
    }

    @Test
    void transferTest() {
        Integer appPort = appFirst.getMappedPort(5500);
        final String transferLink = "http://localhost:" + appPort + "/transfer";

        try {
            URI transferUri = new URI(transferLink);
            TransferMoney transferMoney = new TransferMoney("4875130166305242", "05/26",
                    "123", "4832137002407281", null);
            HttpEntity<TransferMoney> requestTransfer = new HttpEntity<>(transferMoney);
            ResponseEntity<SuccessResponse> resultTransfer = restTemplate.postForEntity(transferUri, requestTransfer, SuccessResponse.class);
            Assertions.assertEquals(HttpStatus.OK, resultTransfer.getStatusCode());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void confirmOperationTest() {
        Integer appPort = appFirst.getMappedPort(5500);
        final String confirmOperationLink = "http://localhost:" + appPort + "/confirmOperation";
        try {
            URI confirmOperationUri = new URI(confirmOperationLink);
            TransferOperation transferOperation = new TransferOperation("1", "0000");
            HttpEntity<TransferOperation> requestConfirmOperation = new HttpEntity<>(transferOperation);
            ResponseEntity<SuccessResponse> resultConfirmOperation = restTemplate.postForEntity(confirmOperationUri, requestConfirmOperation, SuccessResponse.class);
            Assertions.assertEquals(HttpStatus.OK, resultConfirmOperation.getStatusCode());

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
