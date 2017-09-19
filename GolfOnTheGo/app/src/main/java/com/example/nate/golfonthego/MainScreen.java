package com.example.nate.golfonthego;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        Intent sender = getIntent();
        String message = sender.getStringExtra(LoginScreen.EXTRA_MESSAGE);
        TextView userMessage = (TextView)findViewById(R.id.usernameHello);
        userMessage.setText("Hello " + message + "!");
        userMessage.setTextColor(Color.BLACK);
    }
}
