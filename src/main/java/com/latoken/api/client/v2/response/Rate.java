package com.latoken.api.client.v2.response;

import lombok.Data;

@Data
public final class Rate {
    private String symbol;
    private double rate;
}
