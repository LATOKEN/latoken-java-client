package com.latoken.api.client.v2;

public class ExampleBalances {
   public static void main(String[] args) throws Exception {
      Shared.restV2()
         .getBalances(false)
         .get() // we block since it's acceptable for this example
         .forEach(System.out::println);
   }
}
