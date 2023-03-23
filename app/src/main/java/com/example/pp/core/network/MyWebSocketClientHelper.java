package com.example.pp.core.network;

import android.util.Log;

import com.example.pp.core.Constants;
import com.example.pp.core.messageHandler.MessageHandlerInterface;
import com.example.pp.core.messageHandler.MessageHandlerType;
import com.example.pp.core.utility.ObjectJsonConverter;

import java.net.URI;
import java.net.URISyntaxException;

public class MyWebSocketClientHelper {
    private static MyWebSocketClientHelper myWebSocketClientHelper;
    private MyWebSocketClient myWebSocketClient;

    private MyWebSocketClientHelper() {
    }

    public static MyWebSocketClientHelper getInstance() {
        if (myWebSocketClientHelper == null)
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

        // adding default unknown type Handler.
        myWebSocketClientHelper.addHandler(MessageHandlerType.UNKNOWN, unknownMessageHandler);
    }

    public void addHandler(MessageHandlerType type, MessageHandlerInterface handler) {
        myWebSocketClient.addHandler(type, handler);
    }

    public void send(MessageHandlerType handlerType, Object obj) {
        Payload payload = new Payload(handlerType.name(), obj);
        String jsonPayload = ObjectJsonConverter.toJSON(payload);
        myWebSocketClient.send(jsonPayload);
    }

    private final MessageHandlerInterface unknownMessageHandler = new MessageHandlerInterface() {
        @Override
        public void handleMessage(String message) {
            Log.i(this.getClass().getName(), "Unknown Message Type detected for message : " + message);
        }
    };
}
