package com.latoken.api.client.v2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSession.Subscription;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;
import java.lang.reflect.Type;
import java.util.concurrent.ThreadFactory;
import java.util.function.Consumer;

import static java.util.Collections.singletonList;

final class SpringStompClient {
    final static Logger logger = LoggerFactory.getLogger(SpringStompClient.class);

    String url;
    WebSocketStompClient stompClient;
    StompSession session;

    SpringStompClient(String url, ThreadFactory threadFactory) {
        this.url = url;

        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        container.setDefaultMaxTextMessageBufferSize(Integer.MAX_VALUE);
        Transport transport = new WebSocketTransport(new StandardWebSocketClient(container));

        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(1);
        taskScheduler.setThreadFactory(threadFactory);
        taskScheduler.initialize();

        this.stompClient = new WebSocketStompClient(new SockJsClient(singletonList(transport)));
        stompClient.setTaskScheduler(taskScheduler);
        stompClient.setDefaultHeartbeat(new long[]{10000, 10000});
        stompClient.setInboundMessageSizeLimit(Integer.MAX_VALUE);
    }

    void connect() throws Exception {
        this.connect(new WebSocketHttpHeaders());
    }

    void connect(WebSocketHttpHeaders headers) throws Exception {
        ListenableFuture<StompSession> session = stompClient.connect(url, headers, new StompSessionHandler() {
            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                logger.info("connected {}", session.getSessionId());
            }

            @Override
            public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
                logger.error("unhandled exception", exception);
            }

            @Override
            public void handleTransportError(StompSession session, Throwable exception) {
                logger.error("unhandled exception", exception);
            }

            @Override
            public Type getPayloadType(StompHeaders headers) {
                return byte[].class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
            }
        });

        this.session = session.get();
    }

    void stop() {
        logger.info("disconnecting {}", session.getSessionId());
        stompClient.stop();
    }

    Subscription subscribe(String topic, Consumer<byte[]> onMessage) {
        StompHeaders headers = new StompHeaders();
        headers.setDestination(topic);
        return this.subscribe(headers, onMessage);
    }

    Subscription subscribe(StompHeaders headers, Consumer<byte[]> onMessage) {
        logger.debug("subscribing to {}", headers.getFirst("destination"));


        return session.subscribe(headers, new StompFrameHandler() {
            public Type getPayloadType(StompHeaders stompHeaders) {
                return byte[].class;
            }

            public void handleFrame(StompHeaders stompHeaders, Object o) {
                byte[] bytes = (byte[]) o;
                logger.debug("received frame {} bytes", bytes.length);
                onMessage.accept(bytes);
            }
        });
    }
}
