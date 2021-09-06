package com.latoken.api.client.v2.response;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CurrencyLimit {
    private UUID id;
    private String tag;
    private List<Binding> bindings;
}
