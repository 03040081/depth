package com.zsc.modules.transport.ws.outbound;

/**
 * TODO
 */
public interface WsOutboundHandlerService {

    void afterConnectionEstablished();

    boolean handlerMessage();

    void afterConnectionClosed();
}
