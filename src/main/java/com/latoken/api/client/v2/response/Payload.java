package com.latoken.api.client.v2.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data 
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Payload<T> {
    private T payload;
    private long nonce;
    private long timestamp;
}
