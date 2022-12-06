/*
 * Copyright (c) 2020-2021 VMware, Inc. or its affiliates, All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package reactor.netty.examples.http.helloworld;

import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServer;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpHeaderValues.TEXT_PLAIN;

/**
 * An HTTP server that expects GET request and sends back "Hello World!".
 *
 * @author Violeta Georgieva
 */
public final class HelloWorldServer {

    static final int PORT = 8080;

    public static void main(String[] args) {
        HttpServer server =
                HttpServer.create()
                        .port(PORT)
						.forwarded(true)
                        .accessLog(true)
                        .route(r -> r.get("/hello",
                                (req, res) -> res.header(CONTENT_TYPE, TEXT_PLAIN)
                                        .sendString(Mono.just(req.remoteAddress().toString()))));

        server.bindNow()
                .onDispose()
                .block();
    }
}
