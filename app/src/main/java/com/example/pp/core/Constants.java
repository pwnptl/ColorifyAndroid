package com.example.pp.core;

public final class Constants {

    public static final class GAME {
        public static final String ID = "GAME_ID";
        public static final String GameResponse = "GameResponse";
    }

    public static final class SHARED_PREFERENCE {
        public static final String NAME = "COLORIFY_SHARED_PREFERENCE_NAME";
        public static final String USER_ID = "USER_ID";
        public static final String SERVER_SUBDOMAIN = "SERVER_SUBDOMAIN";
    }

    public static final class Socket {
        public static final String PUBLIC_SERVEO_DOMAIN = "tuli.serveo.net";
        public static final String LOCAL_DOMAIN = "192.168.29.14:8080";
        private static final String SERVER_URL_PREFIX = "ws://";
        private static final String SERVER_URL_POSTFIX = "/myHandler";
        public static final String SERVER_URL_DOMAIN = ".serveo.net";
        public static final String SERVER_PING_URL_PREFIX = "https://";
        public static final String SERVER_PING_URL_POSTFIX = "/ping";

        public static final String LOCAL_SOCKET_URL = SERVER_URL_PREFIX + PUBLIC_SERVEO_DOMAIN + SERVER_URL_POSTFIX;

        public static final long AUTOMATIC_RECONNECTION_TIMEOUT_MILLIS = 10000;
        public static final int READ_TIMEOUT_MILLIS = 60000;
        public static final int CONNECT_TIMEOUT_MILLIS = 10000;

        public static String getServeoPingUrl(String subDomain)
        {

            assert subDomain != null;
            assert subDomain.length() > 0;

            return SERVER_PING_URL_PREFIX + subDomain + SERVER_URL_DOMAIN + SERVER_PING_URL_POSTFIX;
        }

        public static String getServeoUrl(String subDomain) {
            assert subDomain != null;
            assert subDomain.length() > 0;

            return SERVER_URL_PREFIX + subDomain + SERVER_URL_DOMAIN + SERVER_URL_POSTFIX;
        }
    }

    public static final class APIs {
        public final String hello = "hello";
    }
}
