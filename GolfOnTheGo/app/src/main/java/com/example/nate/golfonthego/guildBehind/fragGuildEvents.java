package com.example.nate.golfonthego.guildBehind;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.nate.golfonthego.Models.Event;
import com.example.nate.golfonthego.R;
import com.example.nate.golfonthego.guildBehind.guildAdapters.guildEventsAdapter;

/**
 * Created by tyler on 10/16/2017.
 * Fragment used for the guild members screen
 */

public class fragGuildEvents extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.guildlist_events_fragment, container, false);

        //get the users in the guild then set the Adapter to interpret them
        Event[] events = {new Event("10/31/17 - 10/31/17" , "HallowedGolf" , "Course3"), new Event("12/24/17 - 12/25/17","SantaGolf","Course16"), new Event("1/1/18 - 2/1/18","Its January","Course25")};
        ListAdapter eventAdapter = new guildEventsAdapter(this.getContext(), events);
        ListView memberListView = view.findViewById(R.id.list_guild_events);
        memberListView.setAdapter(eventAdapter);

        return view;
    }
}
