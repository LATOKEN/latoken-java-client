package com.latoken.api.client.v3;

import com.latoken.api.client.v2.AsyncRestV2Client;
import com.latoken.api.client.v2.AsyncRestV2ClientImpl;
import com.latoken.api.client.v2.request.PlaceOrder;
import com.latoken.api.client.v2.response.Ack;
import com.latoken.api.client.v2.response.Currency;
import com.latoken.api.client.v2.response.Pair;
import com.latoken.api.client.v3.request.CreateOrderReq;
import com.latoken.api.client.v3.response.Asset;
import com.latoken.api.client.v3.response.CancelOrderRep;
import com.latoken.api.client.v3.response.CreateOrderRep;
import com.latoken.api.client.v3.response.Market;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static java.util.Objects.requireNonNull;

public class AsyncRestV3ClientImpl implements AsyncRestV3Client {
    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncRestV2ClientImpl.class);

    private final AsyncRestV2Client v2;

    private final HashMap<String, UUID> symbolToUuid = new HashMap<>();
    private final HashMap<UUID, String> uuidToSymbol = new HashMap<>();
    private final HashMap<String, com.latoken.api.client.v2.response.Pair> pairInfo = new HashMap<>();

    public AsyncRestV3ClientImpl(AsyncRestV2Client asyncClientV2) throws LatokenApiException {
        this.v2 = asyncClientV2;

        LOGGER.debug("preloading currencies");
        try {
            v2.activeCurrencies().get().forEach(currency -> {
                symbolToUuid.put(currency.getTag(), currency.getId());
                uuidToSymbol.put(currency.getId(), currency.getTag());
            });
        } catch (Exception e) {
            LOGGER.error("failed to load existing currencies", e);
            throw new LatokenApiException(e);
        }

        LOGGER.debug("preloading markets");
        try {
            v2.getActivePairs().get().forEach(pair -> {
                pairInfo.put(symbol(pair.getBaseCurrency()) + "/" + symbol(pair.getQuoteCurrency()), pair);
            });
        } catch (Exception e) {
            LOGGER.error("failed to load existing markets", e);
            throw new LatokenApiException(e);
        }
    }

    @Override
    public CompletableFuture<List<Asset>> getAssets() {
        return v2.availableCurrencies()
            .thenApply(this::convertToAssets);
    }

    @Override
    public CompletableFuture<List<Market>> getMarkets() {
        return v2.getAvailablePairs()
            .thenApply(this::convertToMarkets);
    }

    @Override
    public CompletableFuture<CreateOrderRep> createOrder(CreateOrderReq request) {
        return v2.placeOrder(
            id(request.getBase()),
            id(request.getQuote()),
            PlaceOrder.Side.valueOf(request.getSide().toString()),
            PlaceOrder.Condition.valueOf(request.getTimeInForce().toString()),
            PlaceOrder.Type.valueOf(request.getType().toString()),
            "",
            request.getPrice(),
            request.getAmount(),
            System.currentTimeMillis()
        ).thenApply(ack -> {
            if(ack.getStatus() == Ack.Status.FAILURE){
                throw new LatokenApiException("failed to place order : "+ack.getMessage());
            }
            return new CreateOrderRep(ack.getId());
        });
    }

    @Override
    public CompletableFuture<CancelOrderRep> cancelOrder(UUID id) {
        return null;
    }


    private String symbol(UUID id) {
        return requireNonNull(uuidToSymbol.get(id), () -> "failed to resolve symbol of " + id);
    }

    private UUID id(String symbol) {
        return requireNonNull(symbolToUuid.get(symbol), () -> "failed to resolve id of " + symbol);
    }

    private List<Market> convertToMarkets(List<Pair> pairs) {

        return null;
    }

    private List<Asset> convertToAssets(List<Currency> currencies) {
        return null;
    }
}
