package com.latoken.api.client.v3.response;

import lombok.Data;

import java.util.UUID;

// https://ccxt.readthedocs.io/en/latest/manual.html#market-structure
@Data
public final class Market {
    /**
     * string literal for referencing within an exchange
     */
    private UUID id;

    /**
     * uppercase string literal of a pair of assets
     */
    private String symbol;

    /**
     * uppercase string, unified base currency code, 3 or more letters
     */
    private String base;

    /**
     * uppercase string, unified quote currency code, 3 or more letters
     */
    private String quote;

    /**
     * exchange-specific base currency id
     */
    private String baseId;

    /**
     * exchange-specific quote currency id
     */
    private String quoteId;

    /**
     * market status
     */
    private String active;

    /**
     * taker fee rate, 0.002 = 0.2%
     */
    private double taker;

    /**
     * maker fee rate, 0.0016 = 0.16%
     */
    private double maker;

    /**
     * whether the taker and maker fee rate is a multiplier or a fixed flat amount
     */
    private boolean percentage;

    /**
     * whether the fee depends on your trading tier (your trading volume)
     */
    private boolean tierBased;

//    private FeeSide feeSide; // does not need to be part of our api, ccxt will hardcode this

    /**
     * number of decimal digits "after the dot"
     */
    private Precision precision;


    /**
     * value limits when placing orders on this market
     */
    private Limits limits;

    @Data
    static final class Precision {
        /**
         * integer or float for TICK_SIZE roundingMode, might be missing if not supplied by the exchange
         */
        private int price;
        /**
         * integer, might be missing if not supplied by the exchange
         */
        private int amount;
        /**
         * integer, very few exchanges actually have it
         */
        private int cost;
    }

    @Data
    static final class Limits {

        @Data
        static final class Limit {
            private double min;
            private double max;
        }

        private Limit amount;
        private Limit price;
        private Limit cost;
    }
}
