package com.latoken.api.client.v2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.latoken.api.client.v2.response.Balance;
import com.latoken.api.client.v2.response.BalanceTotal;
import com.latoken.api.client.v2.response.BookChange;
import com.latoken.api.client.v2.response.Currency;
import com.latoken.api.client.v2.response.Order;
import com.latoken.api.client.v2.response.Pair;
import com.latoken.api.client.v2.response.Payload;
import com.latoken.api.client.v2.response.Rate;
import com.latoken.api.client.v2.response.Ticker;
import com.latoken.api.client.v2.response.Trade;
import com.latoken.api.client.v2.response.UserTransaction;
import com.latoken.api.client.v2.response.UserTransfer;
import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.web.socket.WebSocketHttpHeaders;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadFactory;
import java.util.function.Consumer;

import static com.latoken.api.client.v2.CustomJacksonTypes.PAYLOAD_WITH_BOOK_CHANGES;
import static com.latoken.api.client.v2.CustomJacksonTypes.PAYLOAD_WITH_LIST_OF_BALANCES;
import static com.latoken.api.client.v2.CustomJacksonTypes.PAYLOAD_WITH_LIST_OF_BALANCE_TOTALS;
import static com.latoken.api.client.v2.CustomJacksonTypes.PAYLOAD_WITH_LIST_OF_CURRENCIES;
import static com.latoken.api.client.v2.CustomJacksonTypes.PAYLOAD_WITH_LIST_OF_ORDERS;
import static com.latoken.api.client.v2.CustomJacksonTypes.PAYLOAD_WITH_LIST_OF_PAIRS;
import static com.latoken.api.client.v2.CustomJacksonTypes.PAYLOAD_WITH_LIST_OF_RATES;
import static com.latoken.api.client.v2.CustomJacksonTypes.PAYLOAD_WITH_LIST_OF_TICKERS;
import static com.latoken.api.client.v2.CustomJacksonTypes.PAYLOAD_WITH_LIST_OF_TRADES;
import static com.latoken.api.client.v2.CustomJacksonTypes.PAYLOAD_WITH_LIST_OF_USER_TRANSACTIONS;
import static com.latoken.api.client.v2.CustomJacksonTypes.PAYLOAD_WITH_LIST_OF_USER_TRANSFERS;

class WebsocketV2ClientImpl implements WebsocketV2Client {
    static final Logger logger = LoggerFactory.getLogger(WebsocketV2ClientImpl.class);
    final String url = "wss://api.latoken.com/stomp";
//   final String url = "wss://api-qa-auto.dev3.nekotal.tech/stomp";

    final String key;
    final String sec;
    final ObjectMapper jsonMapper;
    final SpringStompClient client;
    final Mac crypto;

