package com.example.pp.colorify;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pp.core.Constants;
import com.example.pp.core.messageHandler.MessageHandlerInterface;
import com.example.pp.core.messageHandler.MessageHandlerRegistry;
import com.example.pp.core.messageHandler.MessageHandlerType;
import com.example.pp.core.network.MyWebSocketClientHelper;
import com.example.pp.core.request.CreatePlayerRequest;
import com.example.pp.core.request.GetPlayerRequest;
import com.example.pp.core.response.CreatePlayerResponse;
import com.example.pp.utility.ConfigReader;

import java.util.Random;


// UserName Activity
public class LauncherActivity extends AppCompatActivity {

    private MyWebSocketClientHelper myWebSocketClientHelper;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConfigReader.init(getApplicationContext());
        setContentView(R.layout.activity_launcher);
        createWebsocketAndAddHandlers();
        initViews();
        initUser();
    }

    private void initUser() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCE.NAME, Context.MODE_PRIVATE);
        Log.i(LauncherActivity.class.getName(), sharedPreferences.getAll().toString());
        if (sharedPreferences.contains(Constants.SHARED_PREFERENCE.USER_ID)) {
            userId = sharedPreferences.getString(Constants.SHARED_PREFERENCE.USER_ID, null);
            Log.i(LauncherActivity.class.getName(), "User exist in the preference : " + userId);
        } else {
            // todo : createPlayer via dialog.
            Random random = new Random(DateUtils.SECOND_IN_MILLIS % 100);
            CreatePlayerRequest createPlayerRequest = new CreatePlayerRequest("user-" + random.nextInt(1000));
            myWebSocketClientHelper.send(MessageHandlerType.CREATE_PLAYER, createPlayerRequest);
        }
    }

    private void initViews() {
        final Button startGameButton = findViewById(R.id.startGame);

        startGameButton.setOnClickListener(startButtonClickListener);
    }

    private void createWebsocketAndAddHandlers() {
        myWebSocketClientHelper = MyWebSocketClientHelper.getInstance();
        myWebSocketClientHelper.createWebSocketClient();
        myWebSocketClientHelper.addHandler(MessageHandlerType.GET_PLAYER_DATA, startButtonMessageHandler);
        myWebSocketClientHelper.addHandler(MessageHandlerType.PLAYER_DATA, playerDataMessageHandler);
        myWebSocketClientHelper.addHandler(MessageHandlerType.PLAYER_CREATED, playerCreatedMessageHandler);
    }

    private final View.OnClickListener startButtonClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            GetPlayerRequest getPlayerRequest = new GetPlayerRequest(userId);
            myWebSocketClientHelper.send(MessageHandlerType.GET_PLAYER_DATA, getPlayerRequest);
        }
    };

    private final MessageHandlerInterface playerDataMessageHandler = new MessageHandlerInterface() {
        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(String message) {
            Log.i(MessageHandlerRegistry.class.getName(), "message Processing");
            final TextView dummyResponseView = findViewById(R.id.dummyResponseView);
            dummyResponseView.setText("player_data " + message);
        }
    };

    private final MessageHandlerInterface startButtonMessageHandler = new MessageHandlerInterface() {
        @Override
        public void handleMessage(String message) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        final TextView dummyResponseView = findViewById(R.id.dummyResponseView);
                        dummyResponseView.setText(message);
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    private MessageHandlerInterface playerCreatedMessageHandler = new MessageHandlerInterface() {
        @Override
        public void handleMessage(String message) {
            CreatePlayerResponse createPlayerResponse = CreatePlayerResponse.fromJson(message);

            Log.i(LauncherActivity.class.getName(), "Player Created " + createPlayerResponse.getPlayerId());

            SharedPreferences.Editor editor = getSharedPreferences(Constants.SHARED_PREFERENCE.NAME, Context.MODE_PRIVATE).edit();
            editor.putString(Constants.SHARED_PREFERENCE.USER_ID, createPlayerResponse.getPlayerId());
            editor.apply();
        }
    };
}