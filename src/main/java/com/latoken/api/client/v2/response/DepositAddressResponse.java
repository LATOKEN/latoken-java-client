package com.latoken.api.client.v2.response;

import lombok.Data;

import java.util.Map;

@Data
public final class DepositAddressResponse {

    private Account depositAccount;
    private String message;
    private Error.ErrorCode error;
    private Error.Status status;
    private Map<String, String> errors;

    @Data
    public static final class Account {
        private String address;
        private String memo;
    }
}
