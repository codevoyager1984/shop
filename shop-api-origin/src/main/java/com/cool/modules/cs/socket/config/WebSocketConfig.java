//package com.cool.modules.cs.socket.config;
//import java.net.URI;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.reactive.config.EnableWebFlux;
//import org.springframework.web.reactive.config.WebFluxConfigurer;
//import org.springframework.web.reactive.socket.WebSocketHandler;
//import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
//import org.springframework.web.servlet.function.HandlerFunction;
//import org.springframework.web.servlet.function.RequestPredicates;
//import org.springframework.web.servlet.function.RouterFunction;
//import org.springframework.web.servlet.function.RouterFunctions;
//import org.springframework.web.servlet.function.ServerResponse;
//
//@Configuration
//@EnableWebFlux
//public class WebSocketConfig implements WebFluxConfigurer {
//
//    @Bean
//    public WebSocketHandlerAdapter handlerAdapter() {
//        return new WebSocketHandlerAdapter();
//    }
//
//    @Bean
//    public RouterFunction<ServerResponse> route() {
//        URI targetUri = URI.create("ws://127.0.0.1:9092/socket.io"); // 目标 WebSocket 服务器
//        WebSocketHandler handler = new ProxyWebSocketHandler(targetUri);
//
//        return RouterFunctions
//            .route(RequestPredicates.GET("/socket.io/**"),
//                (HandlerFunction<ServerResponse>) handler);
//    }
//}