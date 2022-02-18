package com.latoken.api.client.v2.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data 
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyLimit {
    private UUID id;
    private String tag;
    private List<Binding> bindings;
}
