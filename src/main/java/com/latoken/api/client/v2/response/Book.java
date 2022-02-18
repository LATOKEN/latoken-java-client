package com.latoken.api.client.v2.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data 
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Book {

    private List<Order> ask;
    private List<Order> bid;
    private double totalAsk;
    private double totalBid;

    @Data
    public static final class Order {
        private double price;
        private double quantity;
        private double cost;
        private double accumulated;
    }
}
