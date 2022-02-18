package com.latoken.api.client.v2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleSubscriptionRawStompTicker {

   static Logger logger = LoggerFactory.getLogger(ExampleSubscriptionRawStompTicker.class);

   public static void main(String[] args) throws Exception {
      SpringStompClient client = new SpringStompClient(
         "wss://api.latoken.com/stomp",
         runnable -> {
            Thread thread = new Thread(runnable);
            thread.setDaemon(true);
            return thread;
         }
      );

      client.connect();

//      String topic = "/v1/ticker";
      String topic = "/v1/currency";

      client.subscribe(topic, bytes -> {
         logger.info(topic + " << {}", new String(bytes));
      });

      Thread.sleep(60000);

      client.stop();
   }

}
