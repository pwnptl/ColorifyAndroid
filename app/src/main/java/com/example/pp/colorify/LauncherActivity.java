package com.example.pp.colorify;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pp.core.Constants;
import com.example.pp.core.messageHandler.MessageHandlerInterface;
import com.example.pp.core.messageHandler.MessageHandlerType;
import com.example.pp.core.network.MyWebSocketClientHelper;
import com.example.pp.core.request.CreatePlayerRequest;
import com.example.pp.core.response.CreatePlayerResponse;
import com.example.pp.core.response.GetPlayerResponse;
import com.example.pp.core.utility.ObjectJsonConverter;
import com.example.pp.utility.ConfigReader;

// UserName Activity
public class LauncherActivity extends AppCompatActivity {

    private MyWebSocketClientHelper myWebSocketClientHelper;
    private String userId;

    private Button clearUserButton;
    private Button createUserButton;
    private EditText nameEditText;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConfigReader.init(getApplicationContext());
        setContentView(R.layout.activity_launcher);

        initViews();
        initUser();
        createWebsocketAndAddHandlers();
    }

    private void initViews() {
        createUserButton = findViewById(R.id.createUser_button);
        clearUserButton = findViewById(R.id.clearUser_button);
        nameEditText = findViewById(R.id.name_editTest);
        nextButton = findViewById(R.id.launcher_button_next);

        createUserButton.setOnClickListener(createUserButtonClickListener);
        clearUserButton.setOnClickListener(clearUserButtonClickListner);
        nextButton.setOnClickListener(nextButtonClickListner);
    }

    private void initUser() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCE.NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(Constants.SHARED_PREFERENCE.USER_ID)) {
            userId = sharedPreferences.getString(Constants.SHARED_PREFERENCE.USER_ID, null);
            Log.i(LauncherActivity.class.getName(), "User exist in the preference : " + userId);
            hasUser(userId);
        } else {
            Log.i(LauncherActivity.class.getName(), "User do not exist in the preferences.");
            noUser();
        }
    }

    private void noUser() {
        createUserButton.setEnabled(true);
        nameEditText.setEnabled(true);
        nextButton.setEnabled(false);
        nameEditText.setText("");
    }

    private void hasUser(String userId) {
        Log.i(LauncherActivity.class.getName(), "has user " + userId);
        Log.i(LauncherActivity.class.getName(), createUserButton.toString() + nextButton.toString());
        createUserButton.setEnabled(false);
        nameEditText.setEnabled(false);
        nextButton.setEnabled(true);
        nameEditText.setText(userId);
    }

    private void createWebsocketAndAddHandlers() {
        myWebSocketClientHelper = MyWebSocketClientHelper.getInstance();
        myWebSocketClientHelper.createWebSocketClient();
        myWebSocketClientHelper.addHandler(MessageHandlerType.PLAYER_DATA, playerDataMessageHandler);
        myWebSocketClientHelper.addHandler(MessageHandlerType.PLAYER_CREATED, playerCreatedMessageHandler);
    }

    private View.OnClickListener createUserButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String name = nameEditText.getText().toString();
            // todo: use ApacheCommons StringUtils
            if ("".equals(name)) return;

            CreatePlayerRequest createPlayerRequest = new CreatePlayerRequest(name);
            myWebSocketClientHelper.send(MessageHandlerType.CREATE_PLAYER, createPlayerRequest);
        }
    };

    private void setSharedPreferences(String name) {
        SharedPreferences.Editor editor = getSharedPreferences(Constants.SHARED_PREFERENCE.NAME, Context.MODE_PRIVATE).edit();
        editor.putString(Constants.SHARED_PREFERENCE.USER_ID, name);
        editor.apply();
    }

    private View.OnClickListener clearUserButtonClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            clearSharedPreferences();
            noUser();
        }
    };

    private View.OnClickListener nextButtonClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // todo : add next Activity.
            // Intent intent = new Intent(getBaseContext(), SignoutActivity.class);
            // intent.putExtra("USER_ID", userId);
            // startActivity(intent);
        }
    };


    private void clearSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCE.NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }


    private final MessageHandlerInterface playerDataMessageHandler = new MessageHandlerInterface() {
        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(String message) {
            Log.i(LauncherActivity.class.getName(), "message + " + message);
            GetPlayerResponse getPlayerResponse = (GetPlayerResponse) ObjectJsonConverter.fromJson(message, GetPlayerResponse.class);
//            final TextView dummyResponseView = findViewById(R.id.dummyResponseView);
//            dummyResponseView.setText("player_data " + message);
        }
    };

    private final MessageHandlerInterface playerCreatedMessageHandler = new MessageHandlerInterface() {
        @Override
        public void handleMessage(String message) {
            CreatePlayerResponse createPlayerResponse = CreatePlayerResponse.fromJson(message);
            Log.i(LauncherActivity.class.getName(), "Player Created " + createPlayerResponse.getPlayerId());
            setSharedPreferences(createPlayerResponse.getPlayerId());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    hasUser(createPlayerResponse.getPlayerId());
                }
            });
        }
    };
}