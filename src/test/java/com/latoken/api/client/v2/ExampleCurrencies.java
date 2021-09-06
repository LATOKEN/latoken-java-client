package com.latoken.api.client.v2;

public class ExampleCurrencies {
   public static void main(String[] args) throws Exception {
      System.out.println("activeCurrencies()");
      System.out.println(Shared.restV2().activeCurrencies().get());

      System.out.println("quoteCurrencies()");
      System.out.println(Shared.restV2().quoteCurrencies().get());

      System.out.println("getCurrencyById(92151d82-df98-4d88-9a4d-284fa9eca49f)");
      System.out.println(Shared.restV2().getCurrencyById(Shared.BTC).get());

      System.out.println("getCurrencyByTag(BTC)");
      System.out.println(Shared.restV2().getCurrencyByTag("BTC").get());
   }
}
