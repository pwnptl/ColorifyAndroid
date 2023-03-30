package com.example.pp.core.UserManagement;

import android.util.Log;

import com.example.pp.core.messageHandler.MessageHandlerInterface;
import com.example.pp.core.messageHandler.MessageHandlerType;
import com.example.pp.core.network.MyWebSocketClientHelper;
import com.example.pp.core.request.RegisterPlayerSessionRequest;
import com.example.pp.core.response.RegisterPlayerSessionResponse;
import com.example.pp.core.utility.ObjectJsonConverter;

import lombok.Getter;

public class UserManagementHelper {
    public void syncUserWithServer() {
        if (UserManager.getInstance().hasUserId()) {
            RegisterPlayerSessionRequest request = new RegisterPlayerSessionRequest(UserManager.getInstance().getUserId());
            MyWebSocketClientHelper.getInstance().send(MessageHandlerType.REGISTER_PLAYER_SESSION, request);
        }
    }

    @Getter
    final MessageHandlerInterface playerRegistrationHandler = new MessageHandlerInterface() {
        @Override
        public void handleMessage(String message) {
            RegisterPlayerSessionResponse response = (RegisterPlayerSessionResponse) ObjectJsonConverter.fromJson(message, RegisterPlayerSessionResponse.class);
            if (response.isRegistered())
                Log.i(UserManagementHelper.class.getName(), "player Session Registered");
            else
                // todo : handle this in a better way. Should not throw Exception. instead there should be retry.
                throw new RuntimeException("User Session is not registered. ");
        }
    };
}
