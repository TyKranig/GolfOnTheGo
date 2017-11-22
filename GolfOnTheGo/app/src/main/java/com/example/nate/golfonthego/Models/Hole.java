package com.example.nate.golfonthego.Models;

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
    private LatLng holeLocation;

    public Hole() {
        fairway = new ArrayList<>();
        green = new ArrayList<>();
    }

    public void setFairway(ArrayList<LatLng> coords) {
        this.fairway = coords;
    }

    public ArrayList<LatLng> getFairway() {
        return this.fairway;
    }
    public void setGreen(ArrayList<LatLng> coords) {
        this.green = coords;
    }

    public ArrayList<LatLng> getGreen() {
        return this.green;
    }
    public void setTee(LatLng tee) {
        this.tee = tee;
    }

    public LatLng getTee() {
        return this.tee;
    }

    public void setHoleLocation(LatLng holeLocation) { this.holeLocation = holeLocation; }
    public LatLng getHoleLocation() { return this.holeLocation; }
}
