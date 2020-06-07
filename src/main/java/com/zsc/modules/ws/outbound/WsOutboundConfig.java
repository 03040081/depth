package com.zsc.modules.ws.outbound;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WsOutboundConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        WsOutboundHandler handler = wsOutboundHandler();
        WsOutboundHandshakeInterceptor interceptor = interceptor();

        registry.addHandler(handler, "/ws").addInterceptors(interceptor).setAllowedOrigins("*");

        registry.addHandler(handler, "/socketjs/ws").addInterceptors(interceptor).withSockJS();
    }

    @Bean
    public WsOutboundHandler wsOutboundHandler() {
        return new WsOutboundHandler();
    }

    @Bean
    public WsOutboundHandshakeInterceptor interceptor() {
        return new WsOutboundHandshakeInterceptor();
    }


}
