package com.example.nate.golfonthego;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONObject;

import Constants.ConstantURL;
import VolleyAPI.RequestQueueSingleton;

public class LoginScreen extends AppCompatActivity {

    public EditText userName;
    public EditText password;
    public Button login;
    public Button register;
    public static final String EXTRA_MESSAGE = "userName";
    private String tag_string_request = "string_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = new Intent(this, MainActivity.class);
        //startActivity(intent);//TODO remove once testing is done

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        userName = (EditText)findViewById(R.id.userName);
        password = (EditText)findViewById(R.id.password);

        login = (Button)findViewById(R.id.login);
        register = (Button)findViewById(R.id.register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginRequest();
            }
        });
        register.setOnClickListener(RegisterClickDo());

    }

    private void loginRequest(){
        String user = userName.getText().toString();
        String pass = password.getText().toString();

        if (user.equals("") || pass.equals("")) {
            Toast.makeText(getApplicationContext(), "Please enter a UserName and Password", Toast.LENGTH_LONG).show();
            return;
        }

        JsonObjectRequest loginRequest = new JsonObjectRequest
                (Request.Method.GET, ConstantURL.URL_LOGIN + "userName=\"" + user + "\"&password=\"" + pass + "\""
                        , null, new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            System.out.println(response.toString());
                            if(response.getInt("result") == 1){
                                Intent intent = new Intent(LoginScreen.this, MainActivity.class);
                                intent.putExtra(EXTRA_MESSAGE, userName.getText().toString());
                                finish();
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "Incorrect Username or password", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e){
                            System.out.println(e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.toString());
                        VolleyLog.d(tag_string_request, "Error: " + error.getMessage());
                    }
                });

        RequestQueueSingleton.getInstance(this).addToRequestQueue(loginRequest);
    }

    View.OnClickListener RegisterClickDo() {
        return new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(LoginScreen.this, Register.class);
                startActivity(intent);
            }
        };
    }
}
