package com.latoken.api.client.v2.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.UUID;

@Data 
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Ticker {
    private String symbol;
    private UUID baseCurrency;
    private UUID quoteCurrency;
    private double volume24h;
    private double volume7d;
    private double change24h;
    private double change7d;
    private double lastPrice;
}
