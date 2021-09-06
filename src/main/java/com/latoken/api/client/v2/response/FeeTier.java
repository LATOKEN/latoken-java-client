package com.latoken.api.client.v2.response;

import lombok.Data;

@Data
public final class FeeTier {
    private double makerFee;
    private double takerFee;
    private long volume;
}
