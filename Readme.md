# Java client library for LAtoken exchange

Java library for interacting with [Latoken API](https://api.latoken.com/doc), providing complete API coverage, and supporting synchronous and asynchronous requests, as well as event streaming via WebSockets.

## Installation

For maven add the following to your `pom.xml`

```
<dependency>
    <groupId>com.latoken</groupId>
    <artifactId>latoken-java-client</artifactId>
    <version>1.0.0</version>
</dependency>
```

for gradle simply add this to your dependencies section

```
implementation("com.squareup.okhttp3:okhttp:4.9.0")
```

## How to use

There are many examples available in `/src/test/java`, however your entry point is always static class `Latoken` which will have methods to create both `REST` and `Websocket` clients.

All threads created by this library are `daemon` threads.

Logging is done via `slf4j`, if you use it your existing logging implementation will be used within the library.

Before starting to use the library make sure to [generate your api keys](https://latoken.com/account/apikeys).

```Java
// for REST v2 api simply do
Latoken.asyncClientV2("your-key", "your-secret")
// for Websocket api
Latoken.websocketV2Client("your-key", "your-secret");
```
After that api is quite straight forward. All async rest calls return `CompletableFuture<T>` and all websocket calls take a subscriber/consumer `Consumer<T>` and return a `Subscription` which you can use to unsubscribe from topic.

To gracefully shutdown websocket client make sure to call `.stop()`.

Note that api that is marked `@Experimental` may change with future versions, we seek community feedback before finalizing the api, so please don't hesitate to engage with the project via [Github Issues](https://github.com/LATOKEN/latoken-java-client/issues). We will be actively maintaining this project to provide the best possible api experience for our customers.


