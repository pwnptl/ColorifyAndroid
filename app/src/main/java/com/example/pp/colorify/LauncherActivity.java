package com.example.pp.colorify;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pp.core.BaseNetwork;
import com.example.pp.utility.ConfigReader;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConfigReader.init(getApplicationContext());
        setContentView(R.layout.activity_launcher);

        final Button startGameButton = findViewById(R.id.startGame);
        startGameButton.setOnClickListener(startButtonClickListener);
    }

    private View.OnClickListener startButtonClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            BaseNetwork baseNetwork = BaseNetwork.getInstance();
            String response = baseNetwork.getSync("/hello", null);
            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
        }
    };

}