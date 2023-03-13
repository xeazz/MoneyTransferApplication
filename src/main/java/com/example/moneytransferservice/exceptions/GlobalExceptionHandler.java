package com.example.moneytransferservice.exceptions;

import com.example.moneytransferservice.model.ErrorResponse;
import com.example.moneytransferservice.repository.TransferRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> badRequestException(IncorrectInputDataException exception) {
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage(), UUID.randomUUID()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> internalServerErrorException(InternalServerErrorException exception) {
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage(), UUID.randomUUID()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
