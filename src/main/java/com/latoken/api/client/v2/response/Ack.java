package com.latoken.api.client.v2.response;

import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
public final class Ack {
    private UUID id;
    private String message;
    private Status status;
    private String error;
    private Map<String, String> errors;

    public enum Status {
        SUCCESS,
        FAILURE,
    }
}
