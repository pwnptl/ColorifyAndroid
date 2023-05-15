package com.example.pp.colorify;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pp.core.Constants;
import com.example.pp.core.network.MyWebSocketClientHelper;

import java.io.IOException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class ServerActivity extends AppCompatActivity {

    private EditText serverSubDomain;
    private Button serverConnectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
        initViews();
        initServerConnection();
    }

    private void initViews() {
        serverSubDomain = findViewById(R.id.serverActivity_editText_serverName);
        serverConnectButton = findViewById(R.id.serverActivity_button_connect);
        serverConnectButton.setOnClickListener(connectToServerOnClickListener);
    }


    private void initServerConnection() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCE.NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(Constants.SHARED_PREFERENCE.SERVER_SUBDOMAIN)) {
            String serverSubDoamin = sharedPreferences.getString(Constants.SHARED_PREFERENCE.SERVER_SUBDOMAIN, null);
            Log.i(LauncherActivity.class.getName(), "ServerSubdomain exist in the preference : " + serverSubDoamin);
            serverSubDomain.setText(serverSubDoamin);
            proceedIfConnectionAlive();
        } else {
            Log.i(LauncherActivity.class.getName(), "ServerSubdomain do not exist in the preferences.");
        }
    }

    private void proceedIfConnectionAlive() {
        new Thread(() -> {
            if (isConnectionAlive()) {
                proceedToNextActivity();
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Server Not Alive", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        }).start();

    }

    private void proceedToNextActivity() {
        setServerNameInPreference();

        Intent intent = new Intent(getBaseContext(), LauncherActivity.class);
        startActivity(intent);
        finish();
    }

    private void setServerNameInPreference() {
        MyWebSocketClientHelper.setSubDomain(serverSubDomain.getText().toString());

        SharedPreferences.Editor editor = getSharedPreferences(Constants.SHARED_PREFERENCE.NAME, Context.MODE_PRIVATE).edit();
        editor.putString(Constants.SHARED_PREFERENCE.SERVER_SUBDOMAIN, serverSubDomain.getText().toString());
        editor.apply();
    }

    private boolean isConnectionAlive() {
        String serverUrl = Constants.Socket.getServeoPingUrl(serverSubDomain.getText().toString());
        Log.i(ServerActivity.class.getName(), "server Url : " + serverUrl);
        return checkConnection(serverUrl);
    }


    private final View.OnClickListener connectToServerOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            proceedIfConnectionAlive();
        }
    };

    private boolean checkConnection(String url) {
        try {
            URL obj = new URL(url);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            System.out.println("Ping Response Code : " + responseCode);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}