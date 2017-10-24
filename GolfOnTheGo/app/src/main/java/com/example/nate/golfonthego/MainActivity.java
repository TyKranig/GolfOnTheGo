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
    private Handler handler = new Handler();
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Random rand = new Random(System.currentTimeMillis());
<<<<<<< HEAD
<<<<<<< HEAD
        altBackground(rand.nextInt()%4+2);
=======

        //handler.postDelayed(altBackground(rand), 500);
        handler.postDelayed(runnable, 0);
        //altBackground(rand);


>>>>>>> f7577ed2daf10b26d6618611403dc0513fc3ce18
        //creates tool bar
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
        //put in activity_main.xml if toolbar is to be implemented
//        <android.support.v7.widget.Toolbar
//        android:id="@+id/toolbar"
//        android:layout_width="222dp"
//        android:layout_height="56dp"
//        android:background="?attr/colorPrimary"
//        tools:layout_editor_absoluteY="102dp"
//        tools:layout_editor_absoluteX="16dp">
//
//         </android.support.v7.widget.Toolbar>



//        //creates fab or little mail icon in lower right
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


        //Plays music - change music eventualy or comment to perma-off
=======
        altBackground(rand.nextInt()%5+1);

        //Plays music - change music eventualy or comment to off
>>>>>>> 9f68db9e95803c31accdae1c9f141c75b680818c
        player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI); //TODO find a way to change from a system default
        player.setLooping(true);
        player.start();
        player.setVolume(currVolume, currVolume);

<<<<<<< HEAD
        //toggles the music on and off
        /*
        final ToggleButton t;
        t=(ToggleButton)findViewById(R.id.toggleButton);
        t.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //mute
                if(t.isChecked()){
                    player.stop();

                    //AudioManager amanager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
                    //amanager.setStreamMute(AudioManager.STREAM_MUSIC, true);
                    //amanager.setStreamMute(AudioManager.STREAM_RING, true);

                }

                else{

                    player.setLooping(true);
                    player.reset();

                    //AudioManager amanager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
                    //amanager.setStreamMute(AudioManager.STREAM_MUSIC, false);
                    //amanager.setStreamMute(AudioManager.STREAM_RING, false);

                }
            }

<<<<<<< HEAD
        });*/

        //});
<<<<<<< HEAD
=======
>>>>>>> 9f68db9e95803c31accdae1c9f141c75b680818c
=======
>>>>>>> f7577ed2daf10b26d6618611403dc0513fc3ce18
        Button testButton = (Button)findViewById(R.id.testButton);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent testScreenIntent = new Intent(context, AccelerometerTest.class);
                startActivity(testScreenIntent);
            }
        });
<<<<<<< HEAD
<<<<<<< HEAD

=======
>>>>>>> 9f68db9e95803c31accdae1c9f141c75b680818c
=======

>>>>>>> f7577ed2daf10b26d6618611403dc0513fc3ce18
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
    public void altBackground(Random r){
        final LinearLayout layout = (LinearLayout) findViewById(R.id.linearlayout);


        switch (r.nextInt()%4+2){ //skip background 1 for now
            case 1:
                        layout.setBackgroundResource(R.drawable.golf_background1);
<<<<<<< HEAD
                    }
                }, 0);
=======
>>>>>>> f7577ed2daf10b26d6618611403dc0513fc3ce18
                break;
            case 2:
                        layout.setBackgroundResource(R.drawable.golf_background2);
<<<<<<< HEAD
                    }
                }, 0);
=======

>>>>>>> f7577ed2daf10b26d6618611403dc0513fc3ce18
                break;
            case 3:
                        layout.setBackgroundResource(R.drawable.golf_background3);
<<<<<<< HEAD
                    }
                }, 0);
=======

>>>>>>> f7577ed2daf10b26d6618611403dc0513fc3ce18
                break;
            case 4:
                        layout.setBackgroundResource(R.drawable.golf_background4);
<<<<<<< HEAD
                    }
                }, 0);
=======

>>>>>>> f7577ed2daf10b26d6618611403dc0513fc3ce18
                break;
            case 5:
                        layout.setBackgroundResource(R.drawable.golf_background5);
<<<<<<< HEAD
                    }
                }, 0);
=======
>>>>>>> f7577ed2daf10b26d6618611403dc0513fc3ce18
                break;



        }
<<<<<<< HEAD
=======
            //if(n>=5) n=1;
            //altBackground(n);

        //handler.postDelayed(altBackground(r), 500);
        return;
>>>>>>> f7577ed2daf10b26d6618611403dc0513fc3ce18
    }

    private Runnable runnable = new Runnable() {

        @Override
        public void run() {
            Random rand = new Random(System.currentTimeMillis());
            altBackground(rand);
            handler.postDelayed(this, 5000);
        }
    };
}
