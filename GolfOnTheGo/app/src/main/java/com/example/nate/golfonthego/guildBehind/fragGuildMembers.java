package com.example.nate.golfonthego.guildBehind;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.nate.golfonthego.*;
import com.example.nate.golfonthego.Models.User;
import com.example.nate.golfonthego.guildBehind.guildAdapters.guildMemberAdapter;

/**
 * Created by tyler on 10/16/2017.
 * Fragment used for the guild members screen
 */

public class fragGuildMembers extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.guildlist_members_fragment, container, false);

        //get the users in the guild then set the Adapter to interpret them
        User[] users = {new User("nate", "nate"), new User("tyler", "tyler"), new User("ryan", "ryan")};
        ListAdapter memberAdapter = new guildMemberAdapter(this.getContext(), users);
        ListView memberListView = view.findViewById(R.id.list_Members);
        memberListView.setAdapter(memberAdapter);

        return view;
    }
}
