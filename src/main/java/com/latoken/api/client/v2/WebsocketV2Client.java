package com.latoken.api.client.v2;

import com.latoken.api.client.v2.response.Balance;
import com.latoken.api.client.v2.response.BookChange;
import com.latoken.api.client.v2.response.Currency;
import com.latoken.api.client.v2.response.Order;
import com.latoken.api.client.v2.response.Pair;
import com.latoken.api.client.v2.response.Rate;
import com.latoken.api.client.v2.response.Ticker;
import com.latoken.api.client.v2.response.Trade;
import com.latoken.api.client.v2.response.UserTransaction;
import com.latoken.api.client.v2.response.UserTransfer;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public interface WebsocketV2Client {

    void start();

    void stop();

    void onConnectionStateChange(Consumer<ConnectionState> consumer);

    Subscription subscribeToCurrencies(Consumer<List<Currency>> consumer);

    Subscription subscribeToPairs(Consumer<List<Pair>> consumer);

    Subscription subscribeToTickers(Consumer<List<Ticker>> consumer);

    Subscription subscribeToTickers(UUID base, UUID quote, Consumer<Ticker> consumer);

    Subscription subscribeToRates(UUID base, UUID quote, Consumer<List<Rate>> consumer);

    Subscription subscribeToBooks(UUID base, UUID quote, Consumer<BookChange> consumer);

    Subscription subscribeToTrades(UUID base, UUID quote, Consumer<List<Trade>> consumer);

    Subscription subscribeToOrders(UUID user, Consumer<List<Order>> consumer);

    Subscription subscribeToBalances(UUID user, Consumer<List<Balance>> consumer);

    Subscription subscribeToBalanceTotals(UUID user, Consumer<List<Balance>> consumer);

    Subscription subscribeToTransactions(UUID user, Consumer<List<UserTransaction>> consumer);

    Subscription subscribeToTransfers(UUID user, Consumer<List<UserTransfer>> consumer);

    enum ConnectionState {
        CONNECTED,
        DISCONNECTED
    }

    interface Subscription {
        void unsubscribe();
    }

}
