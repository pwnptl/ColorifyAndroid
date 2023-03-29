package com.example.pp.core.messageHandler;

import lombok.Getter;

public enum MessageHandlerType {

    // Requests -> should not require handler if explicit Response type is present. todo : simplify this incompetence.
    GET_PLAYER_DATA("GET_PLAYER_DATA"),
    CREATE_PLAYER("CREATE_PLAYER"),
    REGISTER_PLAYER_SESSION("REGISTER_PLAYER_SESSION"),
    CREATE_GAME("CREATE_GAME"),
    JOIN_GAME("JOIN_GAME"),
    SYN("SYN"),

    // Responses
    PLAYER_DATA("PLAYER_DATA"),
    PLAYER_CREATED("PLAYER_CREATED"),
    PLAYER_SESSION_REGISTERED("PLAYER_SESSION_REGISTERED"),
    GAME_CREATED("GAME_CREATED"),
    GAME_JOINED("GAME_JOINED"),
    ACK("ACK"),

    GAME_READY("GAME_READY"),

    // Misc
    START_BUTTON_MESSAGE_HANDLER("START_BUTTON_MESSAGE_HANDLER"),
    UNKNOWN("UNKNOWN"),
    DEFAULT("DEFAULT"),
    ;

    /*
     * Value of the enum.
     * */
    @Getter
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
