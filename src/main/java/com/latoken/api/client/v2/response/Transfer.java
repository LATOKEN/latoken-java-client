package com.latoken.api.client.v2.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.UUID;

@Data 
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Transfer {

    private UUID id;
    private Status status;
    private Type type;
    private UUID fromAccount;
    private UUID toAccount;
    private String transferringFunds;
    private String usdValue;
    private String rejectReason;
    private long timestamp;
    private Direction direction;
    private Method method;
    private String recipient;
    private String sender;
    private UUID currency;

    public enum Status {
        TRANSFER_STATUS_CANCELLED,
        TRANSFER_STATUS_COMPLETED,
        TRANSFER_STATUS_PENDING,
        TRANSFER_STATUS_REJECTED,
        TRANSFER_STATUS_UNVERIFIED
    }

    public enum Type {
        TRANSFER_TYPE_DEPOSIT_SPOT,
        TRANSFER_TYPE_WITHDRAW_SPOT,
    }

    public enum Direction {
        INTERNAL,
        OUTCOME,
        INCOME,
    }

    public enum Method {
        TRANSFER_METHOD_EMAIL,
        TRANSFER_METHOD_DIRECT,
        TRANSFER_METHOD_PHONE
    }
}
