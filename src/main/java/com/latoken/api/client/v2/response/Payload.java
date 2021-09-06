package com.latoken.api.client.v2.response;

import lombok.Data;

@Data
public final class Payload<T> {
    private T payload;
    private long nonce;
    private long timestamp;
}
