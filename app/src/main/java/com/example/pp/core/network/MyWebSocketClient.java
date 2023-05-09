package com.example.pp.core.network;

import android.util.Log;

import com.example.pp.core.UserManagement.UserManagementHelper;
import com.example.pp.core.messageHandler.MessageHandlerRegistry;
import com.example.pp.core.messageHandler.MessageHandlerType;
import com.example.pp.core.utility.ObjectJsonConverter;

import java.net.URI;
import java.util.Objects;

import kotlin.NotImplementedError;
import lombok.Getter;
import tech.gusavila92.websocketclient.WebSocketClient;

class MyWebSocketClient extends WebSocketClient {
    // todo: this class should be singleton instead of its helper. For now this class is package private.
    private MessageHandlerRegistry messageHandlerRegistry;

    @Getter
    private boolean isConnected;

    /**
     * Initialize all the variables
     *
     * @param uri URI of the WebSocket server
     */
    public MyWebSocketClient(URI uri) {
        super(uri);
        isConnected = false;
        messageHandlerRegistry = MessageHandlerRegistry.getInstance();
    }

    @Override
    public void onOpen() {
        isConnected = true;
        Log.i("WebSocket", "Session is starting");
        // send UserId to the server after Connecting to the Socket. todo: there should be a better way to support this.
        new UserManagementHelper().syncUserWithServer();
    }

    @Override
    public void onTextReceived(String message) {
        // todo : get type & data from message
        if (ObjectJsonConverter.isJson(message)) {
            Payload payload = Payload.fromJson(message);
            MessageHandlerType type = MessageHandlerType.valueOf(payload.getMessageType());
            Log.i("WebSocket", "Message received is a json " + message + " and type " + type);
            Objects.requireNonNull(this.messageHandlerRegistry.get(type))
                    .handleMessage(payload.getMessageData());
        } else {
            this.messageHandlerRegistry.get(MessageHandlerType.GET_PLAYER_DATA)
                    .handleMessage(message);
        }
    }

    @Override
    public void onBinaryReceived(byte[] data) {
        throw new NotImplementedError("not implemented");
    }

    @Override
    public void onPingReceived(byte[] data) {
        throw new NotImplementedError("not implemented");
    }

    @Override
    public void onPongReceived(byte[] data) {
        throw new NotImplementedError("not implemented");

    }

    @Override
    public void onException(Exception e) {
        Log.e(MyWebSocketClient.class.getName(), e.getMessage());
        System.out.println(e.getMessage());
    }

    @Override
    public void onCloseReceived() {
        Log.i("WebSocket", "Closed ");
        System.out.println("onCloseReceived");
        isConnected = false;
    }
}
