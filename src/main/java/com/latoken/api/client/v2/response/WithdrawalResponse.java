package com.latoken.api.client.v2.response;

import lombok.Data;

import java.util.UUID;

@Data
public final class WithdrawalResponse {
    private UUID withdrawalId;
    private boolean codeRequired;
    private Transaction transaction;
}
