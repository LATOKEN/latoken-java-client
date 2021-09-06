package com.latoken.api.client.v2.response;

import lombok.Data;

import java.util.UUID;

@Data
public final class Transaction {
//      public enum Status{
//            TRANSACTION_STATUS_EXECUTED,
//      }
//
//      public enum Type{
//            TRANSACTION_TYPE_DEPOSIT,
//      }

    private UUID id;
    private String status; // todo find exact type (not in docs)
    private String type; // todo find exact type (not in docs)
    private String senderAddress;
    private String recipientAddress;
    private double amount;
    private long timestamp;
    private String transactionHash;
    private long blockHeight;
    private UUID currency;
    private String rejectReason;
    private String memo;
    private UUID paymentProvider;
    private boolean requiresCode;
}
