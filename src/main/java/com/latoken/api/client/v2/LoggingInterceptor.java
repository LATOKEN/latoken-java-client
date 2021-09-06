package com.latoken.api.client.v2;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

final class LoggingInterceptor implements Interceptor {
    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        long time = System.currentTimeMillis();
        logger.debug("--> {} {}", request.method(), request.url());
        RequestBody requestBody = request.body();
        if (requestBody != null) {
            Buffer requestBuffer = new Buffer();
            requestBody.writeTo(requestBuffer);
            String content = requestBuffer.readUtf8();
            if (!content.isEmpty()) {
                logger.debug("--> {}", content);
            }
        }

        Response response = chain.proceed(request);

        time = System.currentTimeMillis() - time;
        logger.debug("{} <-- {} {} ({}ms)", response.code(), request.method(), request.url(), time);

        ResponseBody responseBody = response.body();
        if (responseBody != null) {
            BufferedSource buffer = responseBody.source();
            if (!buffer.exhausted()) {
                String content = buffer.readUtf8();
                if (!content.isEmpty()) {
                    logger.debug("<-- {}", content);
                }

                return response
                    .newBuilder()
                    .body(ResponseBody.create(content.getBytes(StandardCharsets.UTF_8), responseBody.contentType()))
                    .build();
            }
        }

        return response;
    }
}
