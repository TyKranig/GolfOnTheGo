package com.example.nate.golfonthego;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        MediaPlayer player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
        player.setLooping(true);
        player.start();

        //toggles the music on and off
        final ToggleButton t;
        t=(ToggleButton)findViewById(R.id.toggleButton);
        t.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //mute
                if(t.isChecked()){
                    AudioManager amanager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
                    amanager.setStreamMute(AudioManager.STREAM_MUSIC, true);
                    amanager.setStreamMute(AudioManager.STREAM_RING, true);

                }

                else{
                    AudioManager amanager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
                    amanager.setStreamMute(AudioManager.STREAM_MUSIC, false);
                    amanager.setStreamMute(AudioManager.STREAM_RING, false);

                }
            }

        });
    }



    //options button function
    public void btnSettings_onClick(View view){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
