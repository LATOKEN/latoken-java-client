package com.latoken.api.client.v2;

import com.latoken.api.client.v2.request.PlaceOrder;
import com.latoken.api.client.v2.response.Ack;

public class ExamplePlaceOrder {
   public static void main(String[] args) throws Exception {

      Ack response = Shared.restV2().placeOrder(
         Shared.TUSD,
         Shared.USDT,
         PlaceOrder.Side.BUY,
         PlaceOrder.Condition.GOOD_TILL_CANCELLED,
         PlaceOrder.Type.LIMIT,
         "test_order_1",
         0.5,
         100.0,
         System.currentTimeMillis()
      ).get();

      System.out.println(response);
   }
}
