package com.example.pp.core.network;

import com.example.pp.core.Constants;
import com.example.pp.core.messageHandler.MessageHandlerInterface;
import com.example.pp.core.messageHandler.MessageHandlerType;

import java.net.URI;
import java.net.URISyntaxException;

public class MyWebSocketClientHelper {
    private static MyWebSocketClientHelper myWebSocketClientHelper;
    private MyWebSocketClient myWebSocketClient;

    private MyWebSocketClientHelper()
    {}

    public static MyWebSocketClientHelper getInstance(){
        if(myWebSocketClientHelper == null)
            myWebSocketClientHelper = new MyWebSocketClientHelper();
        return myWebSocketClientHelper;
    }
    public void createWebSocketClient() {
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

    public void addHandler(MessageHandlerType type, MessageHandlerInterface handler)
    {
        myWebSocketClient.addHandler(type, handler);
    }

    public void send(MessageHandlerType handlerType, String message) {
        // todo : addHandlerType in message.
        myWebSocketClient.send(message);
    }
}
