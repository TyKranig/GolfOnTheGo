package com.example.nate.golfonthego.guildBehind;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tyler on 10/16/2017.
 */

public class GuildInfoPageAdapter extends FragmentPagerAdapter{

    private final List<Fragment> mGuildTabsList = new ArrayList<>();
    private final List<String> mGuildTitleList = new ArrayList<>();

    public void addFragment(Fragment frag, String title){
        mGuildTabsList.add(frag);
        mGuildTitleList.add(title);
    }

    public GuildInfoPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mGuildTitleList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mGuildTabsList.get(position);
    }

    @Override
    public int getCount() {
        return mGuildTabsList.size();
    }
}
