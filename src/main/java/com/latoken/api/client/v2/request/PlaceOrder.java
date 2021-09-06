package com.latoken.api.client.v2.request;

import lombok.Data;

import java.util.UUID;

@Data
public final class PlaceOrder {

    private UUID baseCurrency;
    private UUID quoteCurrency;
    private Side side;
    private Condition condition;
    private Type type;
    private String clientOrderId;
    private double price;
    private double quantity;
    private long timestamp;

    public PlaceOrder(
        UUID baseCurrency,
        UUID quoteCurrency,
        Side side,
        Condition condition,
        Type type,
        String clientOrderId,
        double price,
        double quantity,
        long timestamp
    ) {
        this.baseCurrency = baseCurrency;
        this.quoteCurrency = quoteCurrency;
        this.side = side;
        this.condition = condition;
        this.type = type;
        this.clientOrderId = clientOrderId;
        this.price = price;
        this.quantity = quantity;
        this.timestamp = timestamp;
    }

    public enum Side {
        BUY,
        SELL,
    }

    public enum Status {
        UNKNOWN,
        PLACED,
        REJECTED,
        CLOSED,
        CANCELLED,
    }

    public enum Condition {
        GOOD_TILL_CANCELLED,
        ALL_OR_NONE,
        IMMEDIATE_OR_CANCEL,
        FILL_OR_KILL,
    }

    public enum Type {
        LIMIT,
        MARKET,
    }
}
