package com.example.moneytransferservice.model;

import java.util.UUID;

public record ErrorResponse(String message, UUID id) {
}
