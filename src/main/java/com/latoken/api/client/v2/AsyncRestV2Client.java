package com.latoken.api.client.v2;


import com.latoken.api.client.v2.request.PlaceOrder;
import com.latoken.api.client.v2.response.Ack;
import com.latoken.api.client.v2.response.Balance;
import com.latoken.api.client.v2.response.Binding;
import com.latoken.api.client.v2.response.Bindings;
import com.latoken.api.client.v2.response.Book;
import com.latoken.api.client.v2.response.Currency;
import com.latoken.api.client.v2.response.CurrencyLimit;
import com.latoken.api.client.v2.response.DepositAddressResponse;
import com.latoken.api.client.v2.response.FeeScheme;
import com.latoken.api.client.v2.response.FeeTier;
import com.latoken.api.client.v2.response.Order;
import com.latoken.api.client.v2.response.PaginatedResult;
import com.latoken.api.client.v2.response.Pair;
import com.latoken.api.client.v2.response.ServerTime;
import com.latoken.api.client.v2.response.Ticker;
import com.latoken.api.client.v2.response.Trade;
import com.latoken.api.client.v2.response.Transaction;
import com.latoken.api.client.v2.response.Transfer;
import com.latoken.api.client.v2.response.UserDetails;
import com.latoken.api.client.v2.response.WithdrawalResponse;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface AsyncRestV2Client {
    CompletableFuture<UserDetails> getUser();

    CompletableFuture<List<Balance>> getBalances(boolean includeZeros);

    CompletableFuture<Balance> getBalances(UUID currency, Balance.Type type);

    default CompletableFuture<List<Balance>> getBalances() {
        return getBalances(false);
    }

    CompletableFuture<Book> getBook(UUID base, UUID quote, int limit);

    CompletableFuture<Map<UUID, double[]>> getWeeklyCharts();

    CompletableFuture<Map<UUID, double[]>> getWeeklyCharts(UUID base, UUID quote);

    CompletableFuture<List<Currency>> activeCurrencies();

    CompletableFuture<List<Currency>> availableCurrencies();

    CompletableFuture<List<UUID>> quoteCurrencies();

    CompletableFuture<Currency> getCurrencyById(UUID id);

    CompletableFuture<Currency> getCurrencyBySymbol(String symbol);

    CompletableFuture<List<Order>> getOrders(long from, int limit);

    default CompletableFuture<List<Order>> getOrders() {
        return getOrders(0, 100);
    }

    CompletableFuture<Ack> cancelOrder(UUID id);

    CompletableFuture<Ack> cancelAllOrders();

    CompletableFuture<Ack> cancelAllOrdersInPair(UUID base, UUID quote);

    CompletableFuture<Order> getOrderById(UUID id);

    CompletableFuture<List<Order>> getOrdersInPair(UUID base, UUID quote);

    CompletableFuture<List<Order>> getActiveOrdersInPair(UUID base, UUID quote);

    CompletableFuture<Ack> placeOrder(
        UUID baseCurrency,
        UUID quoteCurrency,
        PlaceOrder.Side side,
        PlaceOrder.Condition condition,
        PlaceOrder.Type type,
        @Nullable String clientOrderId,
        @Nullable Double price,
        double quantity,
        long timestamp
    );

    CompletableFuture<List<Pair>> getActivePairs();

    CompletableFuture<List<Pair>> getAvailablePairs();

    CompletableFuture<Transfer> deposit(double value, UUID currency);

    CompletableFuture<Transfer> withdraw(double value, UUID currency);

    CompletableFuture<List<Ticker>> getAllTickers();

    CompletableFuture<Ticker> getTicker(UUID base, UUID quote);

    CompletableFuture<ServerTime> getServerTime(UUID base, UUID quote);

    CompletableFuture<List<Trade>> getMyTrades(long from, int limit);

    default CompletableFuture<List<Trade>> getMyTrades() {
        return getMyTrades(System.currentTimeMillis(), 100);
    }

    CompletableFuture<List<Trade>> getMyTrades(long from, int limit, UUID base, UUID quote);


    default CompletableFuture<List<Trade>> getMyTrades(UUID base, UUID quote) {
        return getMyTrades(System.currentTimeMillis(), 100, base, quote);
    }

    CompletableFuture<FeeScheme> getMyFeeScheme(UUID base, UUID quote);

    CompletableFuture<List<Trade>> getTradeHistory(long from, int limit, UUID base, UUID quote);

    CompletableFuture<FeeScheme> getFeeScheme(UUID base, UUID quote);

    CompletableFuture<List<FeeTier>> getFeeTiers();

    CompletableFuture<PaginatedResult<Transaction>> getTransaction(int page, int pageSize);

    CompletableFuture<Bindings> getCurrenciesWithActiveBindings();

    CompletableFuture<List<Binding>> getBindings(UUID instrument);

    CompletableFuture<DepositAddressResponse> getDepositAddress(UUID instrument);

    CompletableFuture<WithdrawalResponse> requestWithdrawal(
        int twoFaCode,
        UUID currencyBinding,
        double amount,
        String recipientAddress,
        String memo
    );

    CompletableFuture<Ack> cancelWithdrawal(UUID id);

    CompletableFuture<Ack> confirmWithdrawal(UUID id);

    CompletableFuture<Ack> resendVerification(UUID id);

    CompletableFuture<Transaction> getTransaction(UUID id);

    CompletableFuture<List<CurrencyLimit>> getCurrencyLimits();

    CompletableFuture<List<Transfer>> getTransfers();

    CompletableFuture<Transfer> createTransferByEmail(
        double amount,
        String recipient,
        UUID currency,
        int twoFaCode
    );

    CompletableFuture<Transfer> createTransferById(
        double amount,
        UUID recipient,
        UUID currency,
        int twoFaCode
    );


    CompletableFuture<Transfer> createTransferByPhone(
        double amount,
        String recipient,
        UUID currency,
        int twoFaCode
    );
}
