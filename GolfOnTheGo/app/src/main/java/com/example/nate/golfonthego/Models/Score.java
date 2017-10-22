package com.example.nate.golfonthego.Models;

import java.util.ArrayList;

/**
 * Created by tyler on 10/22/2017.
 */

public class Score {
    public String courseName;
    private ArrayList<Integer> numberScore;
    private ArrayList<String> userNames;

    public Score(String courseName){
        numberScore = new ArrayList<>();
        userNames = new ArrayList<>();
        this.courseName = courseName;
    }

    public void AddScore(String userName, int score){
        userNames.add(userName);
        numberScore.add(score);
    }

    public int getScore(int position){
        return numberScore.get(position);
    }

    public int getScore(String userName){
        int index = userNames.indexOf(userName);
        if(index > 0){
            return getScore(index);
        }

        return Integer.MIN_VALUE;
    }

    public String getUser(int position){
        return userNames.get(position);
    }
}
