package com.latoken.api.client.v3;


import com.latoken.api.client.Experimental;
import com.latoken.api.client.v3.request.CreateOrderReq;
import com.latoken.api.client.v3.response.Asset;
import com.latoken.api.client.v3.response.CancelOrderRep;
import com.latoken.api.client.v3.response.CreateOrderRep;
import com.latoken.api.client.v3.response.Market;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;


/**
 * All api methods either successfully return declared result
 * or throw LatokenApiException with details of the cause,
 * which include timeouts, transport errors, parsing and other
 * errors within library code
 */
@Experimental
public interface AsyncRestV3Client {

    /**
     * List of all assets traded on the exchange
     */
    CompletableFuture<List<Asset>> getAssets();

    /**
     * List of all markets and their details
     */
    CompletableFuture<List<Market>> getMarkets();

    CompletableFuture<CreateOrderRep> createOrder(CreateOrderReq request);

    default CompletableFuture<CreateOrderRep> createOrder(
        @NotNull String base,
        @NotNull String quote,
        @NotNull CreateOrderReq.Type type,
        @NotNull CreateOrderReq.Side side,
        @NotNull Double price,
        @NotNull Double amount,
        @NotNull CreateOrderReq.TimeInForce timeInForce
    ){
        return createOrder(new CreateOrderReq(
            base,
            quote,
            type,
            side,
            price,
            amount,
            timeInForce
        ));
    }

    /**
     * Request order to be cancelled, depending on order state this can result in
     *
     * CANCELLED - order was fully cancelled
     * PARTIALLY_CANCELED - order was partially filled and the rest was cancelled
     * FILLED - order was not cancelled because it was filled already
     */
    CompletableFuture<CancelOrderRep> cancelOrder(UUID id);



}
