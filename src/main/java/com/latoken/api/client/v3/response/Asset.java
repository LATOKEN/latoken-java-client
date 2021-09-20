package com.latoken.api.client.v3.response;

import lombok.Data;

import java.util.UUID;


//{
//    'id':       'btc',       // string literal for referencing within an exchange
//    'code':     'BTC',       // uppercase unified string literal code the currency
//    'name':     'Bitcoin',   // string, human-readable name, if specified
//    'active':    true,       // boolean, currency status (tradeable and withdrawable)
//    'fee':       0.123,      // withdrawal fee, flat
//    'precision': 8,          // number of decimal digits "after the dot" (depends on exchange.precisionMode)
//    'limits': {              // value limits when placing orders on this market
//        'amount': {
//            'min': 0.01,     // order amount should be > min
//            'max': 1000,     // order amount should be < max
//        },
//        'withdraw': { ... }, // withdrawal limits
//    },
//    'info': { ... }, // the original unparsed currency info from the exchange
//}

// https://ccxt.readthedocs.io/en/latest/manual.html#currency-structure
@Data
public final class Asset {

    /**
     * reference within the exchange
     */
    private UUID id;

    /**
     * uppercase unified string literal code the currency
     * a.k.a. symbol
     */
    private String code;

    /**
     * human-readable name
     */
    private String name;

    /**
     * boolean, currency status (tradeable and withdrawable)
     */
    private boolean active;

    /**
     * withdrawal fee, flat
     */
    private double fee;

    /**
     * number of decimal digits "after the dot" (depends on exchange.precisionMode)
     */
    private int precision;

    /**
     * value limits when placing orders on this market
     */
    private Limits limits;


    @Data
    static final class Limits {

        @Data
        static final class Limit {
            private double min;
            private double max;
        }

        private Limits.Limit amount;
        private Limits.Limit withdraw;
    }
}
