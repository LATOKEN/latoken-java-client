package com.latoken.api.client.v2.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.UUID;

@Data 
@JsonIgnoreProperties(ignoreUnknown = true)
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
    private UUID user;
    private UUID paymentProvider;
    private Boolean requiresCode;
}
