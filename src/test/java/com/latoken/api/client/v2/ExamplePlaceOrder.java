package com.latoken.api.client.v2;

import com.latoken.api.client.v2.request.PlaceOrder;
import com.latoken.api.client.v2.response.Ack;

public class ExamplePlaceOrder {
   public static void main(String[] args) throws Exception {
      Ack response = Shared.restV2().placeOrder(
         Shared.BTC,
         Shared.USDT,
         PlaceOrder.Side.BUY,
         PlaceOrder.Condition.FILL_OR_KILL,
         PlaceOrder.Type.MARKET,
         "",
         null,
         0.0001,
         System.currentTimeMillis()
      ).get();

      Thread.sleep(1000);
      System.out.println(Shared.restV2().getOrderById(response.getId()).get());
      System.out.println(response);
   }
}
