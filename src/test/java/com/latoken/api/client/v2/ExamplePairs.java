package com.latoken.api.client.v2;

import java.util.UUID;

public class ExamplePairs {
   public static void main(String[] args) throws Exception {
      System.out.println(
         Shared.restV2()
         .getOrderById(UUID.fromString("29a6efbc-bd0b-4d59-8c08-285fffd81fab"))
         .get()
      );
   }
}
