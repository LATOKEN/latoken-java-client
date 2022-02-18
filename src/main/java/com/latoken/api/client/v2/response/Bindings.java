package com.latoken.api.client.v2.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data 
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Bindings {
    private List<UUID> inputs;
    private List<UUID> outputs;
}
