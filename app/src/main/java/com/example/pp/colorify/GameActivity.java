package com.example.pp.colorify;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pp.core.customViews.BoardView;
import com.example.pp.core.customViews.PaletteView;
import com.example.pp.core.customViews.ScoreBoardView;
import com.example.pp.core.messageHandler.MessageHandlerInterface;
import com.example.pp.core.messageHandler.MessageHandlerType;
import com.example.pp.core.network.MyWebSocketClientHelper;
import com.example.pp.core.request.GetGameRequest;
import com.example.pp.core.response.GetGameResponse;
import com.example.pp.core.utility.ObjectJsonConverter;

public class GameActivity extends AppCompatActivity {

    private MyWebSocketClientHelper myWebSocketClientHelper;
    private BoardView boardView;
    private ScoreBoardView scoreBoardView_p1;
    private ScoreBoardView scoreBoardView_p2;
    private PaletteView paletteView;
    private TextView gameIdTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initHandlers();
        initViews();
        getGame();
    }

    private void initViews() {
        boardView = findViewById(R.id.gameActivity_boardView);
        scoreBoardView_p1 = findViewById(R.id.gameActivity_scoreBoard_p1);
        scoreBoardView_p2 = findViewById(R.id.gameActivity_scoreBoard_p2);
        paletteView = findViewById(R.id.gameActivity_palette);
        gameIdTextView = findViewById(R.id.gameActivity_gameId);
    }

    private void getGame() {
        GetGameRequest getGameRequest = new GetGameRequest("0FY9P"); // todo: get this info from last activity.
        myWebSocketClientHelper.send(MessageHandlerType.GET_GAME, getGameRequest);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyHandlers();
    }

    private void initHandlers() {
        myWebSocketClientHelper = MyWebSocketClientHelper.getInstance();
        myWebSocketClientHelper.addHandler(MessageHandlerType.GAME_DATA, new MessageHandlerInterface() {
            @Override
            public void handleMessage(String message) {
                GetGameResponse gameResponse = (GetGameResponse) ObjectJsonConverter.fromJson(message, GetGameResponse.class);
                Log.i(GameActivity.class.getName(), gameResponse.getBoard().toString());
                setGameView(gameResponse);
            }
        });
    }

    private void setGameView(GetGameResponse gameResponse){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                boardView.setBoard(gameResponse.getBoard());
                paletteView.setPalette(gameResponse.getPalette());
                gameIdTextView.setText(gameResponse.getGameId());
            }
        });
    }

    private void destroyHandlers() {
        myWebSocketClientHelper.removeHandler(MessageHandlerType.GAME_DATA);
    }
}