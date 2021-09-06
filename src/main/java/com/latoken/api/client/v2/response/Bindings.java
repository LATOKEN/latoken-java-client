package com.latoken.api.client.v2.response;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public final class Bindings {
    private List<UUID> inputs;
    private List<UUID> outputs;
}
