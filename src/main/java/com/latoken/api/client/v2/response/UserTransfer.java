package com.latoken.api.client.v2.response;

import lombok.Data;

import java.util.UUID;

@Data
public class UserTransfer {
    private UUID id;
    private String status;
    private String type;
    private String fromUser;
    private String toUser;
    private UUID fromAccount;
    private UUID toAccount;
    private double transferringAmount;
    private String direction;
    private long timestamp;
    private String sender;
    private String recipient;

}
