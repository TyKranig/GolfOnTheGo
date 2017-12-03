package com.example.nate.golfonthego.Models;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by tkranig on 11/21/2017.
 * stores holes for a course
 */

public class Hole {
    private ArrayList<LatLng> fairway;
    private ArrayList<LatLng> green;
    private LatLng tee;
    protected int holeScore = 0;
    private Ball holeBall;
    private Course course;
    public Location flagLocation;

    public Hole(Course course) {
        fairway = new ArrayList<>();
        green = new ArrayList<>();

        this.course = course;
        //setting up ball with hole
        holeBall = new Ball(this.course.courseNumber, tee);
    }

    public void setFairway(ArrayList<LatLng> coords) {
        fairway.clear();
        fairway.addAll(coords);
    }
    public ArrayList<LatLng> getFairway() {
        return this.fairway;
    }

    public void setGreen(ArrayList<LatLng> coords) {
        green.clear();
        green.addAll(coords);
    }
    public ArrayList<LatLng> getGreen() {
        return this.green;
    }

    public void setTee(LatLng tee) {
        this.tee = tee;
    }
    public LatLng getTee() {
        try{
            return this.tee;
        } catch (Exception e) { return null; }
    }

    public Location setFlagLocation(LatLng flagLatLng){
        Location l = new Location("flag");
        l.setLatitude(flagLatLng.latitude);
        l.setLongitude(flagLatLng.longitude);
        this.flagLocation = l;
        return l;
    }
    public LatLng getFlagLocationAsLatLng(){
        return new LatLng(flagLocation.getLatitude(), flagLocation.getLongitude());
    }

    public void addLatLng(LatLng latLng, int pointType){
        if(pointType == greenInt){
            green.add(latLng);
        }
        else if(pointType == fairwayInt){
            fairway.add(latLng);
        }
    }

    public static int fairwayInt = 0;
    public static int greenInt = 1;
}
