package com.latoken.api.client.v2.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data 
@JsonIgnoreProperties(ignoreUnknown = true)
public final class BookChange {

    private List<Order> ask;
    private List<Order> bid;

    @Data
    public static final class Order {
        private double price;
        private double quantityChange;
        private double costChange;
        private double quantity;
        private double cost;
    }
}
