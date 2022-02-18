package com.latoken.api.client.v2.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.UUID;

@Data 
@JsonIgnoreProperties(ignoreUnknown = true)
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
