package com.latoken.api.client.v2;

import com.latoken.api.client.Shared;
import com.latoken.api.client.v2.response.Book;

public class ExampleBook {
   public static void main(String[] args) throws Exception {
      Book book = Shared.restV2().getBook(Shared.BTC, Shared.USDT, 1000).get();
      System.out.println(book);
   }
}
