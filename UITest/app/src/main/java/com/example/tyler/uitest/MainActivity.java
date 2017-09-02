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

        //Add the username field
        EditText userName = new EditText(this);
        userName.setGravity(Gravity.CENTER);
        userName.setHint(R.string.userName);
        userName.setWidth(560);
        userName.setId(0);

        //Add the password field
        EditText password = new EditText(this);
        password.setGravity(Gravity.CENTER);
        password.setHint(R.string.password);
        password.setWidth(560);
        password.setTransformationMethod(new PasswordTransformationMethod());
        password.setId(1);

        //Add the login button
        Button login = new Button(this);
        login.setText(R.string.login);
        login.setBackgroundColor(Color.BLACK);
        login.setTextColor(Color.WHITE);
        login.setId(2);

        RelativeLayout.LayoutParams buttonContainer = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        RelativeLayout.LayoutParams userNameContainer = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        RelativeLayout.LayoutParams passwordContainer = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        //give rules to containers
        buttonContainer.addRule(RelativeLayout.CENTER_HORIZONTAL);
        buttonContainer.addRule(RelativeLayout.CENTER_VERTICAL);

        userNameContainer.addRule(RelativeLayout.ABOVE, password.getId());
        userNameContainer.addRule(RelativeLayout.CENTER_HORIZONTAL);
        userNameContainer.setMargins(0, 0, 0, 50);

        passwordContainer.addRule(RelativeLayout.ABOVE, login.getId());
        passwordContainer.addRule(RelativeLayout.CENTER_HORIZONTAL);
        passwordContainer.setMargins(0, 0, 0, 50);

        //add to layout
        layout.addView(login, buttonContainer);
        layout.addView(password, passwordContainer);
        layout.addView(userName, userNameContainer);

        setContentView(layout);
    }
}
