package com.example.pp.colorify;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pp.core.Constants;
import com.example.pp.core.GameState;
import com.example.pp.core.UserManagement.UserManager;
import com.example.pp.core.messageHandler.MessageHandlerInterface;
import com.example.pp.core.messageHandler.MessageHandlerType;
import com.example.pp.core.network.MyWebSocketClientHelper;
import com.example.pp.core.request.CreateGameRequest;
import com.example.pp.core.request.JoinGameRequest;
import com.example.pp.core.response.CreateGameResponse;
import com.example.pp.core.response.JoinGameResponse;
import com.example.pp.core.utility.ObjectJsonConverter;

public class GameLobbyActivity extends AppCompatActivity {

    private MyWebSocketClientHelper myWebSocketClientHelper;

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
        validateUserId();
        initHandlers();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(this.getClass().getName(), "OnStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        // removing handlers as handlers are using previous instance of activity.
        myWebSocketClientHelper.removeHandler(MessageHandlerType.GAME_CREATED);
        myWebSocketClientHelper.removeHandler(MessageHandlerType.GAME_JOINED);
        myWebSocketClientHelper.removeHandler(MessageHandlerType.GAME_READY);

    }

    private void validateUserId() {
        if (!UserManager.getInstance().hasUserId())
            // todo:
            throw new IllegalArgumentException("todo: implement going back to previous activity.");
        else
            userTextView.setText(UserManager.getInstance().getUserId());
    }

    private void initHandlers() {
        Log.i(this.getClass().getName(), "adding handlers");
        myWebSocketClientHelper = MyWebSocketClientHelper.getInstance();

        myWebSocketClientHelper.addHandler(MessageHandlerType.GAME_CREATED, gameCreatedMessageHandler);
        myWebSocketClientHelper.addHandler(MessageHandlerType.GAME_JOINED, gameJoinedMessageHandler);
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

    private final View.OnClickListener createGameButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            CreateGameRequest createGameRequest = new CreateGameRequest(UserManager.getInstance().getUserId());
            myWebSocketClientHelper.send(MessageHandlerType.CREATE_GAME, createGameRequest);
        }
    };

    private final View.OnClickListener joinGameButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            gameIdEditText.setText(gameIdEditText.getText().toString().toUpperCase()); // force uppercase.
            String gameId = gameIdEditText.getText().toString();
            if (gameId.equals(""))
                return; // todo : 1. use StringUtils, 2. show user that gameId is empty and they need to provide it.

            JoinGameRequest joinGameRequest = new JoinGameRequest(UserManager.getInstance().getUserId(), gameId);
            myWebSocketClientHelper.send(MessageHandlerType.JOIN_GAME, joinGameRequest);
        }
    };

    private final MessageHandlerInterface gameCreatedMessageHandler = new MessageHandlerInterface() {
        @Override
        public void handleMessage(String message) {
            Log.i(GameLobbyActivity.class.getName(), "gameCreatedMessageHandler message received " + message);
            CreateGameResponse createGameResponse = (CreateGameResponse) ObjectJsonConverter.fromJson(message, CreateGameResponse.class);

            gameIdPresent(createGameResponse.getGameId(), createGameResponse.getStatus());

        }
    };

    private void gameIdPresent(final String gameId, final String status) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                gameIdTextView.setText(gameId);
                gameStatusTextView.setText(status);

                createGameButton.setEnabled(false);
                joinGameButton.setEnabled(false);
                gameIdEditText.setEnabled(false);
            }
        });

    }


    private void gameIdNotPresent() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // new UI thread.
                gameStatusTextView.setText("GameId not found");
                // todo: hmm, what to do here pawan??

                createGameButton.setEnabled(true);
                joinGameButton.setEnabled(true);
                gameIdEditText.setEnabled(true);
            }
        });

    }

    private final MessageHandlerInterface gameJoinedMessageHandler = new MessageHandlerInterface() {
        @SuppressLint("SetTextI18n") // remove all @suppress
        @Override
        public void handleMessage(String message) {
            JoinGameResponse joinGameResponse = (JoinGameResponse) ObjectJsonConverter.fromJson(message, JoinGameResponse.class);

            if (joinGameResponse.isJoined()) {
                gameIdPresent(joinGameResponse.getGameId(), String.valueOf(joinGameResponse.isJoined()));
                if(joinGameResponse.getGameState().equals(GameState.START))
                {
                    Log.i(GameLobbyActivity.class.getName(), "gameReadyMessageHandler " + message);
                    Intent intent = new Intent(getBaseContext(), GameActivity.class);
                    intent.putExtra(Constants.GAME.ID, joinGameResponse.getGameId());
                    startActivity(intent);
                }
            } else {
                gameIdNotPresent();
            }
        }
    };
}