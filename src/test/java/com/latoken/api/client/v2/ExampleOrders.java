package com.latoken.api.client.v2;

import com.latoken.api.client.v2.response.Order;

public class ExampleOrders {
   public static void main(String[] args) throws Exception {
      Shared.restV2().getOrders(System.currentTimeMillis(), 1000)
         .get()
         .stream()
         .filter(order -> order.getStatus() == Order.Status.ORDER_STATUS_PLACED)
         .forEach(System.out::println);
   }
}
