package com.latoken.api.client.v2.response;

import lombok.Data;

@Data
public class CurrencyLimit {
    private String tag;
    private double min_amount;
    private double fee;
}
