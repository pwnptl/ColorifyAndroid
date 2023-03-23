package com.example.pp.colorify;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pp.core.Constants;

public class GameLobby extends AppCompatActivity {

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_lobby);

        getUserId();
    }

    private void getUserId() {
        this.userId = getIntent().getStringExtra(Constants.SHARED_PREFERENCE.USER_ID);
    }

}