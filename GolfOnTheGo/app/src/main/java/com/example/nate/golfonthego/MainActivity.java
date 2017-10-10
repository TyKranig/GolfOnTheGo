package com.example.nate.golfonthego;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    final Context context = this;
    float currVolume = 0.90f;
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Random rand = new Random(System.currentTimeMillis());
        altBackground(rand.nextInt()%5+1);

        //Plays music - change music eventualy or comment to off
        player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI); //TODO find a way to change from a system default
        player.setLooping(true);
        player.start();
        player.setVolume(currVolume, currVolume);

        Button testButton = (Button)findViewById(R.id.testButton);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent testScreenIntent = new Intent(context, AccelerometerTest.class);
                startActivity(testScreenIntent);
            }
        });
    }



    //options button function
    public void btnSettings_onClick(View view){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void friendsBtn_onClick(View view){ // used to test dialogues
        //dialog
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom);
        dialog.setTitle("Title");

        // set the custom dialog components - text, image and button
        TextView text = dialog.findViewById(R.id.text);
        text.setText("Dialog Testing");
        Button dialogButton = dialog.findViewById(R.id.dialogButtonOK);

        //close dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    public void coursesBtn_onClick(View view){

        //Intent intent = new Intent(this, CourseActivity.class);
        //startActivity(intent);

    }
    public void leaderBoardBtn_onClick(View view){

        Intent intent = new Intent(this, LeaderActivity.class);
        startActivity(intent);

    }

    public void play_onClick(View view){
        Intent intent = new Intent(this, CourseActivity.class);
        startActivity(intent);
    }
    public void volumeOff(View view) {
        //MediaPlayer mediaPlayer = null;
        player.setVolume(0.0f, 0.0f);
        currVolume = 0.0f;
    }

    public void volumeLow(View view){
        //MediaPlayer mediaPlayer = null;
        player.setVolume(0.09f, 0.09f);
        currVolume = 0.09f;
    }
    public void volumeMed(View view) {
        //MediaPlayer mediaPlayer = null;
        player.setVolume(0.40f, 0.40f);
        currVolume = 0.40f;
    }

    public void volumeHigh(View view) {
        //MediaPlayer mediaPlayer = null;
        player.setVolume(0.90f, 0.90f);
        currVolume = 0.90f;
    }
    public void altBackground(int n){
        final LinearLayout layout = (LinearLayout) findViewById(R.id.linearlayout);
        Handler handler = new Handler();



        //while(true){
        switch (n){
            case 1:
                handler.postDelayed(new Runnable(){

                    @Override
                    public void run() {
                        layout.setBackgroundResource(R.drawable.golf_background1);
                    }
                }, 0);
                break;
            case 2:
                handler.postDelayed(new Runnable(){

                    @Override
                    public void run() {
                        layout.setBackgroundResource(R.drawable.golf_background2);
                    }
                }, 0);
                break;
            case 3:
                handler.postDelayed(new Runnable(){

                    @Override
                    public void run() {
                        layout.setBackgroundResource(R.drawable.golf_background3);
                    }
                }, 0);
                break;
            case 4:
                handler.postDelayed(new Runnable(){

                    @Override
                    public void run() {
                        layout.setBackgroundResource(R.drawable.golf_background4);
                    }
                }, 0);
                break;
            case 5:
                handler.postDelayed(new Runnable(){

                    @Override
                    public void run() {
                        layout.setBackgroundResource(R.drawable.golf_background5);
                    }
                }, 0);
                break;



        }
    }
}
