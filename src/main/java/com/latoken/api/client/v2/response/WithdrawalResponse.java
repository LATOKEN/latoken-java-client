package com.latoken.api.client.v2.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.UUID;

@Data 
@JsonIgnoreProperties(ignoreUnknown = true)
public final class WithdrawalResponse {
    private UUID withdrawalId;
    private boolean codeRequired;
    private Transaction transaction;
}
