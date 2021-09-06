package com.latoken.api.client.v2;

import com.latoken.api.client.v2.response.Balance;

public class ExampleBalanceByCurrency {
   public static void main(String[] args) throws Exception {

      Balance balance = Shared.restV2()
         .getBalances(Shared.BTC, Balance.Type.ACCOUNT_TYPE_SPOT)
         .get();

      System.out.println(balance);
   }
}
