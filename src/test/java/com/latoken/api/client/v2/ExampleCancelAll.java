package com.latoken.api.client.v2;

import java.util.UUID;

public class ExampleCancelAll {
   public static void main(String[] args) throws Exception {
      System.out.println(
         Shared.restV2()
            .cancelOrder(UUID.fromString("5b8556b5-ed13-4aa8-b219-86975d07ed44"))
            .get()
      );
   }
}
