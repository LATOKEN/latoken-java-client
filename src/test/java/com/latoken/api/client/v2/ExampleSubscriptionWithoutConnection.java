package com.latoken.api.client.v2;

public class ExampleSubscriptionWithoutConnection {

   public static void main(String[] args) throws Exception {
      WebsocketV2Client client = Shared.wsV2();
//      client.start();
      client.subscribeToTickers(Shared.BTC, Shared.USDT, ticker -> System.out.println(ticker));

      Thread.sleep(1000);
   }

}
