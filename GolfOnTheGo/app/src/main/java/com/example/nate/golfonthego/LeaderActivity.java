package com.example.nate.golfonthego;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.Random;

import Constants.ConstantURL;
import VolleyAPI.VolleyBall;

public class LeaderActivity extends AppCompatActivity {
    //TableLayout ll = (TableLayout) findViewById(R.id.Table);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader);
        try {
            initTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void initTable() throws SQLException{


        int t = 0;
        String url = ConstantURL.URL_LEADER + "pos=\"" + t + "\"";
        VolleyBall.getResponseString(this, new VolleyBall.VolleyCallback() {
            @Override
            public void doThings(Object result) {
                try{
                    String[] name = {"Pass:", "Stan", "Dave", "Matt", "Larry", "Joseph", "Danny", "Luke", "Zach", "John"};
                    //JSONObject list = (JSONObject)result;
                    name[0] = (String) result;
                    //System.out.println(list.toString());
                    displayTable(name);


                } catch (Exception e){
                    String[] name = {"Fail:", "Stan", "Dave", "Matt", "Larry", "Joseph", "Danny", "Luke", "Zach", "John"};
                    displayTable(name);
                    System.out.println(e.toString());
                    System.out.println("failed");
                }
            }
        }, url);

        /*Connection con = null;
        String sql = "SELECT userName FROM user";
        ResultSet namesql = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql.cs.iastate.edu:db309amc1", "dbu309amc1", "XFsBvb1t");
            //jdbc:subprotocol:subname
            namesql = con.prepareStatement(sql).executeQuery();
        }  catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Unable to make connection to database");
            //return;
        }*/




    }

    public void displayTable(String[] names){
        //Find Table
        TableLayout tl=(TableLayout)findViewById(R.id.Table);
        //String[] names = {"NAME:", "Stan", "Dave", "Matt", "Larry", "Joseph", "Danny", "Luke", "Zach", "John"};
        Random rand = new Random(System.currentTimeMillis());
        for(int i=0; i<10; i++) {
            //create a new row
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            //add text for name
            TextView tvn = new TextView(this);
            tvn.setPadding(10,0,0,0);
            tvn.setText(names[i]);
            //tvn.setText("asdf");

            //textview.getTextColors(R.color.)
            tvn.setTextColor(Color.BLACK);

            //add name into the row
            tr.addView(tvn);

            //buffer space
            TextView tvb = new TextView(this);
            tvb.setPadding(10,0,0,0);
            tvb.setText("  ");
            tr.addView(tvb);

            //add text for score
            TextView tvs = new TextView(this);
            if(i==0) tvs.setText("SCORE:");
                //else tvs.setText("asdf");
            else{
                int score = rand.nextInt()%999+1;
                if(score<1) score = score *-1;
                tvs.setText(Integer.toString(score));
            }

            //add score into the row
            tr.addView(tvs);


            //add row into table
            tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }

    }
}
