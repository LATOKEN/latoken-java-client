package com.latoken.api.client.v2;


import com.fasterxml.jackson.core.type.TypeReference;
import com.latoken.api.client.v2.response.Ack;
import com.latoken.api.client.v2.response.Balance;
import com.latoken.api.client.v2.response.Binding;
import com.latoken.api.client.v2.response.Bindings;
import com.latoken.api.client.v2.response.Book;
import com.latoken.api.client.v2.response.BookChange;
import com.latoken.api.client.v2.response.Currency;
import com.latoken.api.client.v2.response.CurrencyLimit;
import com.latoken.api.client.v2.response.DepositAddressResponse;
import com.latoken.api.client.v2.response.FeeScheme;
import com.latoken.api.client.v2.response.FeeTier;
import com.latoken.api.client.v2.response.Order;
import com.latoken.api.client.v2.response.PaginatedResult;
import com.latoken.api.client.v2.response.Pair;
import com.latoken.api.client.v2.response.Payload;
import com.latoken.api.client.v2.response.Rate;
import com.latoken.api.client.v2.response.ServerTime;
import com.latoken.api.client.v2.response.Ticker;
import com.latoken.api.client.v2.response.Trade;
import com.latoken.api.client.v2.response.Transaction;
import com.latoken.api.client.v2.response.Transfer;
import com.latoken.api.client.v2.response.UserDetails;
import com.latoken.api.client.v2.response.UserTransaction;
import com.latoken.api.client.v2.response.UserTransfer;
import com.latoken.api.client.v2.response.WithdrawalResponse;

import java.util.List;
import java.util.Map;
import java.util.UUID;

final class CustomJacksonTypes {
    static final TypeReference<UserDetails> USER_DETAILS = new TypeReference<UserDetails>() {
    };
    static final TypeReference<Currency> CURRENCY = new TypeReference<Currency>() {
    };
    static final TypeReference<List<Currency>> LIST_OF_CURRENCIES = new TypeReference<List<Currency>>() {
    };
    static final TypeReference<List<UUID>> LIST_OF_IDS = new TypeReference<List<UUID>>() {
    };
    static final TypeReference<List<Balance>> LIST_OF_BALANCES = new TypeReference<List<Balance>>() {
    };
    static final TypeReference<Balance> BALANCE = new TypeReference<Balance>() {
    };
    static final TypeReference<Book> BOOK = new TypeReference<Book>() {
    };
    static final TypeReference<Map<UUID, double[]>> MAP_OF_CHARTS = new TypeReference<Map<UUID, double[]>>() {
    };
    static final TypeReference<List<Order>> LIST_OF_ORDERS = new TypeReference<List<Order>>() {
    };
    static final TypeReference<Order> ORDER = new TypeReference<Order>() {
    };
    static final TypeReference<Ack> ACK = new TypeReference<Ack>() {
    };
    static final TypeReference<List<Pair>> LIST_OF_PAIRS = new TypeReference<List<Pair>>() {
    };
    static final TypeReference<Transfer> TRANSFER = new TypeReference<Transfer>() {
    };
    static final TypeReference<List<Ticker>> LIST_OF_TICKERS = new TypeReference<List<Ticker>>() {
    };
    static final TypeReference<Ticker> TICKER = new TypeReference<Ticker>() {
    };
    static final TypeReference<ServerTime> SERVER_TIME = new TypeReference<ServerTime>() {
    };
    static final TypeReference<List<Trade>> LIST_OF_TRADES = new TypeReference<List<Trade>>() {
    };
    static final TypeReference<FeeScheme> FEE_SCHEME = new TypeReference<FeeScheme>() {
    };
    static final TypeReference<List<FeeTier>> LIST_OF_FEE_TIERS = new TypeReference<List<FeeTier>>() {
    };
    static final TypeReference<PaginatedResult<Transaction>> PAGE_OF_TRANSACTIONS = new TypeReference<PaginatedResult<Transaction>>() {
    };
    static final TypeReference<Bindings> BINDINGS = new TypeReference<Bindings>() {
    };
    static final TypeReference<List<Binding>> LIST_OF_BINDINGS = new TypeReference<List<Binding>>() {
    };
    static final TypeReference<DepositAddressResponse> DEPOSIT_ADDRESS_RESPONSE = new TypeReference<DepositAddressResponse>() {
    };
    static final TypeReference<WithdrawalResponse> WITHDRAWAL_RESPONSE = new TypeReference<WithdrawalResponse>() {
    };
    static final TypeReference<Transaction> TRANSACTION = new TypeReference<Transaction>() {
    };
    static final TypeReference<List<CurrencyLimit>> LIST_OF_CURRENCY_LIMITS = new TypeReference<List<CurrencyLimit>>() {
    };
    static final TypeReference<List<Transfer>> LIST_OF_TRANSFERS = new TypeReference<List<Transfer>>() {
    };
    static final TypeReference<Payload<List<Ticker>>> PAYLOAD_WITH_LIST_OF_TICKERS = new TypeReference<Payload<List<Ticker>>>() {
    };
    static final TypeReference<Payload<Ticker>> PAYLOAD_WITH_TICKER = new TypeReference<Payload<Ticker>>() {
    };
    static final TypeReference<Payload<List<Currency>>> PAYLOAD_WITH_LIST_OF_CURRENCIES = new TypeReference<Payload<List<Currency>>>() {
    };
    static final TypeReference<Payload<List<Pair>>> PAYLOAD_WITH_LIST_OF_PAIRS = new TypeReference<Payload<List<Pair>>>() {
    };
    static final TypeReference<Payload<List<Rate>>> PAYLOAD_WITH_LIST_OF_RATES = new TypeReference<Payload<List<Rate>>>() {
    };
    static final TypeReference<Payload<BookChange>> PAYLOAD_WITH_BOOK_CHANGES = new TypeReference<Payload<BookChange>>() {
    };
    static final TypeReference<Payload<List<Trade>>> PAYLOAD_WITH_LIST_OF_TRADES = new TypeReference<Payload<List<Trade>>>() {
    };
    static final TypeReference<Payload<List<Order>>> PAYLOAD_WITH_LIST_OF_ORDERS = new TypeReference<Payload<List<Order>>>() {
    };
    static final TypeReference<Payload<List<Balance>>> PAYLOAD_WITH_LIST_OF_BALANCES = new TypeReference<Payload<List<Balance>>>() {
    };
    static final TypeReference<Payload<List<UserTransaction>>> PAYLOAD_WITH_LIST_OF_USER_TRANSACTIONS = new TypeReference<Payload<List<UserTransaction>>>() {
    };
    static final TypeReference<Payload<List<UserTransfer>>> PAYLOAD_WITH_LIST_OF_USER_TRANSFERS = new TypeReference<Payload<List<UserTransfer>>>() {
    };
}
