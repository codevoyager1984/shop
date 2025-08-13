//package com.cool.modules.cs.socket.config;
//import java.net.URI;
//import org.springframework.web.reactive.socket.WebSocketHandler;
//import org.springframework.web.reactive.socket.WebSocketMessage;
//import org.springframework.web.reactive.socket.WebSocketSession;
//import reactor.core.publisher.Mono;
//import reactor.netty.http.client.HttpClient;
//
//public class ProxyWebSocketHandler implements WebSocketHandler {
//
//    private final URI targetUri;
//
//    public ProxyWebSocketHandler(URI targetUri) {
//        this.targetUri = targetUri;
//    }
//
//    @Override
//    public Mono<Void> handle(WebSocketSession session) {
//        HttpClient client = HttpClient.create();
//
//        return client
//            .websocket()
//            .uri(targetUri)
//            .handle((inbound, outbound) -> {
//                // 转发客户端消息到目标 WebSocket 服务器
//                Mono<Void> outboundMono = session.receive()
//                    .map(WebSocketMessage::getPayloadAsText)
//                    .flatMap(msg -> outbound.sendString(Mono.just(msg)))
//                    .then();
//
//                // 转发目标 WebSocket 服务器的消息到客户端
//                Mono<Void> inboundMono = inbound.receive()
//                    .asString()
//                    .flatMap(message -> session.send(Mono.just(session.textMessage(message))))
//                    .then();
//
//                return outboundMono.and(inboundMono);
//            }).then();
//    }
//}
//
//
//
