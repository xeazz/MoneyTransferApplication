package com.example.moneytransferservice.integration;

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


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MoneyTransferServiceImplApplicationTests {
    @Autowired
    private final TestRestTemplate restTemplate = null;
    private final Integer appPort = appFirst.getMappedPort(5500);
    private final String mainLink = "http://localhost:" + appPort;

    @Container
    private static final GenericContainer<?> appFirst = new GenericContainer<>("myapp:latest")
            .withExposedPorts(5500);

    @BeforeAll
    public static void setUp() {
        appFirst.start();
    }

    @Test
    void transferTest() {
        final String transferLink = mainLink + "/transfer";
        try {
            URI transferUri = new URI(transferLink);
            TransferMoney transferMoney = new TransferMoney("4875130166305242", "05/26",
                    "123", "4832137002407281", new Amount(10, "rus"));
            HttpEntity<TransferMoney> requestTransfer = new HttpEntity<>(transferMoney);
            ResponseEntity<SuccessResponse> resultTransfer = restTemplate.postForEntity(transferUri, requestTransfer, SuccessResponse.class);
            Assertions.assertEquals(HttpStatus.OK, resultTransfer.getStatusCode());
            Assertions.assertNotEquals(null, resultTransfer.getBody().operationId());

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void confirmOperationTest() {
        final String confirmOperationLink = mainLink + "/confirmOperation";
        try {
            URI confirmOperationUri = new URI(confirmOperationLink);
            TransferOperation transferOperation = new TransferOperation("1", "0000");
            HttpEntity<TransferOperation> requestConfirmOperation = new HttpEntity<>(transferOperation);
            ResponseEntity<SuccessResponse> resultConfirmOperation = restTemplate.postForEntity(confirmOperationUri, requestConfirmOperation, SuccessResponse.class);
            Assertions.assertEquals(HttpStatus.OK, resultConfirmOperation.getStatusCode());
            Assertions.assertNotEquals(null, resultConfirmOperation.getBody().operationId());

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
