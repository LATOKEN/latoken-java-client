package com.latoken.api.client.v3.response;

import lombok.Data;

@Data
public final class CancelOrderRep {
    enum Status {
        CANCELLED,
        PARTIALLY_CANCELED,
        FILLED
    }

    private Status status;
}
