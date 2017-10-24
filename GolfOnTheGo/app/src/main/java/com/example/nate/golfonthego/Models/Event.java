package com.example.nate.golfonthego.Models;

/**
 * Created by tyler on 10/23/17.
 * Date and event name are  saved here for guild events
 */

public class Event {
    public String date;
    public String eventName;
    public String courseName;

    public Event(String date, String eventName, String courseName) {
        this.date = date;
        this.eventName = eventName;
        this.courseName = courseName;
    }
}
