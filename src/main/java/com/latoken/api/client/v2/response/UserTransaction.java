package com.latoken.api.client.v2.response;

import lombok.Data;

import java.util.UUID;

@Data
public class UserTransaction {
    private UUID id;
    private String status;
    private String type;
    private String senderAddress;
    private String recipientAddress;
    private double transferredAmount;
    private long timestamp;
    private String transactionHash;
    private int blockHeight;
    private String comment;
    private double transactionFee;
    private UUID currency;
}
