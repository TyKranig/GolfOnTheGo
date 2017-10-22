package com.example.nate.golfonthego;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.nate.golfonthego.guildBehind.*;
import com.example.nate.golfonthego.guildBehind.guildAdapters.GuildInfoPageAdapter;

public class guildInfoScreen extends AppCompatActivity {

    private GuildInfoPageAdapter _guildInfoPageAdapter;

    private ViewPager _guildViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guild_info_screen);

        //here we set the title of toolbar to the guild name that was put inside the intent
        Toolbar infoToolbar = (Toolbar) findViewById(R.id.guildInfoToolbar);
        setSupportActionBar(infoToolbar);
        getSupportActionBar().setTitle(getIntent().getExtras().getString(guildListMain.tag_guild_name));

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
