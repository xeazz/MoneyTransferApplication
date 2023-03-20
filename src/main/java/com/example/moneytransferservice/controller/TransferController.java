package com.example.moneytransferservice.controller;

import com.example.moneytransferservice.model.SuccessResponse;
import com.example.moneytransferservice.model.TransferMoney;
import com.example.moneytransferservice.model.TransferOperation;
import com.example.moneytransferservice.service.TransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "${cors_url}")
@RestController
@Slf4j
@RequiredArgsConstructor
public class TransferController {
    private final TransferService service;

    @PostMapping("/transfer")
    public ResponseEntity<SuccessResponse> transfer(@RequestBody TransferMoney transferMoney) {
        log.info("Post request on endpoint \"/transfer\": {}", transferMoney);
        return ResponseEntity.ok().body(service.transfer(transferMoney));
    }

    @PostMapping("/confirmOperation")
    public ResponseEntity<SuccessResponse> confirmOperation(@RequestBody TransferOperation transferOperation) {
        log.info("Post request on endpoint \"/confirmOperation\": {}", transferOperation);
        return ResponseEntity.ok().body(service.confirmOperation(transferOperation));
    }
}
