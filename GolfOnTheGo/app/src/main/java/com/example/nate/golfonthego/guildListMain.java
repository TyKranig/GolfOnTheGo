package com.example.nate.golfonthego;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.nate.golfonthego.Models.Guild;
import com.example.nate.golfonthego.guildBehind.guildAdapters.guildListAdapter;

public class guildListMain extends AppCompatActivity {
    public static String tag_guild_name = "guildName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guild_list_main);

        Guild[] guild = {new Guild("Golfer4Life", 1), new Guild("HackingGolf", 2), new Guild("TigerBools", 3)};
        ListAdapter guildAdapter = new guildListAdapter(this, guild);
        ListView guildListView = (ListView) findViewById(R.id.listGuilds);
        guildListView.setAdapter(guildAdapter);

        guildListView.setOnItemClickListener(guildClick());
    }

    AdapterView.OnItemClickListener guildClick(){
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Guild guild = (Guild)adapterView.getItemAtPosition(position);
                Intent intent = new Intent(guildListMain.this, guildInfoScreen.class);
                System.out.println(guild.get_name());
                intent.putExtra(tag_guild_name, guild.get_name());
                startActivity(intent);
            }
        };
    }
}
