package com.latoken.api.client.v2;

import com.latoken.api.client.Shared;

import java.util.UUID;

public class ExampleSubscriptionUserOrders {

   public static void main(String[] args) throws Exception {
      UUID userId = Shared.restV2().getUser().get().getId();
      System.out.println("my user id : " + userId);

      WebsocketV2Client client = Shared.wsV2();
      client.start();
      client.subscribeToOrders(userId, orders -> orders.forEach(System.out::println));

      Thread.sleep(5000);
   }

}
