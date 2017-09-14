package com.example.nate.golfonthego;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LoginScreen extends AppCompatActivity {

    Connection conn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        }
        catch(Exception e){
            System.out.println("The class was not found!");
            e.printStackTrace();
            return;
        }

        try{
            conn = DriverManager.getConnection("jdbc:mysql://mysql.cs.iastate.edu:3306/db309amc1","dbu309amc1","XFsBvb1t");
        }
        catch(SQLException e){
            System.out.println("couldn't get the connection ");
            e.printStackTrace();
            return;
        }



    }
}
