package com.example.nate.golfonthego;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class LoginScreen extends AppCompatActivity {

    public EditText userName;
    public EditText password;
    public Button login;
    public Button register;
    public Boolean loginsuccess;
    public static final String EXTRA_MESSAGE = "userName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        userName = (EditText)findViewById(R.id.userName);
        password = (EditText)findViewById(R.id.password);

        login = (Button)findViewById(R.id.login);
        register = (Button)findViewById(R.id.register);

        login.setOnClickListener(LoginClickDo());
        register.setOnClickListener(RegisterClickDo());

    }

    View.OnClickListener LoginClickDo(){
        return new View.OnClickListener() {
            public void onClick(View v) {
                if (userName.getText().toString().equals("") || password.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter a UserName and Password", Toast.LENGTH_LONG).show();

                } else {
                    try {
                        final AsyncTask<Void,Void,Void> loginner = new URLTask(userName.getText().toString(), password.getText().toString());
                        loginner.execute();
                        loginner.get(10000, TimeUnit.MILLISECONDS);

                        if (loginsuccess) {
                            Intent intent = new Intent(LoginScreen.this, MainScreen.class);
                            intent.putExtra(EXTRA_MESSAGE, userName.getText().toString());
                            finish();
                            startActivity(intent);

                        } else {
                            Toast.makeText(getApplicationContext(), "Incorrect Username or password", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    View.OnClickListener RegisterClickDo() {
        return new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(LoginScreen.this, Register.class);
                startActivity(intent);
            }
        };
    }

    class URLTask extends AsyncTask<Void,Void,Void>{

        private String username;
        private String password;
        public boolean Success;

        public URLTask(String username, String password)
        {
            this.username = username;
            this.password = password;
        }
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                URL url = new URL("http://proj-309-am-c-1.cs.iastate.edu/login.php?userName=\"" + username + "\"&password=\"" + password + "\"");
                HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
                urlconnection.setRequestProperty("Accept-Charset", "UTF-8");
                urlconnection.setConnectTimeout(15000);
                urlconnection.setDoOutput(true);
                urlconnection.setRequestMethod("POST");
                urlconnection.connect();
                int status = urlconnection.getResponseCode();
                switch (status) {
                    case 200:
                    case 201:
                        BufferedReader br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line+"\n");
                        }
                        br.close();
                        //JSONArray users = new JSONArray(sb.toString());
                        System.out.println("\n" + sb.toString());
                        Success = sb.toString().trim().equals("1");
                        handleLogin(Success);
                }
                // add more code here to send a run request ?
                urlconnection.disconnect();
            }
            catch (Exception e) {
                System.out.println("Couldn't get connection");
                e.printStackTrace();
            }

            return null;
        }
    }

    private void handleLogin(Boolean result){
        loginsuccess = result;
    }

}
