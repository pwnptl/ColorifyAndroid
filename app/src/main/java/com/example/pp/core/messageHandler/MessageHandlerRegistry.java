package com.example.pp.core.messageHandler;

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
        messageHandlers.put(type, messageHandler);
    }

    public MessageHandlerInterface get(MessageHandlerType handlerType) {
        return messageHandlers.get(handlerType);
    }
}
