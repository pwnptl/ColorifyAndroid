package com.example.pp.colorify;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pp.core.Constants;
import com.example.pp.core.UserManagement.UserManager;
import com.example.pp.core.customViews.BoardView;
import com.example.pp.core.customViews.PaletteView;
import com.example.pp.core.customViews.ScoreBoardView;
import com.example.pp.core.messageHandler.MessageHandlerInterface;
import com.example.pp.core.messageHandler.MessageHandlerType;
import com.example.pp.core.models.ColorifyColor;
import com.example.pp.core.network.MyWebSocketClientHelper;
import com.example.pp.core.request.GetGameRequest;
import com.example.pp.core.request.MakeMoveRequest;
import com.example.pp.core.response.GetGameResponse;
import com.example.pp.core.response.MakeMoveResponse;
import com.example.pp.core.utility.ObjectJsonConverter;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    private MyWebSocketClientHelper myWebSocketClientHelper;
    private BoardView boardView;
    private ArrayList<ScoreBoardView> scoreBoardViews;
    private PaletteView paletteView;
    private TextView gameIdTextView;
    private String gameId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initHandlers();
        initViews();
        getGame();
    }

    private void initViews() {
        scoreBoardViews = new ArrayList<>();
        boardView = findViewById(R.id.gameActivity_boardView);
        scoreBoardViews.add(findViewById(R.id.gameActivity_scoreBoard_p1));
        scoreBoardViews.add(findViewById(R.id.gameActivity_scoreBoard_p2));
        paletteView = findViewById(R.id.gameActivity_palette);
        paletteView.setOnClickListenerForButtons(paletteButtonOnclickListener);
        gameIdTextView = findViewById(R.id.gameActivity_gameId);
    }

    private void getGame() {
        gameId = getIntent().getStringExtra(Constants.GAME.ID);
        GetGameRequest getGameRequest = new GetGameRequest(gameId);
        myWebSocketClientHelper.send(MessageHandlerType.GET_GAME, getGameRequest);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyHandlers();
    }

    private void initHandlers() {
        myWebSocketClientHelper = MyWebSocketClientHelper.getInstance();
        myWebSocketClientHelper.addHandler(MessageHandlerType.GAME_DATA, gameDateMessageHandler);
        myWebSocketClientHelper.addHandler(MessageHandlerType.MADE_MOVE, madeMoveMessageHandler);
    }

    private void setGameView(GetGameResponse gameResponse) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                boardView.setBoard(gameResponse.getBoard());
                paletteView.setPalette(gameResponse.getPalette());
                gameIdTextView.setText(gameResponse.getGameId());
                setScoreBoards(gameResponse);
            }
        });
    }

    private void setScoreBoards(GetGameResponse gameResponse) {
        // todo : Improve Impl. This function is only good for 2 players. Should be rotating strategy with preserved order to scale to more players.
        String currentPlayerId = UserManager.getInstance().getUserId();
        for (String pId : gameResponse.getScoreTracker().getPlayerIdToScoreMap().keySet()) {
            if (pId.equals(currentPlayerId))
                setScoreBoard(scoreBoardViews.get(0), pId, gameResponse);
            else
                setScoreBoard(scoreBoardViews.get(1), pId, gameResponse);
        }
    }

    private void setScoreBoard(ScoreBoardView scoreBoardView, String pId, GetGameResponse gameResponse) {
        scoreBoardView.setName(pId, pId); // todo: add name as second param.
        scoreBoardView.setScore(gameResponse.getScoreTracker().getPlayerIdToScoreMap().get(pId).getCount(), gameResponse.getScoreTracker().getTotalCells());
    }

    private final View.OnClickListener paletteButtonOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int color = ((ColorDrawable) v.getBackground()).getColor();
            int index = ColorifyColor.getIndex(color);
            String playerId = UserManager.getInstance().getUserId();
            Toast.makeText(getApplicationContext(), ColorifyColor.valueOf(index).toString(), Toast.LENGTH_SHORT).show();
            Log.i(GameActivity.class.getName(), "playerId:index " + playerId + ": " + index + " " + ColorifyColor.valueOf(index));
            MakeMoveRequest makeMoveRequest = new MakeMoveRequest(playerId, gameId, index);
            myWebSocketClientHelper.send(MessageHandlerType.MAKE_MOVE, makeMoveRequest);
        }
    };

    private final MessageHandlerInterface gameDateMessageHandler = new MessageHandlerInterface() {
        @Override
        public void handleMessage(String message) {
            GetGameResponse gameResponse = (GetGameResponse) ObjectJsonConverter.fromJson(message, GetGameResponse.class);
            Log.i(GameActivity.class.getName(), gameResponse.getBoard().toString());
            setGameView(gameResponse);
        }
    };

    private final MessageHandlerInterface madeMoveMessageHandler = new MessageHandlerInterface() {
        @Override
        public void handleMessage(String message) {
            MakeMoveResponse gameResponse = (MakeMoveResponse) ObjectJsonConverter.fromJson(message, MakeMoveResponse.class);
            Log.i(GameActivity.class.getName(), gameResponse.getBoard().toString());
            setGameView(gameResponse);
        }
    };

    private void destroyHandlers() {
        myWebSocketClientHelper.removeHandler(MessageHandlerType.GAME_DATA);
        myWebSocketClientHelper.removeHandler(MessageHandlerType.MADE_MOVE);
    }
}