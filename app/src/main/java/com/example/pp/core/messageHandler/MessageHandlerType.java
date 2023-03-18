package com.example.pp.core.messageHandler;

public enum MessageHandlerType {

    // Requests -> should not require handler if explicit Response type is present. todo : simplify this incompetence.
    GET_PLAYER_DATA("GET_PLAYER_DATA"),


    // Responses
    PLAYER_DATA("PLAYER_DATA"),


    // Misc
    START_BUTTON_MESSAGE_HANDLER("DATA_HANDLER"),
    UNKNOWN("IDK"),
    ;

    /*
    * Value of the enum.
    * */
    private final String value;

    MessageHandlerType(String value) {

        this.value = value;
    }

    public static MessageHandlerType getValue(String type) {
        for (MessageHandlerType t : values())
            if (t.value.equalsIgnoreCase(type)) return t;
        throw new IllegalArgumentException("unknown enum type : " + type);
    }
}
