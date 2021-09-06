package com.latoken.api.client.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.LoggerFactory;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class Latoken {

    public static AsyncRestV2Client asyncClientV2(
        String key,
        String sec
    ) {
        return asyncClientV2(key, sec, defaultObjectMapper(), defaultHttpClient());
    }

    public static AsyncRestV2Client asyncClientV2(
        @Nullable String key,
        @Nullable String sec,
        @NotNull ObjectMapper customObjectMapper,
        @NotNull OkHttpClient customOkHttpClient
    ) {
        return new AsyncRestV2ClientImpl(key, sec, customObjectMapper, customOkHttpClient);
    }


    public static WebsocketV2Client websocketV2Client(
        @Nullable String key,
        @Nullable String sec
    ) {
        return new WebsocketV2ClientImpl(key, sec, defaultObjectMapper(), daemonThreadFactory());
    }

    public static WebsocketV2Client websocketV2Client(
        @Nullable String key,
        @Nullable String sec,
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
}
