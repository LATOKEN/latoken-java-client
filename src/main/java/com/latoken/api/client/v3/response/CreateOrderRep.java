package com.latoken.api.client.v3.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class CreateOrderRep {
    private UUID id;
}
