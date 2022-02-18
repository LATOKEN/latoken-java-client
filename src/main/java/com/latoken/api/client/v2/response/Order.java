package com.latoken.api.client.v2.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Order {

    private UUID id;
    private UUID user;
    private String changeType;
    private Status status;
    private Side side;
    private Condition condition;
    private Type type;
    private UUID baseCurrency;
    private UUID quoteCurrency;
    private String clientOrderId;
    private double price;
    private double quantity;
    private double cost;
    private double filled;
    private UUID trader;
    private String deltaFilled;
    private long timestamp;
    private String rejectError;
    private String rejectComment;
    private String creator;
    private String creatorId;

    public enum Side {
        ORDER_SIDE_UNKNOWN,
        ORDER_SIDE_BUY,
        ORDER_SIDE_SELL,
    }

    public enum Status {
        ORDER_STATUS_UNKNOWN,
        ORDER_STATUS_PLACED,
        ORDER_STATUS_REJECTED,
        ORDER_STATUS_CLOSED,
        ORDER_STATUS_CANCELLED,
    }

    public enum Condition {
        ORDER_CONDITION_UNKNOWN,
        ORDER_CONDITION_GOOD_TILL_CANCELLED,
        ORDER_CONDITION_ALL_OR_NONE,
        ORDER_CONDITION_IMMEDIATE_OR_CANCEL,
        ORDER_CONDITION_FILL_OR_KILL,
    }

    public enum Type {
        ORDER_TYPE_UNKNOWN,
        ORDER_TYPE_LIMIT,
        ORDER_TYPE_MARKET,
    }
}
