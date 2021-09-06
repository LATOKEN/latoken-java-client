package com.latoken.api.client.v2.response;

import lombok.Data;

@Data
public final class FeeScheme {
    private double makerFee;
    private double takerFee;
    private Type type;
    private Take take;

    public enum Type {
        FEE_SCHEME_TYPE_FREE,
        FEE_SCHEME_TYPE_PERCENT_QUOTE,
        FEE_SCHEME_TYPE_FIXED_QUOTE,
    }

    public enum Take {
        FEE_SCHEME_TAKE_PROPORTION,
        FEE_SCHEME_TAKE_IMMEDIATE,
    }
}