    public WebsocketV2ClientImpl(String key, String sec, ObjectMapper objectMapper, ThreadFactory threadFactory) {
        this.key = key;
        this.sec = sec;
        this.jsonMapper = objectMapper;
        this.client = new SpringStompClient(url, threadFactory);

        try {
            this.crypto = Mac.getInstance("HmacSHA256");
            this.crypto.init(new SecretKeySpec(sec.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new LatokenApiException("failed to create api client", e);
        }
    }

    @Override
    public void start() {
        try {
            logger.info("initiating websocket connection to {}", url);
            // todo @Dmitry please help with this
            String now = Long.toString(System.currentTimeMillis());
            String sign = sign(now);
            WebSocketHttpHeaders handshakeHeaders = new WebSocketHttpHeaders();
            handshakeHeaders.add("X-LA-APIKEY", this.key);
            handshakeHeaders.add("X-LA-SIGNATURE", sign);
            handshakeHeaders.add("X-LA-DIGEST", "HMAC-SHA512");
            handshakeHeaders.add("X-LA-SIGDATA", now);
            this.client.connect(handshakeHeaders);
        } catch (Exception e) {
            throw new LatokenApiException("failed to start websocket client", e);
        }
    }

    @Override
    public void stop() {
        try {
            logger.info("shutting down websocket connection to {}", url);
            this.client.stop();
        } catch (Exception e) {
            throw new LatokenApiException("failed to shut down websocket client", e);
        }
    }

    @Override
    public Subscription subscribeToCurrencies(Consumer<List<Currency>> consumer) {
        return subscribe(
            "/v1/currency",
            PAYLOAD_WITH_LIST_OF_CURRENCIES,
            consumer
        );
    }

    @Override
    public Subscription subscribeToPairs(Consumer<List<Pair>> consumer) {
        return subscribe(
            "/v1/pair",
            PAYLOAD_WITH_LIST_OF_PAIRS,
            consumer
        );
    }

    @Override
    public Subscription subscribeToTickers(Consumer<List<Ticker>> consumer) {
        return subscribe(
            "/v1/ticker",
            PAYLOAD_WITH_LIST_OF_TICKERS,
            consumer
        );
    }

    @Override
    public Subscription subscribeToRates(UUID base, UUID quote, Consumer<List<Rate>> consumer) {
        return subscribe(
            "/v1/rate/" + base + "/" + quote,
            PAYLOAD_WITH_LIST_OF_RATES,
            consumer
        );
    }

    @Override
    public Subscription subscribeToBooks(UUID base, UUID quote, Consumer<BookChange> consumer) {
        return subscribe(
            "/v1/book/" + base + "/" + quote,
            PAYLOAD_WITH_BOOK_CHANGES,
            consumer
        );
    }

    @Override
    public Subscription subscribeToTrades(UUID base, UUID quote, Consumer<List<Trade>> consumer) {
        return subscribe(
            "/v1/trade/" + base + "/" + quote,
            PAYLOAD_WITH_LIST_OF_TRADES,
            consumer
        );
    }

    @Override
    public Subscription subscribeToOrders(UUID user, Consumer<List<Order>> consumer) {
        return subscribe(
            "/user/" + user + "/v1/order",
            PAYLOAD_WITH_LIST_OF_ORDERS,
            consumer
        );
    }

    @Override
    public Subscription subscribeToBalances(UUID user, Consumer<List<Balance>> consumer) {
        return subscribe(
            "/user/" + user + "/v1/account",
            PAYLOAD_WITH_LIST_OF_BALANCES,
            consumer
        );
    }

    @Override
    public Subscription subscribeToBalanceTotals(UUID user, Consumer<List<BalanceTotal>> consumer) {
        return subscribe(
            "/user/" + user + "/v1/account/total",
            PAYLOAD_WITH_LIST_OF_BALANCE_TOTALS,
            consumer
        );
    }

    @Override
    public Subscription subscribeToTransactions(UUID user, Consumer<List<UserTransaction>> consumer) {
        return subscribe(
            "/user/" + user + "/v1/transaction",
            PAYLOAD_WITH_LIST_OF_USER_TRANSACTIONS,
            consumer
        );
    }

    @Override
    public Subscription subscribeToTransfers(UUID user, Consumer<List<UserTransfer>> consumer) {
        return subscribe(
            "/user/" + user + "/v1/transfers",
            PAYLOAD_WITH_LIST_OF_USER_TRANSFERS,
            consumer
        );
    }

    private String sign(String message) {
        logger.trace("signature string : '{}'", message);
        byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
        return HexBin.encode(this.crypto.doFinal(bytes));
//      return HexBin.encode(this.crypto.doFinal(bytes)).toLowerCase();  // todo private api still broken
    }

    private <T> Subscription subscribe(String topic, TypeReference<Payload<T>> type, Consumer<T> consumer) {
        logger.info("subscribing to {}", topic);

        StompSession.Subscription subscription = client.subscribe(topic, bytes -> {
            String jsonString = new String(bytes, StandardCharsets.UTF_8);
            logger.debug(topic + " << {}", jsonString);

            Payload<T> result;

            try {
                result = jsonMapper.readValue(jsonString, type);
            } catch (JsonProcessingException e) {
                throw new LatokenApiException("failed to parse payload : " + jsonString, e);
            }

            try {
                consumer.accept(result.getPayload());
            } catch (Exception e) {
                throw new LatokenApiException("uncaught exception from consumer while handling : " + jsonString, e);
            }
        });

        return () -> {
            logger.info("unsubscribing from {}", topic);
            subscription.unsubscribe();
        };
    }
}
