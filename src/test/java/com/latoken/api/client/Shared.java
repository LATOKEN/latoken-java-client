package com.latoken.api.client;

import com.latoken.api.client.v2.AsyncRestV2Client;
import com.latoken.api.client.v2.WebsocketV2Client;
import com.latoken.api.client.v3.AsyncRestV3Client;
import com.latoken.api.client.v3.WebsocketV3Client;

import java.util.HashMap;
import java.util.UUID;

public final class Shared {
   // prod
   public static final String key = "your-key";
   public static final String sec = "your-secret";

//    qa
//   static final String key = "2268f839-2fa6-4ded-b4b5-851369d358d3";
//   static final String sec = "MGNjOWYxYjktZWZiNC00NjJmLWExMTUtZGMyYjgwZWM1ZWJl";

   // just to have lazyness, in real use you won't create websocket client if you don't need it
   private static final HashMap<String, Object> cache = new HashMap<>();

   public static AsyncRestV2Client restV2() {
      return (AsyncRestV2Client) cache.computeIfAbsent("restV2Client", s -> Latoken.asyncClientV2(key, sec));
   }

   public static WebsocketV2Client wsV2() {
      return (WebsocketV2Client) cache.computeIfAbsent("wsV2", s -> Latoken.websocketV2Client(key, sec));
   }

   public static AsyncRestV3Client restV3() {
      return (AsyncRestV3Client) cache.computeIfAbsent("restV3Client", s -> Latoken.asyncClientV3(key, sec));
   }

   public static WebsocketV3Client wsV3() {
      return (WebsocketV3Client) cache.computeIfAbsent("wsV3", s -> Latoken.websocketV3Client(key, sec));
   }


   public static UUID BTC = UUID.fromString("92151d82-df98-4d88-9a4d-284fa9eca49f");
   public static UUID TUSD = UUID.fromString("211cec45-a160-4b69-9a7c-60ebc9d37a48");
   public static UUID USDT = UUID.fromString("0c3a106d-bde3-4c13-a26e-3fd2394529e5");

   private Shared() {
   }
}
