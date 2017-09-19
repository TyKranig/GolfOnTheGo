package com.example.nate.golfonthego;

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

public class Register extends AppCompatActivity {

    public EditText userName;
    public EditText password;
    public EditText confirm;
    public Button register;
    public Boolean registerSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userName = (EditText)findViewById(R.id.regUserName);
        password = (EditText)findViewById(R.id.regPass);
        confirm = (EditText)findViewById(R.id.regPassConf);
        register = (Button)findViewById(R.id.regRegister);

        register.setOnClickListener(RegisterClickDo());
    }

    View.OnClickListener RegisterClickDo(){
        return new View.OnClickListener() {
            public void onClick(View v) {
                if (userName.getText().toString().equals("") || password.getText().toString().equals("") || confirm.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter a UserName and Password", Toast.LENGTH_LONG).show();
                } else if (!(password.getText().toString().equals(confirm.getText().toString()))) {
                    Toast.makeText(getApplicationContext(), "Your passwords do not match", Toast.LENGTH_LONG).show();
                } else {
                    try {
                        final AsyncTask<Void, Void, Void> loginner = new URLTask(userName.getText().toString(), password.getText().toString());
                        loginner.execute();
                        loginner.get(10000, TimeUnit.MILLISECONDS);

                        if (registerSuccess) {
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Username already taken", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    class URLTask extends AsyncTask<Void,Void,Void> {

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
                URL url = new URL("http://proj-309-am-c-1.cs.iastate.edu/register.php?userName=\"" + username + "\"&password=\"" + password + "\"");
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
        registerSuccess = result;
    }
}
