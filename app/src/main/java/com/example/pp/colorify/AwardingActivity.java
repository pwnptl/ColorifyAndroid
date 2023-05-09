package com.example.pp.colorify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pp.core.Constants;
import com.example.pp.core.response.MakeMoveResponse;
import com.example.pp.core.utility.ObjectJsonConverter;

public class AwardingActivity extends AppCompatActivity {

    private Button goBackButton;
    private View.OnClickListener goBackClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getBaseContext(), GameLobbyActivity.class);
            startActivity(intent);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_awarding);
        MakeMoveResponse makeMoveResponse = populateResponse();
        initViews();
    }

    private void initViews() {
        goBackButton = findViewById(R.id.awardintActivity_goBackButton);
        goBackButton.setOnClickListener(goBackClickListener);
    }

    private MakeMoveResponse populateResponse() {
        String makeMoveResponseJson = getIntent().getStringExtra(Constants.GAME.GameResponse);
        MakeMoveResponse makeMoveResponse = (MakeMoveResponse) ObjectJsonConverter.fromJson(makeMoveResponseJson, MakeMoveResponse.class);
        return makeMoveResponse;
    }
}