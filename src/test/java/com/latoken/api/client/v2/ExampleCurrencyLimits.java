package com.latoken.api.client.v2;

public class ExampleCurrencyLimits {
    public static void main(String[] args) throws Exception {
        Shared.restV2()
            .getCurrencyLimits().get().stream()
            .filter(it -> it.getTag().equals("BTC"))
            .forEach(System.out::println);
    }
}
