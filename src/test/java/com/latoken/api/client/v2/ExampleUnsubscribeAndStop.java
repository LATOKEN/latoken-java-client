package com.latoken.api.client.v2;

import com.latoken.api.client.Shared;
import com.latoken.api.client.v2.WebsocketV2Client.Subscription;

public class ExampleUnsubscribeAndStop {

   public static void main(String[] args) throws Exception {
      WebsocketV2Client client = Shared.wsV2();

      client.start();

      Subscription subscription = client.subscribeToTickers(tickers -> {
         tickers.forEach(System.out::println);
      });

      Thread.sleep(10000);

      subscription.unsubscribe();

      Thread.sleep(4000);

      subscription = client.subscribeToTickers(tickers -> {
         tickers.forEach(System.out::println);
      });

      Thread.sleep(10000);

      subscription.unsubscribe();
      Thread.sleep(4000);

      client.stop();
   }

}
