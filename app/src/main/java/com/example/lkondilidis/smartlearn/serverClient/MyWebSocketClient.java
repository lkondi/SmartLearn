package com.example.lkondilidis.smartlearn.serverClient;


import com.example.lkondilidis.smartlearn.services.ServerService;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class MyWebSocketClient extends WebSocketClient {

    private ServerService serverService;

    public MyWebSocketClient(URI uri, ServerService serverService){
        super(uri);
        this.serverService = serverService;
    }


    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println( "opened connection" );
    }

    @Override
    public void onMessage(final String message) {
        System.out.println("message will be handled");
        serverService.notifyMain(message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println( "closed connection" );
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }
}
