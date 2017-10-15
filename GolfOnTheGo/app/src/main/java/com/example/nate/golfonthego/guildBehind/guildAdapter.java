package com.example.nate.golfonthego.guildBehind;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nate.golfonthego.R;

import java.util.Random;

/**
 * Created by tyler on 10/15/17.
 * Used as a custom adapted for guild list screen
 */

public class guildAdapter extends ArrayAdapter<Guild>{

    public guildAdapter(Context context, Guild[] guilds) {
        super(context, R.layout.custom_row_guild, guilds);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater guildInflater = LayoutInflater.from(getContext());
        View customView = guildInflater.inflate(R.layout.custom_row_guild, parent, false);

        Guild guild = getItem(position);
        TextView guildText = customView.findViewById(R.id.txtGuildName);
        TextView scoreText = customView.findViewById(R.id.txtGuildScore);

        assert guild != null;
        guildText.setText(guild.get_name());
        scoreText.setText(guild.get_id());

        return customView;
    }
}

