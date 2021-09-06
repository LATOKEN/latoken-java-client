package com.latoken.api.client.v2;

public class ExampleSubscriptionRates {

   public static void main(String[] args) throws Exception {
      WebsocketV2Client client = Shared.wsV2();
      client.start();
      client.subscribeToRates(Shared.BTC, Shared.USDT, rates -> rates.forEach(System.out::println));

      Thread.sleep(5000);
   }
}
