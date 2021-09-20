package com.latoken.api.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.latoken.api.client.v2.AsyncRestV2Client;
import com.latoken.api.client.v2.AsyncRestV2ClientImpl;
import com.latoken.api.client.v2.WebsocketV2Client;
import com.latoken.api.client.v2.WebsocketV2ClientImpl;
import com.latoken.api.client.v3.AsyncRestV3Client;
import com.latoken.api.client.v3.AsyncRestV3ClientImpl;
import com.latoken.api.client.v3.WebsocketV3Client;
import com.latoken.api.client.v3.WebsocketV3ClientImpl;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import org.slf4j.LoggerFactory;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class Latoken {

    public static AsyncRestV2Client asyncClientV2(
        @NotNull String key,
        @NotNull String sec
    ) {
        return asyncClientV2(key, sec, defaultObjectMapper(), defaultHttpClient());
    }

    public static AsyncRestV2Client asyncClientV2(
        @NotNull String key,
        @NotNull String sec,
        @NotNull ObjectMapper customObjectMapper,
        @NotNull OkHttpClient customOkHttpClient
    ) {
        return new AsyncRestV2ClientImpl(key, sec, customObjectMapper, customOkHttpClient);
    }


    public static WebsocketV2Client websocketV2Client(
        @NotNull String key,
        @NotNull String sec
    ) {
        return new WebsocketV2ClientImpl(key, sec, defaultObjectMapper(), daemonThreadFactory());
    }

    public static WebsocketV2Client websocketV2Client(
        @NotNull String key,
        @NotNull String sec,
        @NotNull ObjectMapper customObjectMapper,
        @NotNull ThreadFactory customThreadFactory
    ) {
        return new WebsocketV2ClientImpl(key, sec, customObjectMapper, customThreadFactory);
    }

    private static OkHttpClient defaultHttpClient() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(0,
            Integer.MAX_VALUE,
            60,
            TimeUnit.SECONDS,
            new SynchronousQueue(),
            // Don't create non daemon threads especially if you are library maintainer...
            daemonThreadFactory()
        );

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
            .dispatcher(new Dispatcher(executor));

        if (LoggerFactory.getLogger(LoggingInterceptor.class).isDebugEnabled()) {
            builder.addInterceptor(new LoggingInterceptor());
        }

        return builder.build();
    }

    private static ThreadFactory daemonThreadFactory() {
        return runnable -> {
            Thread thread = new Thread(runnable);
            thread.setDaemon(true);
            return thread;
        };
    }

    private static ObjectMapper defaultObjectMapper() {
        return new ObjectMapper();
    }




    public static AsyncRestV3Client asyncClientV3(
        @NotNull String key,
        @NotNull String sec
    ) {
        return new AsyncRestV3ClientImpl(asyncClientV2(key, sec));
    }


    public static WebsocketV3Client websocketV3Client(
        @NotNull String key,
        @NotNull String sec
    ) {
        return new WebsocketV3ClientImpl(websocketV2Client(key, sec));
    }
}
