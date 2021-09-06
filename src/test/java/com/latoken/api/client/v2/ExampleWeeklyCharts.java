package com.latoken.api.client.v2;

import java.util.Map;
import java.util.UUID;

public class ExampleWeeklyCharts {
   public static void main(String[] args) throws Exception {
      Map<UUID, double[]> map = Shared.restV2().getWeeklyCharts().get();
      System.out.println(map);

      map = Shared.restV2().getWeeklyCharts(Shared.BTC, Shared.USDT).get();
      System.out.println(map);
   }
}
