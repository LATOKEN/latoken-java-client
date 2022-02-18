package com.latoken.api.client.v2.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.UUID;

@Data 
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Pair {
    private UUID id;
    private Status status;
    private UUID baseCurrency;
    private UUID quoteCurrency;
    private String priceTick;
    private Long priceDecimals;
    private String quantityTick;
    private Integer quantityDecimals;
    private Integer costDisplayDecimals;
    private Long created;
    private String minOrderQuantity;
    private String maxOrderCostUsd;
    private String minOrderCostUsd;
    private String externalSymbol;

    public enum Status {
        PAIR_STATUS_UNKNOWN,
        PAIR_STATUS_INACTIVE,
        PAIR_STATUS_ACTIVE,
        PAIR_STATUS_DISABLED,
        PAIR_STATUS_FROZEN,
        PAIR_STATUS_EXPIRED,
        PAIR_STATUS_DELETED,
        PAIR_STATUS_EXTERNAL,
    }
}
