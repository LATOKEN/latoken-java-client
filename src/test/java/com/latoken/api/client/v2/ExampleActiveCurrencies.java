package com.latoken.api.client.v2;

public class ExampleActiveCurrencies {
   public static void main(String[] args) throws Exception {
      Shared.restV2()
         .activeCurrencies()
         .get() // we block since it's acceptable for this example
         .forEach(System.out::println);
   }
}
