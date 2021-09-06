package com.latoken.api.client.v2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.latoken.api.client.v2.request.PlaceOrder;
import com.latoken.api.client.v2.response.Ack;
import com.latoken.api.client.v2.response.Balance;
import com.latoken.api.client.v2.response.Binding;
import com.latoken.api.client.v2.response.Bindings;
import com.latoken.api.client.v2.response.Book;
import com.latoken.api.client.v2.response.Currency;
import com.latoken.api.client.v2.response.CurrencyLimit;
import com.latoken.api.client.v2.response.DepositAddressResponse;
import com.latoken.api.client.v2.response.Error;
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
import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static com.latoken.api.client.v2.Validation.checkNotNull;
import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static org.apache.commons.lang3.StringUtils.removeStart;

@SuppressWarnings("UastIncorrectHttpHeaderInspection")
final class AsyncRestV2ClientImpl implements AsyncRestV2Client {
    static final Logger LOGGER = LoggerFactory.getLogger(AsyncRestV2ClientImpl.class);
    final String BASE_URL = "https://api.latoken.com";
//   final String BASE_URL = "https://api-qa-auto.dev3.nekotal.tech";

    final Mac crypto;

    final String key;
    final String sec;

    final OkHttpClient client;
    final ObjectMapper jsonMapper;

