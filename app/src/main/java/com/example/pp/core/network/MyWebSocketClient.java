package com.example.pp.core.network;

import android.util.Log;

import com.example.pp.core.messageHandler.MessageHandlerInterface;
import com.example.pp.core.messageHandler.MessageHandlerRegistry;
import com.example.pp.core.messageHandler.MessageHandlerType;

import java.net.URI;
import java.util.Objects;

import tech.gusavila92.websocketclient.WebSocketClient;

public class MyWebSocketClient extends WebSocketClient {

    MessageHandlerRegistry messageHandlerRegistry;

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
        Log.i("WebSocket", "Message received");
        // todo : get type & data from message
        Objects.requireNonNull(this.messageHandlerRegistry.get(MessageHandlerType.START_BUTTON_MESSAGE_HANDLER))
                .handleMessage(message);
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
