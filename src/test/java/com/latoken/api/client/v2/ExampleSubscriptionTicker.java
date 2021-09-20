package com.latoken.api.client.v2;

import com.latoken.api.client.Shared;

public class ExampleSubscriptionTicker {

   public static void main(String[] args) throws Exception {
      WebsocketV2Client client = Shared.wsV2();
      client.start();
      client.subscribeToTickers(Shared.BTC, Shared.USDT, ticker -> System.out.println(ticker));

      Thread.sleep(10000);
   }

}
