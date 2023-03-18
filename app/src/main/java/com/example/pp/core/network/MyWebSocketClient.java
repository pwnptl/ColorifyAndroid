package com.example.pp.core.network;

import android.util.Log;

import com.example.pp.core.messageHandler.MessageHandlerInterface;
import com.example.pp.core.messageHandler.MessageHandlerRegistry;
import com.example.pp.core.messageHandler.MessageHandlerType;
import com.example.pp.core.utility.ObjectJsonConverter;

import java.net.URI;
import java.util.Objects;

import tech.gusavila92.websocketclient.WebSocketClient;

class MyWebSocketClient extends WebSocketClient {
    // todo: this class should be singleton instead of its helper. For now this class is package private.
    private MessageHandlerRegistry messageHandlerRegistry;

    /**
     * Initialize all the variables
     *
     * @param uri URI of the WebSocket server
     */
    public MyWebSocketClient(URI uri) {
        super(uri);
        messageHandlerRegistry = MessageHandlerRegistry.getInstance();
    }

    @Override
    public void onOpen() {

        Log.i("WebSocket", "Session is starting");
        this.send("Hello World!");
    }

    @Override
    public void onTextReceived(String message) {
        Log.i("WebSocket", "Message received " + message);
        // todo : get type & data from message
        if (ObjectJsonConverter.isJson(message)) {
            MessageHandlerType type = ObjectJsonConverter.getMessageType(message);

            Log.i("WebSocket", "Message received is a json " + message + " and type " + type);
            Objects.requireNonNull(this.messageHandlerRegistry.get(type))
                    .handleMessage(message);
        } else {
            this.messageHandlerRegistry.get(MessageHandlerType.GET_PLAYER_DATA)
                    .handleMessage(message);
        }
    }

    @Override
    public void onBinaryReceived(byte[] data) {

    }

    @Override
    public void onPingReceived(byte[] data) {

    }

    @Override
    public void onPongReceived(byte[] data) {

    }

    @Override
    public void onException(Exception e) {
        System.out.println(e.getMessage());

    }

    @Override
    public void onCloseReceived() {
        Log.i("WebSocket", "Closed ");
        System.out.println("onCloseReceived");
    }

    public void addHandler(MessageHandlerType type, MessageHandlerInterface messageHandler) {
        this.messageHandlerRegistry.put(type, messageHandler);
    }

}
