package com.latoken.api.client.v2.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.UUID;

@Data 
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Trade {
    private UUID id;
    private Direction direction;
    private UUID baseCurrency;
    private UUID quoteCurrency;
    private double price;
    private double quantity;
    private double cost;
    private long timestamp;

    private String order;
    private Boolean makerBuyer;

    public enum Direction {
        TRADE_DIRECTION_BUY,
        TRADE_DIRECTION_SELL
    }

}
