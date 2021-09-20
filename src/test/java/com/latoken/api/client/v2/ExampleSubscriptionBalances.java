package com.latoken.api.client.v2;

import com.latoken.api.client.Shared;

import java.util.UUID;

public class ExampleSubscriptionBalances {

   public static void main(String[] args) throws Exception {
      UUID userId = Shared.restV2().getUser().get().getId();
      System.out.println("my user id : " + userId);

      WebsocketV2Client client = Shared.wsV2();
      client.start();
//      client.subscribeToBalances(userId, it -> {
//         it.forEach(x -> System.out.println("subscribeToBalances >> "+x));
//      });
      client.subscribeToBalanceTotals(userId, it -> {
         it.forEach(x -> System.out.println("subscribeToBalanceTotals >> "+x));
      });

      Thread.sleep(50000);
   }

}
