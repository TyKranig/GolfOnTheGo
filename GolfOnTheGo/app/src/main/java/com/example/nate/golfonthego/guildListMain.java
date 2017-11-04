package com.example.nate.golfonthego;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.nate.golfonthego.Models.Guild;
import com.example.nate.golfonthego.guildBehind.guildAdapters.guildListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Constants.ConstantURL;
import VolleyAPI.VolleyBall;

public class guildListMain extends AppCompatActivity {
    public static String tag_guild_name = "guildName";
    ArrayList<Guild> guilds;
    ArrayAdapter<Guild> guildAdapter;
    ListView guildListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Clear out the title bar at the top of the page
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_guild_list_main);

        //Generate some fake guilds to use for testing, in the future this will be called from the network
        guilds = new ArrayList();
        //Create a new adapter using the guilds we made above
        guildAdapter = new guildListAdapter(this, guilds);
        guildListView = (ListView) findViewById(R.id.listGuilds);

        //set the adapter to the guild adapter we made
        guildListView.setAdapter(guildAdapter);

        //load data
        loadData();

        //When a user clicks a guild we send them to the guild info screen for that guild
        guildListView.setOnItemClickListener(guildClick());
    }

    AdapterView.OnItemClickListener guildClick(){
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Guild guild = (Guild)adapterView.getItemAtPosition(position);
                Intent intent = new Intent(guildListMain.this, guildInfoScreen.class);
                System.out.println(guild.get_name());

                //making sure that the guild name gets included
                intent.putExtra(tag_guild_name, guild.get_name());
                startActivity(intent);
            }
        };
    }

    private void loadData(){
        VolleyBall.getResponseJsonArray(this, new VolleyBall.VolleyCallback<JSONArray>() {
            @Override
            public void doThings(JSONArray result) {
                for(int i = 0; i < result.length(); i++){
                    try {
                        JSONObject obj = result.getJSONObject(i);

                        Guild guild = new Guild(obj.getString("guildName"), 0);
                        guilds.add(guild);
                        Log.i("json array error", "added things");
                    }
                    catch (JSONException e) {
                        Log.i("json array error", e.toString());
                    }

                    guildAdapter.notifyDataSetChanged();
                }

            }
        }, ConstantURL.URL_GUILDLIST + "userName=" +
                "\"" + MainActivity.mainUser.getName() +"\"");
        Log.i("vollel do things url",ConstantURL.URL_GUILDLIST + "userName=\"" + MainActivity.mainUser.getName() +"\"");
    }
}
