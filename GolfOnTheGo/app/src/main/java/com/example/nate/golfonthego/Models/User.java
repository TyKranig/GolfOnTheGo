package com.example.nate.golfonthego.Models;

/**
 * Created by tyler on 10/21/2017.
 * A User object for storing info of users in the Golfverse
 */

public class User {

    private String name;
    private String password;

    /**
     * @param name The username for the user
     * @param password The password for the user
     * Creates a new user object and returns
     */
    public User(String name, String password){
        this.name = name;
        this.password = password;
    }


    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
