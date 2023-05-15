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

import lombok.Setter;

public class MyWebSocketClientHelper {
    private static MyWebSocketClientHelper myWebSocketClientHelper;
    @Setter
    private static String subDomain = null; // temporary subDomain until the Server have a rigid domain.
    private MyWebSocketClient myWebSocketClient;
    private final MessageHandlerRegistry messageHandlerRegistry;

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
                if (myWebSocketClient == null) {
                    Log.i(MyWebSocketClientHelper.class.getName(), "client was null");
                    myWebSocketClient = new MyWebSocketClient(new URI(Constants.Socket.getServeoUrl(subDomain)));
                }
                Log.i(MyWebSocketClientHelper.class.getName(),  "isConnected " + myWebSocketClient.isConnected());
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
