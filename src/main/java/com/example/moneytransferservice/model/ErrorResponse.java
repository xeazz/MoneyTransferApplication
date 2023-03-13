package com.example.moneytransferservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private final String message;
    private final UUID id;
}
