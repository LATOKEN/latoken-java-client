package com.latoken.api.client.v2.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data 
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Binding {
    private UUID id;
    private UUID currencyProvider;
    private Status status;
    private Type type;
    private UUID currency;
    private double minAmount;
    private double fee;
    private Double percentFee;
    private UUID feeCurrency;
    private String title;
    private int confirmationBlocks;
    private boolean memoSupported;
    private int decimals;
    private Map<String, String> config;
    private String providerName;

    public enum Status {
        CURRENCY_BINDING_STATUS_ACTIVE,
        CURRENCY_BINDING_STATUS_DISABLED,
        CURRENCY_BINDING_STATUS_TESTING,
        CURRENCY_BINDING_STATUS_READONLY,
        CURRENCY_BINDING_STATUS_MAINTENANCE,
        CURRENCY_BINDING_STATUS_FROZEN,
        CURRENCY_BINDING_STATUS_DELETED,
    }

    public enum Type {
        CURRENCY_BINDING_TYPE_INPUT,
        CURRENCY_BINDING_TYPE_OUTPUT,
    }
}
