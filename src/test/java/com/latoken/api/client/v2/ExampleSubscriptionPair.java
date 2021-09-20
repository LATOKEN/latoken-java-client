package com.latoken.api.client.v2;

import com.latoken.api.client.Shared;

public class ExampleSubscriptionPair {

   public static void main(String[] args) throws Exception {
      WebsocketV2Client client = Shared.wsV2();
      client.start();
      client.subscribeToPairs(pairs -> pairs.forEach(System.out::println));

      Thread.sleep(5000);
   }

}
