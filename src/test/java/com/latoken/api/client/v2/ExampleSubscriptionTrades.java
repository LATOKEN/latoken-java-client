package com.latoken.api.client.v2;

public class ExampleSubscriptionTrades {

   public static void main(String[] args) throws Exception {
      WebsocketV2Client client = Shared.wsV2();
      client.start();
      client.subscribeToTrades(Shared.BTC, Shared.USDT, trades -> trades.forEach(System.out::println));

      Thread.sleep(10000);
   }

}
