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


    //fills table with first 10 userNames.
    public void initTable() throws SQLException{
        //initializes headers.
        String[] header = {"Names:"};
        displayTable(header, 0);
        int t = 0;
        //loops to pull 10 userNames from the database
        for(int i=1; i<11; i++) {
            //initializes whitch position in database to pull from
            t++;
            String url = ConstantURL.URL_LEADER + "pos=" + t ;

            final int finalI = i;
            VolleyBall.getResponseString(this, new VolleyBall.VolleyCallback() {
                @Override
                public void doThings(Object result) {
                    try {
                        //overwrites default list to paste new name from database
                        String[] name = {"Pass:", "Stan", "Dave", "Matt", "Larry", "Joseph", "Danny", "Luke", "Zach", "John", "dude"};
                        String temp = result.toString();
                        temp = temp.replace("\n", "").replace("\r", ""); //reomves new lines added in from transition from php.
                        name[finalI] = temp;
                        displayTable(name, finalI);


                    } catch (Exception e) {
                        //ends gives error message and ends table filling.
                        String[] name = {"Failure to access Database", "Failure to access Database", "Failure to access Database", "Failure to access Database", "Failure to access Database", "Failure to access Database", "Failure to access Database", "Failure to access Database", "Failure to access Database", "Failure to access Database", "Failure to access Database"};
                        displayTable(name, finalI);
                        System.out.println(e.toString());
                        System.out.println("failed");
                        return;
                    }
                }
            }, url);
        }


    }

    public void displayTable(String[] names, int i){
        //Find Table
        TableLayout tl=(TableLayout)findViewById(R.id.Table);
        //String[] names = {"NAME:", "Stan", "Dave", "Matt", "Larry", "Joseph", "Danny", "Luke", "Zach", "John"};
        Random rand = new Random(System.currentTimeMillis());

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
