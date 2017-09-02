package com.example.tyler.uitest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.Button;
import android.widget.EditText;
import android.graphics.Color;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RelativeLayout layout = new RelativeLayout(this);
        layout.setBackgroundColor(Color.DKGRAY);

        RelativeLayout.LayoutParams buttonDetails = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        EditText userName = new EditText(this);
        userName.setGravity(Gravity.CENTER);
        userName.setHint(R.string.userName);
        userName.setWidth(560);
        layout.addView(userName);

        EditText password = new EditText(this);
        password.setGravity(Gravity.CENTER);
        password.setHint(R.string.password);
        password.setWidth(560);

        password.setTransformationMethod(new PasswordTransformationMethod());
        layout.addView(password);

        Button login = new Button(this);
        login.setText(R.string.login);
        login.setBackgroundColor(Color.BLACK);
        login.setTextColor(Color.WHITE);
        buttonDetails.addRule(RelativeLayout.CENTER_HORIZONTAL);
        buttonDetails.addRule(RelativeLayout.CENTER_VERTICAL);

        layout.addView(login, buttonDetails);

        setContentView(layout);
    }
}
