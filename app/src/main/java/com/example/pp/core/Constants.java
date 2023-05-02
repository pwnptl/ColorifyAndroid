package com.example.pp.core;

public final class Constants {

    public static final class GAME {
        public static final String ID = "GAME_ID";
    }

    public static final class SHARED_PREFERENCE {
        public static final String NAME = "COLORIFY_SHARED_PREFERENCE_NAME";
        public static final String USER_ID = "USER_ID";
    }

    public static final class Socket {
        public static final String LOCAL_SOCKET_URL = "ws://192.168.29.210:8080/myHandler";
        public static final long AUTOMATIC_RECONNECTION_TIMEOUT_MILLIS = 10000;
        public static final int READ_TIMEOUT_MILLIS = 60000;
        public static final int CONNECT_TIMEOUT_MILLIS = 10000;
    }

    public static final class APIs {
        public final String hello = "hello";
    }
}
