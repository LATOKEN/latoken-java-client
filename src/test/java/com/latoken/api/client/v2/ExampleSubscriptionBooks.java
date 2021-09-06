package com.latoken.api.client.v2;

public class ExampleSubscriptionBooks {

   public static void main(String[] args) throws Exception {
      WebsocketV2Client client = Shared.wsV2();
      client.start();
      client.subscribeToBooks(Shared.BTC, Shared.USDT, book -> System.out.println(book));

      Thread.sleep(5000);
   }

}
