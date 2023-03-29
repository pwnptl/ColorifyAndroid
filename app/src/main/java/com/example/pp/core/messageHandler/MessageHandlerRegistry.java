package com.example.pp.core.messageHandler;

import android.util.Log;

import java.util.HashMap;

public class MessageHandlerRegistry {
    private static MessageHandlerRegistry messageHandlerRegistry;

    private MessageHandlerRegistry() {
        messageHandlers = new HashMap<>();
    }

    private HashMap<MessageHandlerType, MessageHandlerInterface> messageHandlers;

    public static MessageHandlerRegistry getInstance() {
        if (messageHandlerRegistry == null)
            messageHandlerRegistry = new MessageHandlerRegistry();
        return messageHandlerRegistry;
    }

    public void put(MessageHandlerType type, MessageHandlerInterface messageHandler) {
        if (messageHandlers.containsKey(type))
            Log.w(MessageHandlerRegistry.class.getName(), "messageHandler already exist : " + type);
        else
            messageHandlers.put(type, messageHandler);
    }

    public MessageHandlerInterface get(MessageHandlerType handlerType) {
        if (messageHandlers.containsKey(handlerType))
            return messageHandlers.get(handlerType);
        else
            throw new IllegalArgumentException("No handler Found for type " + handlerType);
    }

    public void remove(MessageHandlerType type) {
        if (messageHandlers.containsKey(type))
            messageHandlers.remove(type);
        else
            Log.w(MessageHandlerRegistry.class.getName(), "messageHandler did not exist : " + type);

    }
}
