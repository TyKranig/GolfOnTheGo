package com.example.nate.golfonthego;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.nate.golfonthego.guildBehind.*;

//TODO: add stuff to the toolbar

public class guildInfoScreen extends AppCompatActivity {

    private GuildInfoPageAdapter _guildInfoPageAdapter;

    private ViewPager _guildViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guild_info_screen);

        _guildInfoPageAdapter = new GuildInfoPageAdapter(getSupportFragmentManager());

        //set up the view pager with the sections adapter
        _guildViewPager = (ViewPager) findViewById(R.id.guildInfoContainer);
        setupViewPager(_guildViewPager);

        //setup the tab layout object
        TabLayout guildTabs = (TabLayout) findViewById(R.id.guildInfoTabs);
        guildTabs.setupWithViewPager(_guildViewPager);

    }

    private void setupViewPager(ViewPager viewPager){
        GuildInfoPageAdapter pageAdapter = new GuildInfoPageAdapter(getSupportFragmentManager());
        pageAdapter.addFragment(new fragGuildMembers(), "Members");
        pageAdapter.addFragment(new fragGuildScores(), "Scores");
        pageAdapter.addFragment(new fragGuildEvents(), "Events");
        viewPager.setAdapter(pageAdapter);
    }

}
