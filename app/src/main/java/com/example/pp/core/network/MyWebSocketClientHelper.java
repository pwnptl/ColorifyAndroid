package com.example.pp.core.network;

import android.util.Log;

import com.example.pp.core.Constants;
import com.example.pp.core.UserManagement.UserManagementHelper;
import com.example.pp.core.messageHandler.MessageHandlerInterface;
import com.example.pp.core.messageHandler.MessageHandlerRegistry;
import com.example.pp.core.messageHandler.MessageHandlerType;
import com.example.pp.core.request.Request;
import com.example.pp.core.utility.ObjectJsonConverter;

import java.net.URI;
import java.net.URISyntaxException;

public class MyWebSocketClientHelper {
    private static MyWebSocketClientHelper myWebSocketClientHelper;
    private MyWebSocketClient myWebSocketClient;
    private MessageHandlerRegistry messageHandlerRegistry;

    private MyWebSocketClientHelper() { // private constructor for singleton
        messageHandlerRegistry = MessageHandlerRegistry.getInstance();
    }

    public static MyWebSocketClientHelper getInstance() {
        if (myWebSocketClientHelper == null) {
            myWebSocketClientHelper = new MyWebSocketClientHelper();
            myWebSocketClientHelper.createWebSocketClient();
        }
        return myWebSocketClientHelper;
    }

    private void createWebSocketClient() {
        if (myWebSocketClient == null || !myWebSocketClient.isConnected()) {
            try {
                myWebSocketClient = new MyWebSocketClient(new URI(Constants.Socket.LOCAL_SOCKET_URL));
                registerDefaultHandlers();
                connectWebSocketClient();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

    }

    private void registerDefaultHandlers() {
        // adding default unknown type Handler.
        getInstance().addHandler(MessageHandlerType.UNKNOWN, unknownMessageHandler);
        getInstance().addHandler(MessageHandlerType.DEFAULT, unknownMessageHandler);
        getInstance().addHandler(MessageHandlerType.PLAYER_SESSION_REGISTERED, new UserManagementHelper().getPlayerRegistrationHandler());
    }

    public void addHandler(MessageHandlerType type, MessageHandlerInterface handler) {
        messageHandlerRegistry.put(type, handler);
    }

    public void removeHandler(MessageHandlerType type) {
        messageHandlerRegistry.remove(type);
    }

    public void send(MessageHandlerType handlerType, Request obj) {
        Payload payload = new Payload(handlerType.name(), obj);
        String jsonPayload = ObjectJsonConverter.toJSON(payload);
        getSocketClient().send(jsonPayload);
    }

    private MyWebSocketClient getSocketClient() {
        if (myWebSocketClient == null)
            createWebSocketClient();
//        else if (!myWebSocketClient.isConnected())
//            connectWebSocketClient();
        return myWebSocketClient;
    }

    private void connectWebSocketClient() {
        myWebSocketClient.setConnectTimeout(Constants.Socket.CONNECT_TIMEOUT_MILLIS);
        myWebSocketClient.setReadTimeout(Constants.Socket.READ_TIMEOUT_MILLIS);
        myWebSocketClient.enableAutomaticReconnection(Constants.Socket.AUTOMATIC_RECONNECTION_TIMEOUT_MILLIS);
        myWebSocketClient.connect();
    }

    private final MessageHandlerInterface unknownMessageHandler = new MessageHandlerInterface() {
        @Override
        public void handleMessage(String message) {
            Log.w(this.getClass().getName(), "Unknown Message Type detected for message : " + message);
        }
    };
}