    public AsyncRestV2ClientImpl(
        @NotNull String key,
        @NotNull String sec,
        @NotNull ObjectMapper jsonMapper,
        @NotNull OkHttpClient httpClient
    ) {
        this.key = key;
        this.sec = sec;
        this.jsonMapper = jsonMapper;
        this.client = httpClient;

        try {
            this.crypto = Mac.getInstance("HmacSHA256");
            this.crypto.init(new SecretKeySpec(sec.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new LatokenApiException("failed to create api client", e);
        }
    }

    @Override
    public CompletableFuture<UserDetails> getUser() {
        return asyncRequest(
            "GET",
            BASE_URL + "/v2/auth/user",
            emptyMap(),
            CustomJacksonTypes.USER_DETAILS
        );
    }

    @Override
    public CompletableFuture<List<Balance>> getBalances(boolean includeZeros) {
        return asyncRequest(
            "GET",
            BASE_URL + "/v2/auth/account",
            singletonMap("zeros", Boolean.toString(includeZeros)),
            CustomJacksonTypes.LIST_OF_BALANCES
        );
    }

    @Override
    public CompletableFuture<Balance> getBalances(UUID currency, Balance.Type type) {
        return asyncRequest(
            "GET",
            BASE_URL + "/v2/auth/account/currency/" + currency + "/" + type,
            emptyMap(),
            CustomJacksonTypes.BALANCE
        );
    }

    @Override
    public CompletableFuture<Book> getBook(UUID base, UUID quote, int limit) {
        return asyncRequest(
            "GET",
            BASE_URL + "/v2/book/" + base + "/" + quote,
            singletonMap("limit", Integer.toString(limit)),
            CustomJacksonTypes.BOOK
        );
    }

    @Override
    public CompletableFuture<Map<UUID, double[]>> getWeeklyCharts() {
        return asyncRequest(
            "GET",
            BASE_URL + "/v2/chart/week",
            emptyMap(),
            CustomJacksonTypes.MAP_OF_CHARTS
        );
    }

    @Override
    public CompletableFuture<Map<UUID, double[]>> getWeeklyCharts(UUID base, UUID quote) {
        return asyncRequest(
            "GET",
            BASE_URL + "/v2/chart/week/" + base + "/" + quote,
            emptyMap(),
            CustomJacksonTypes.MAP_OF_CHARTS
        );
    }

    @Override
    public CompletableFuture<List<Currency>> activeCurrencies() {
        return asyncRequest(
            "GET",
            BASE_URL + "/v2/currency",
            emptyMap(),
            CustomJacksonTypes.LIST_OF_CURRENCIES
        );
    }


    @Override
    public CompletableFuture<List<Currency>> availableCurrencies() {
        return asyncRequest(
            "GET",
            BASE_URL + "/v2/currency/available",
            emptyMap(),
            CustomJacksonTypes.LIST_OF_CURRENCIES
        );
    }

    @Override
    public CompletableFuture<List<UUID>> quoteCurrencies() {
        return asyncRequest(
            "GET",
            BASE_URL + "/v2/currency/quotes",
            emptyMap(),
            CustomJacksonTypes.LIST_OF_IDS
        );
    }

    @Override
    public CompletableFuture<Currency> getCurrencyById(UUID id) {
        return asyncRequest(
            "GET",
            BASE_URL + "/v2/currency/" + id,
            emptyMap(),
            CustomJacksonTypes.CURRENCY
        );
    }


    @Override
    public CompletableFuture<Currency> getCurrencyByTag(String tag) {
        return asyncRequest(
            "GET",
            BASE_URL + "/v2/currency/" + tag,
            emptyMap(),
            CustomJacksonTypes.CURRENCY
        );
    }

    @Override
    public CompletableFuture<List<Order>> getOrders(long from, int limit) {
        HashMap<String, String> params = new HashMap<>();
        params.put("from", Long.toString(from));
        params.put("limit", Integer.toString(limit));

        return asyncRequest(
            "GET",
            BASE_URL + "/v2/auth/order",
            params,
            CustomJacksonTypes.LIST_OF_ORDERS
        );
    }

    @Override
    public CompletableFuture<Ack> cancelOrder(UUID id) {
        return asyncRequest(
            "POST",
            BASE_URL + "/v2/auth/order/cancel",
            singletonMap("id", id.toString()),
            CustomJacksonTypes.ACK
        );
    }

    @Override
    public CompletableFuture<Ack> cancelAllOrders() {
        return asyncRequest(
            "POST",
            BASE_URL + "/v2/auth/order/cancelAll",
            emptyMap(),
            CustomJacksonTypes.ACK
        );
    }

    @Override
    public CompletableFuture<Ack> cancelAllOrdersInPair(UUID base, UUID quote) {
        return asyncRequest(
            "POST",
            BASE_URL + "/v2/auth/order/cancelAll/" + base + "/" + quote,
            emptyMap(),
            CustomJacksonTypes.ACK
        );
    }

    @Override
    public CompletableFuture<Order> getOrderById(UUID id) {
        return asyncRequest(
            "POST",
            BASE_URL + "/v2/auth/getOrder/" + id,
            emptyMap(),
            CustomJacksonTypes.ORDER
        );
    }

    @Override
    public CompletableFuture<List<Order>> getOrdersInPair(UUID base, UUID quote) {
        return asyncRequest(
            "POST",
            BASE_URL + "/v2/auth/getOrder/" + base + "/" + quote,
            emptyMap(),
            CustomJacksonTypes.LIST_OF_ORDERS
        );
    }

    @Override
    public CompletableFuture<List<Order>> getActiveOrdersInPair(UUID base, UUID quote) {
        return asyncRequest(
            "POST",
            BASE_URL + "/v2/auth/getOrder/" + base + "/" + quote + "/active",
            emptyMap(),
            CustomJacksonTypes.LIST_OF_ORDERS
        );
    }

    @Override
    public CompletableFuture<Ack> placeOrder(
        UUID baseCurrency,
        UUID quoteCurrency,
        PlaceOrder.Side side,
        PlaceOrder.Condition condition,
        PlaceOrder.Type type,
        String clientOrderId,
        Double price,
        double quantity,
        long timestamp
    ) {
        Map<String, String> params = new HashMap<>();
        params.put("baseCurrency", checkNotNull(baseCurrency).toString());
        params.put("quoteCurrency", checkNotNull(quoteCurrency.toString()));
        params.put("side", checkNotNull(side).toString());
        params.put("condition", checkNotNull(condition).toString());
        params.put("type", checkNotNull(type).toString());
        params.put("clientOrderId", clientOrderId);
        if (price != null) {
            params.put("price", price.toString());
        }
        params.put("quantity", Double.toString(quantity));
        params.put("timestamp", Long.toString(timestamp));

        return asyncRequest(
            "POST",
            BASE_URL + "/v2/auth/order/place",
            params,
            CustomJacksonTypes.ACK
        );
    }

    @Override
    public CompletableFuture<List<Pair>> getActivePairs() {
        return asyncRequest(
            "GET",
            BASE_URL + "/v2/pair",
            emptyMap(),
            CustomJacksonTypes.LIST_OF_PAIRS
        );
    }

    @Override
    public CompletableFuture<List<Pair>> getAvailablePairs() {
        return asyncRequest(
            "GET",
            BASE_URL + "/v2/pair/available",
            emptyMap(),
            CustomJacksonTypes.LIST_OF_PAIRS
        );
    }

    @Override
    public CompletableFuture<Transfer> deposit(double value, UUID currency) {
        Map<String, String> params = new HashMap<>();
        params.put("value", Double.toString(value));
        params.put("currency", currency.toString());

        return asyncRequest(
            "POST",
            BASE_URL + "/v2/auth/spot/deposit",
            params,
            CustomJacksonTypes.TRANSFER
        );
    }

    @Override
    public CompletableFuture<Transfer> withdraw(double value, UUID currency) {
        Map<String, String> params = new HashMap<>();
        params.put("value", Double.toString(value));
        params.put("currency", currency.toString());

        return asyncRequest(
            "POST",
            BASE_URL + "/v2/auth/spot/withdraw",
            params,
            CustomJacksonTypes.TRANSFER
        );
    }

    @Override
    public CompletableFuture<List<Ticker>> getAllTickers() {
        return asyncRequest(
            "GET",
            BASE_URL + "/v2/ticker",
            emptyMap(),
            CustomJacksonTypes.LIST_OF_TICKERS
        );
    }

    @Override
    public CompletableFuture<Ticker> getTicker(UUID base, UUID quote) {
        return asyncRequest(
            "GET",
            BASE_URL + "/v2/ticker/" + base + "/" + quote,
            emptyMap(),
            CustomJacksonTypes.TICKER
        );
    }

    @Override
    public CompletableFuture<ServerTime> getServerTime(UUID base, UUID quote) {
        return asyncRequest(
            "GET",
            BASE_URL + "/v2/time",
            emptyMap(),
            CustomJacksonTypes.SERVER_TIME
        );
    }

    @Override
    public CompletableFuture<List<Trade>> getMyTrades(long from, int limit) {
        Map<String, String> params = new HashMap<>();
        params.put("from", Long.toString(from));
        params.put("limit", Integer.toString(limit));

        return asyncRequest(
            "GET",
            BASE_URL + "/v2/auth/trade",
            params,
            CustomJacksonTypes.LIST_OF_TRADES
        );
    }

    @Override
    public CompletableFuture<List<Trade>> getMyTrades(long from, int limit, UUID base, UUID quote) {
        Map<String, String> params = new HashMap<>();
        params.put("from", Long.toString(from));
        params.put("limit", Integer.toString(limit));

        return asyncRequest(
            "GET",
            BASE_URL + "/v2/auth/trade/" + base + "/" + quote,
            params,
            CustomJacksonTypes.LIST_OF_TRADES
        );
    }

    @Override
    public CompletableFuture<FeeScheme> getMyFeeScheme(UUID base, UUID quote) {
        return asyncRequest(
            "GET",
            BASE_URL + "/v2/auth/trade/fee/" + base + "/" + quote,
            emptyMap(),
            CustomJacksonTypes.FEE_SCHEME
        );
    }


    @Override
    public CompletableFuture<List<Trade>> getTradeHistory(long from, int limit, UUID base, UUID quote) {
        Map<String, String> params = new HashMap<>();
        params.put("from", Long.toString(from));
        params.put("limit", Integer.toString(limit));

        return asyncRequest(
            "GET",
            BASE_URL + "/v2/trade/history/" + base + "/" + quote,
            params,
            CustomJacksonTypes.LIST_OF_TRADES
        );
    }

    @Override
    public CompletableFuture<FeeScheme> getFeeScheme(UUID base, UUID quote) {
        return asyncRequest(
            "GET",
            BASE_URL + "/v2/trade/fee/" + base + "/" + quote,
            emptyMap(),
            CustomJacksonTypes.FEE_SCHEME
        );
    }

    @Override
    public CompletableFuture<List<FeeTier>> getFeeTiers() {
        return asyncRequest(
            "GET",
            BASE_URL + "/v2/trade/feeLevels",
            emptyMap(),
            CustomJacksonTypes.LIST_OF_FEE_TIERS
        );
    }

    @Override
    public CompletableFuture<PaginatedResult<Transaction>> getTransaction(int page, int size) {
        Map<String, String> params = new HashMap<>();
        params.put("page", Integer.toString(page));
        params.put("size", Integer.toString(size));

        return asyncRequest(
            "GET",
            BASE_URL + "/v2/auth/transaction",
            params,
            CustomJacksonTypes.PAGE_OF_TRANSACTIONS
        );
    }


    @Override
    public CompletableFuture<Bindings> getCurrenciesWithActiveBindings() {
        return asyncRequest(
            "GET",
            BASE_URL + "/v2/auth/transaction/bindings",
            emptyMap(),
            CustomJacksonTypes.BINDINGS
        );
    }

    @Override
    public CompletableFuture<List<Binding>> getBindings(UUID instrument) {
        return asyncRequest(
            "GET",
            BASE_URL + "/v2/auth/transaction/bindings/" + instrument,
            emptyMap(),
            CustomJacksonTypes.LIST_OF_BINDINGS
        );
    }

    @Override
    public CompletableFuture<DepositAddressResponse> getDepositAddress(UUID currencyBinding) {
        return asyncRequest(
            "POST",
            BASE_URL + "/v2/auth/transaction/depositAddress",
            singletonMap("currencyBinding", currencyBinding.toString()),
            CustomJacksonTypes.DEPOSIT_ADDRESS_RESPONSE
        );
    }

    @Override
    public CompletableFuture<WithdrawalResponse> requestWithdrawal(
        int twoFaCode,
        UUID currencyBinding,
        double amount,
        String recipientAddress,
        String memo
    ) {
        Map<String, String> params = new HashMap<>();
        params.put("twoFaCode", Integer.toString(twoFaCode));
        params.put("currencyBinding", currencyBinding.toString());
        params.put("amount", Double.toString(amount));
        params.put("recipientAddress", recipientAddress);
        params.put("memo", memo);

        return asyncRequest(
            "POST",
            BASE_URL + "/v2/auth/transaction/withdraw",
            params,
            CustomJacksonTypes.WITHDRAWAL_RESPONSE
        );
    }

    @Override
    public CompletableFuture<Ack> cancelWithdrawal(UUID id) {
        return asyncRequest(
            "POST",
            BASE_URL + "/v2/auth/transaction/withdraw/cancel",
            singletonMap("id", id.toString()),
            CustomJacksonTypes.ACK
        );
    }

    @Override
    public CompletableFuture<Ack> confirmWithdrawal(UUID id) {
        return asyncRequest(
            "POST",
            BASE_URL + "/v2/auth/transaction/withdraw/confirm",
            singletonMap("id", id.toString()),
            CustomJacksonTypes.ACK
        );
    }

    @Override
    public CompletableFuture<Ack> resendVerification(UUID id) {
        return asyncRequest(
            "POST",
            BASE_URL + "/v2/auth/transaction/withdraw/resendCode",
            singletonMap("id", id.toString()),
            CustomJacksonTypes.ACK
        );
    }

    @Override
    public CompletableFuture<Transaction> getTransaction(UUID id) {
        return asyncRequest(
            "GET",
            BASE_URL + "/v2/auth/transaction/" + id,
            emptyMap(),
            CustomJacksonTypes.TRANSACTION
        );
    }

    @Override
    public CompletableFuture<List<CurrencyLimit>> getCurrencyLimits() {
        return asyncRequest(
            "GET",
            BASE_URL + "/v2/transaction/bindings",
            emptyMap(),
            CustomJacksonTypes.LIST_OF_CURRENCY_LIMITS
        );
    }

    @Override
    public CompletableFuture<List<Transfer>> getTransfers() {
        return asyncRequest(
            "GET",
            BASE_URL + "/v2/auth/transfer",
            emptyMap(),
            CustomJacksonTypes.LIST_OF_TRANSFERS
        );
    }

    @Override
    public CompletableFuture<Transfer> createTransferByEmail(
        double amount,
        String recipient,
        UUID currency,
        int twoFaCode
    ) {
        Map<String, String> params = new HashMap<>();
        params.put("amount", Double.toString(amount));
        params.put("recipient", recipient.toString());
        params.put("currency", currency.toString());
        params.put("twoFaCode", Integer.toString(twoFaCode));

        return asyncRequest(
            "POST",
            BASE_URL + "/v2/auth/transfer/email",
            params,
            CustomJacksonTypes.TRANSFER
        );
    }

    @Override
    public CompletableFuture<Transfer> createTransferById(
        double amount,
        UUID recipient,
        UUID currency,
        int twoFaCode
    ) {
        Map<String, String> params = new HashMap<>();
        params.put("amount", Double.toString(amount));
        params.put("recipient", recipient.toString());
        params.put("currency", currency.toString());
        params.put("twoFaCode", Integer.toString(twoFaCode));

        return asyncRequest(
            "POST",
            BASE_URL + "/v2/auth/transfer/id",
            params,
            CustomJacksonTypes.TRANSFER
        );
    }

    @Override
    public CompletableFuture<Transfer> createTransferByPhone(
        double amount,
        String recipient,
        UUID currency,
        int twoFaCode
    ) {
        Map<String, String> params = new HashMap<>();
        params.put("amount", Double.toString(amount));
        params.put("recipient", recipient);
        params.put("currency", currency.toString());
        params.put("twoFaCode", Integer.toString(twoFaCode));

        return asyncRequest(
            "POST",
            BASE_URL + "/v2/auth/transfer/phone",
            params,
            CustomJacksonTypes.TRANSFER
        );
    }

    /**
     * We never throw exceptions from async api methods, exceptions will be set in the future instance.
     * This allows clients to handle exceptions in one place.
     */
    private <T> CompletableFuture<T> asyncRequest(String method, String endpoint, Map<String, String> params, TypeReference<T> responseType) {
        CompletableFuture<T> future = new CompletableFuture<>();

        Request request;
        try {
            request = checkNotNull(buildRequest(method, endpoint, params));
        } catch (Exception e) {
            if (e.getClass().equals(LatokenApiException.class)) {
                future.completeExceptionally(e);
            } else {
                future.completeExceptionally(new LatokenApiException("failed to build request " + method + " | " + endpoint, e));
            }
            return future;
        }

        Callback callback = new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseString = "";
                int code = response.code();
                ResponseBody body = response.body();
                if (body != null) {
                    responseString = body.string();
                }

                if (!response.isSuccessful()) {
                    try {
                        Error error = jsonMapper.readValue(responseString, Error.class);
                        future.completeExceptionally(new LatokenApiException("request failed  (" + code + ") : " + responseString, error));
                    } catch (Exception e) {
                        future.completeExceptionally(new LatokenApiException("request failed (" + code + "), failed to parse response from '" + responseString + "'", e));
                    }
                } else {
                    try {
                        T result = jsonMapper.readValue(responseString, responseType);
                        future.complete(result);
                    } catch (Exception e) {
                        future.completeExceptionally(new LatokenApiException("request successful  (" + code + "), failed to parse response from '" + responseString + "'", e));
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                future.completeExceptionally(new LatokenApiException(e));
            }
        };

        client.newCall(request).enqueue(callback);
        return future;
    }

    private Request buildRequest(String method, String urlString, Map<String, String> params) throws JsonProcessingException {
        if (method.equals("GET")) {
            HttpUrl url = checkNotNull(HttpUrl.parse(urlString));
            if (!params.isEmpty()) {
                HttpUrl.Builder ub = url.newBuilder();
                params.forEach(ub::addQueryParameter);
                url = ub.build();
            }

            String signature = sign(method, url.toString());
            LOGGER.warn("signature : {}", signature);

            return new Request.Builder()
                .get()
                .url(url)
                .header("X-LA-APIKEY", key)
                .header("X-LA-SIGNATURE", signature)
                .header("X-LA-DIGEST", "HMAC-SHA512")
                .build();
        } else if (method.equals("POST")) {
            HttpUrl url = checkNotNull(HttpUrl.parse(urlString));

            if (params.isEmpty()) {
                String signature = sign(method, url.toString());
                return new Request.Builder()
                    .post(RequestBody.create(new byte[0], null))
                    .url(url)
                    .header("Content-Length", "0")
                    .header("Content-Type", "application/json")
                    .header("X-LA-APIKEY", key)
                    .header("X-LA-SIGNATURE", signature)
                    .header("X-LA-DIGEST", "HMAC-SHA512").build();
            } else {
                HttpUrl.Builder ub = url.newBuilder();

                params.forEach(ub::addQueryParameter);
                HttpUrl urlWithParams = ub.build();
                String signature = sign(method, urlWithParams.toString());
                LOGGER.warn("signature : {}", signature);

                String json = jsonMapper.writeValueAsString(params);
                LOGGER.warn("body : {}", json);
                RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));

                return new Request.Builder()
                    .post(body)
                    .url(url)
                    .header("Content-Type", "application/json")
                    .header("X-LA-APIKEY", key)
                    .header("X-LA-SIGNATURE", signature)
                    .header("X-LA-DIGEST", "HMAC-SHA512").build();
            }

        } else {
            throw new LatokenApiException("not supported request method " + method);
        }
    }

    private String sign(String method, String url) {
//      String stringToSign = method + removeStart(url, "https://api.latoken.com");
//         todo fix this after MID-4992 is fixed
        String prefix = removeStart(url, BASE_URL);
        prefix = prefix.replace("?", "");
        String stringToSign = method + prefix;
        LOGGER.warn("signature string : '{}' : {}", stringToSign, stringToSign.hashCode());
        byte[] bytes = stringToSign.getBytes(StandardCharsets.UTF_8);
        return HexBin.encode(this.crypto.doFinal(bytes)).toLowerCase();
    }
}


