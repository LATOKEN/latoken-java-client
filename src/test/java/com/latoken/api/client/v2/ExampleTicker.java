package com.latoken.api.client.v2;

public class ExampleTicker {
   public static void main(String[] args) throws Exception {
      System.out.println(
         Shared.restV2()
            .getTicker(Shared.BTC, Shared.USDT)
            .get()
      );

      System.out.println(
         Shared.restV2()
            .getAllTickers()
            .get()
      );
   }
}
