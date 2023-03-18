package com.example.pp.colorify;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pp.core.messageHandler.MessageHandlerInterface;
import com.example.pp.core.messageHandler.MessageHandlerType;
import com.example.pp.core.network.MyWebSocketClientHelper;
import com.example.pp.utility.ConfigReader;

public class LauncherActivity extends AppCompatActivity {

    MyWebSocketClientHelper myWebSocketClientHelper = new MyWebSocketClientHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConfigReader.init(getApplicationContext());
        setContentView(R.layout.activity_launcher);
        myWebSocketClientHelper.createWebSocketClient2();
        myWebSocketClientHelper.addHandler(new MessageHandlerInterface() {
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
        });
//        createWebSocketClient();
//        webSocketBasedClient = new WebSocketBasedClient(Constants.Socket.socketURL);

        final Button startGameButton = findViewById(R.id.startGame);
        startGameButton.setOnClickListener(startButtonClickListener);
    }

    private View.OnClickListener startButtonClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "Clicked Button", Toast.LENGTH_LONG).show();
            myWebSocketClientHelper.send(MessageHandlerType.START_BUTTON_MESSAGE_HANDLER, "ClickedButton");
        }
    };

}