package com.latoken.api.client.v3;

import com.latoken.api.client.v2.WebsocketV2Client;

public class WebsocketV3ClientImpl implements WebsocketV3Client {
    private final WebsocketV2Client v2;

    public WebsocketV3ClientImpl(WebsocketV2Client websocketV2Client) {
        this.v2 = websocketV2Client;
    }
}
