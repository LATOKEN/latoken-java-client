package com.latoken.api.client.v2;

public class ExamplePairs {
   public static void main(String[] args) throws Exception {
      System.out.println(
         Shared.restV2()
         .getActivePairs()
         .get()
      );
   }
}
