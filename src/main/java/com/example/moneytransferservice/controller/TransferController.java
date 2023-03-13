package com.example.moneytransferservice.controller;

import com.example.moneytransferservice.model.SuccessResponse;
import com.example.moneytransferservice.model.TransferMoney;
import com.example.moneytransferservice.model.TransferOperation;
import com.example.moneytransferservice.service.TransferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class TransferController {
    TransferService service;

    public TransferController(TransferService service) {
        this.service = service;
    }


    @CrossOrigin
    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse transfer(@RequestBody TransferMoney transferMoney) {
        log.info("Post request on endpoint \"/transfer\": {}", transferMoney);
        return service.transfer(transferMoney);
    }

    @CrossOrigin
    @PostMapping("/confirmOperation")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse confirmOperation(@RequestBody TransferOperation transferOperation) {
        log.info("Post request on endpoint \"/confirmOperation\": {}", transferOperation);
        return service.confirmOperation(transferOperation);
    }
}
