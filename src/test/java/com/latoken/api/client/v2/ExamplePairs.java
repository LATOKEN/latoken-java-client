package com.latoken.api.client.v2;

import com.latoken.api.client.Shared;

public class ExamplePairs {
   public static void main(String[] args) throws Exception {
      System.out.println(
         Shared.restV2()
         .getActivePairs()
         .get()
      );
   }
}
