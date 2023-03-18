package com.example.pp.colorify;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pp.core.messageHandler.MessageHandlerInterface;
import com.example.pp.core.messageHandler.MessageHandlerRegistry;
import com.example.pp.core.messageHandler.MessageHandlerType;
import com.example.pp.core.network.MyWebSocketClientHelper;
import com.example.pp.utility.ConfigReader;

public class LauncherActivity extends AppCompatActivity {

    private MyWebSocketClientHelper myWebSocketClientHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConfigReader.init(getApplicationContext());
        setContentView(R.layout.activity_launcher);

        createWebsocketAndAddHandlers();

        initViews();
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
    }

    private final View.OnClickListener startButtonClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            myWebSocketClientHelper.send(MessageHandlerType.GET_PLAYER_DATA, "");
        }
    };

    private final MessageHandlerInterface playerDataMessageHandler = new MessageHandlerInterface() {
        @Override
        public void handleMessage(String message) {
            Log.i(MessageHandlerRegistry.class.getName(), "message Processing");
        }
    };

    private final MessageHandlerInterface startButtonMessageHandler = new MessageHandlerInterface() {
        @Override
        public void handleMessage(String message) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
}