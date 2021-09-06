package com.latoken.api.client.v2.response;

import lombok.Data;

import java.util.UUID;

@Data
public final class Currency {
    private UUID id;
    private Status status;
    private Type type;
    private String name;
    private String tag;
    private String description;
    private String logo;
    private int decimals;
    private Long created;
    private String tier;
    private AssetClass assetClass;
    private String minTransferAmount;

    public enum Type {
        CURRENCY_TYPE_UNKNOWN,
        CURRENCY_TYPE_CRYPTO,
        CURRENCY_TYPE_IEO,
        CURRENCY_TYPE_ALTERNATIVE,
        CURRENCY_TYPE_STO,
        CURRENCY_TYPE_FUTURES_CONTRACT,
        CURRENCY_TYPE_GLOBAL_MARKETS,
    }

    public enum Status {
        CURRENCY_STATUS_UNKNOWN,
        CURRENCY_STATUS_INACTIVE,
        CURRENCY_STATUS_ACTIVE,
        CURRENCY_STATUS_DISABLED,
        CURRENCY_STATUS_FROZEN,
        CURRENCY_STATUS_EXPIRED,
        CURRENCY_STATUS_DELETED,
    }

    public enum AssetClass {
        ASSET_CLASS_UNKNOWN,
        ASSET_CLASS_US_SHARE,
        ASSET_CLASS_EU_SHARE,
        ASSET_CLASS_AP_SHARE,
        ASSET_CLASS_RU_SHARE,
        ASSET_CLASS_FIAT_CCY,
        ASSET_CLASS_INDEX,
        ASSET_CLASS_COMMODITY,
        ASSET_CLASS_PRECIOUS_METAL,
        ASSET_CLASS_ETF,
    }
}
