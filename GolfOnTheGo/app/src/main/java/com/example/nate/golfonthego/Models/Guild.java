package com.example.nate.golfonthego.Models;

/**
 * Created by tyler on 10/15/17.
 * Class for the guilds objects
 */

public class Guild {
    private String _name;
    private int _id;
    private int currentUserIsLeader;

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int isLeader(){
        return currentUserIsLeader;
    }

    public Guild(String _name, int _id, int currentUserIsLeader) {
        this._name = _name;
        this._id = _id;
        this.currentUserIsLeader = currentUserIsLeader;
    }
}
