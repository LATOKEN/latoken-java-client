package com.latoken.api.client.v2;

public class ExampleSubscriptionTicker {

   public static void main(String[] args) throws Exception {
      WebsocketV2Client client = Shared.wsV2();
      client.start();
      client.subscribeToTickers(tickers -> tickers.forEach(System.out::println));

      Thread.sleep(5000);
   }

}
