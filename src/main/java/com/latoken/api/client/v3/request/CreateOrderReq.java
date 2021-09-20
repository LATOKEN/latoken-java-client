package com.latoken.api.client.v3.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class CreateOrderReq {
    @NotNull
    private String base;
    @NotNull
    private String quote;
    @NotNull
    private Type type;
    @NotNull
    private Side side;
    @NotNull
    private Double price;
    @NotNull
    private Double amount;

    private TimeInForce timeInForce;

    public enum Side {
        BUY,
        SELL,
    }

    public enum TimeInForce {
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
