package com.latoken.api.client.v2;

import com.latoken.api.client.Shared;

public class ExampleActiveCurrencies {
   public static void main(String[] args) throws Exception {
      int s1 = Shared.restV2()
          .activeCurrencies()
          .get()
          .size();
      int s2 = Shared.restV2()
          .availableCurrencies()
          .get()
          .size();
      System.out.println(s1);
      System.out.println(s2);
   }
}
