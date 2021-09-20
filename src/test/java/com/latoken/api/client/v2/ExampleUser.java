package com.latoken.api.client.v2;

import com.latoken.api.client.Shared;

public class ExampleUser {
   public static void main(String[] args) throws Exception {
      System.out.println(
         Shared.restV2()
            .getUser()
            .get()
      );
   }
}
