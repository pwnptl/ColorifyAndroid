package com.example.pp.colorify;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pp.core.Constants;
import com.example.pp.core.messageHandler.MessageHandlerInterface;
import com.example.pp.core.messageHandler.MessageHandlerType;
import com.example.pp.core.network.MyWebSocketClientHelper;
import com.example.pp.core.request.CreateGameRequest;
import com.example.pp.core.response.CreateGameResponse;
import com.example.pp.core.utility.ObjectJsonConverter;

public class GameLobbyActivity extends AppCompatActivity {

    private MyWebSocketClientHelper myWebSocketClientHelper;

    private String userId;
    private Button joinGameButton;
    private Button createGameButton;
    private EditText gameIdEditText;
    private TextView gameIdTextView;
    private TextView gameStatusTextView;
    private TextView userTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_lobby);

        initViews();
        getUserId();
        initHandlers();
    }

    private void initHandlers() {
        myWebSocketClientHelper = MyWebSocketClientHelper.getInstance();
        myWebSocketClientHelper.createWebSocketClient();
        // handlers ?

        myWebSocketClientHelper.addHandler(MessageHandlerType.GAME_CREATED, gameCreatedMessageHandler);
        myWebSocketClientHelper.addHandler(MessageHandlerType.GAME_READY, gameReadyMessageHandler);
    }

    private void initViews() {
        joinGameButton = findViewById(R.id.gameLobby_button_joinGame);
        createGameButton = findViewById(R.id.gameLobby_button_createNewGame);

        gameIdEditText = findViewById(R.id.gameLobby_editText_gameId);
        gameIdTextView = findViewById(R.id.gameLobby_textView_gameId);
        gameStatusTextView = findViewById(R.id.gameLobby_textview_status);
        userTextView = findViewById(R.id.gameLobby_userId);

        joinGameButton.setOnClickListener(joinGameButtonClickListener);
        createGameButton.setOnClickListener(createGameButtonClickListener);
    }

    private void getUserId() {
        this.userId = getIntent().getStringExtra(Constants.SHARED_PREFERENCE.USER_ID);
        userTextView.setText(userId);
    }

    private final View.OnClickListener createGameButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            CreateGameRequest createGameRequest = new CreateGameRequest(userId);
            myWebSocketClientHelper.send(MessageHandlerType.CREATE_GAME, createGameRequest);
        }
    };
    private final View.OnClickListener joinGameButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };


    private final MessageHandlerInterface gameCreatedMessageHandler = new MessageHandlerInterface() {
        @Override
        public void handleMessage(String message) {
            Log.i(GameLobbyActivity.class.getName(), "gameCreatedMessageHandler message received " + message);
            CreateGameResponse createGameResponse = (CreateGameResponse) ObjectJsonConverter.fromJson(message, CreateGameResponse.class);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    gameIdPresent(createGameResponse.getGameId(), createGameResponse.getStatus());
                }
            });
        }
    };

    private void gameIdPresent(final String gameId, final String status) {
        gameIdTextView.setText(gameId);
        gameStatusTextView.setText(status);

        createGameButton.setEnabled(false);
        joinGameButton.setEnabled(false);
        gameIdEditText.setEnabled(false);
    }


    private MessageHandlerInterface gameReadyMessageHandler = new MessageHandlerInterface() {
        @Override
        public void handleMessage(String message) {
            // todo : next Activity Yaay.
        }
    };
}