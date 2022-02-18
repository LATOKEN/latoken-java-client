package com.latoken.api.client.v2.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.UUID;

@Data 
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Balance {
    private UUID id;
    private Status status;
    private Type type;
    private long timestamp;
    private UUID currency;
    private double available;
    private double blocked;
    private UUID user;

    public enum Status {
        ACCOUNT_STATUS_UNKNOWN,
        ACCOUNT_STATUS_ACTIVE,
        ACCOUNT_STATUS_DISABLED,
        ACCOUNT_STATUS_FROZEN,
        ACCOUNT_STATUS_DELETED,
    }

    public enum Type {
        ACCOUNT_TYPE_UNKNOWN,
        ACCOUNT_TYPE_WALLET,
        ACCOUNT_TYPE_SPOT,
        ACCOUNT_TYPE_CROWDSALE,
        ACCOUNT_TYPE_REFERRAL,
        ACCOUNT_TYPE_FUTURES,
        ACCOUNT_TYPE_AIRDROP,
        ACCOUNT_TYPE_BONUS,
        ACCOUNT_TYPE_STAKING,
        ACCOUNT_TYPE_INTERNAL_STAKING,
        ACCOUNT_TYPE_GLOBAL_MARKETS,
        ACCOUNT_TYPE_DISTRIBUTION,
        ACCOUNT_TYPE_TOTAL,
    }
}
