package com.zsc.modules.ws.outbound;

/**
 * TODO
 */
public interface WsOutboundHandlerService {

    void afterConnectionEstablished();

    boolean handlerMessage();

    void afterConnectionClosed();
}
