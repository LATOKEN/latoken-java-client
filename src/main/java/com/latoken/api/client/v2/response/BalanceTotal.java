package com.latoken.api.client.v2.response;

import lombok.Data;

import java.util.UUID;

@Data
public final class BalanceTotal {
    private long timestamp;
    private UUID currency;
    private double available;
    private double blocked;
}
