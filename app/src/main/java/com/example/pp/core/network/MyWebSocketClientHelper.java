package com.example.pp.core.network;

import com.example.pp.core.Constants;
import com.example.pp.core.messageHandler.MessageHandlerInterface;
import com.example.pp.core.messageHandler.MessageHandlerType;

import java.net.URI;
import java.net.URISyntaxException;

public class MyWebSocketClientHelper {

    private MyWebSocketClient myWebSocketClient;

    public void createWebSocketClient2() {
        try {
            myWebSocketClient = new MyWebSocketClient(new URI(Constants.Socket.socketURL));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        myWebSocketClient.setConnectTimeout(10000);
        myWebSocketClient.setReadTimeout(60000);
        myWebSocketClient.enableAutomaticReconnection(10000);
        myWebSocketClient.connect();
    }

    public void addHandler(MessageHandlerInterface handler)
    {
        myWebSocketClient.addHandler(MessageHandlerType.START_BUTTON_MESSAGE_HANDLER, handler);
    }

    public void send(MessageHandlerType handlerType, String message) {
        // todo : addHandlerType in message.
        myWebSocketClient.send(message);
    }
}
