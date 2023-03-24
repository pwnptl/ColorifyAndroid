package com.example.pp.colorify;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pp.core.Constants;
import com.example.pp.core.network.MyWebSocketClientHelper;

public class GameLobby extends AppCompatActivity {

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
            // todo:
        }
    };
    private final View.OnClickListener joinGameButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // todo:
        }
    };
}