package com.example.nate.golfonthego.guildBehind;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.nate.golfonthego.R;

/**
 * Created by tyler on 10/16/2017.
 * Fragment used for the guild members screen
 */

public class fragGuildScores extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.guildlist_scores_fragment, container, false);

        return view;
    }
}
