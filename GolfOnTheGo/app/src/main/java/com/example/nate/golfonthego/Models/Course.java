package com.example.nate.golfonthego.Models;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by nate on 10/9/17.
 * Used to store courses in android
 */

public class Course {

    public ArrayList<Hole> holes;
    public int courseNumber;

    public Course(int courseNumber) {
        this.courseNumber = courseNumber;
        holes = new ArrayList<>();
        CourseInit(courseNumber);
    }

    public ArrayList<LatLng> getFairway(int hole) {
        return holes.get(hole - 1).getFairway();
    }

    public ArrayList<LatLng> getGreen(int hole) {
        return holes.get(hole - 1).getGreen();
    }

    public LatLng getTee(int hole) {
        return holes.get(hole - 1).getTee();
    }

    public LatLng getHoleLocation(int hole) { return holes.get(hole).getHoleLocation(); }

    private void CourseInit (int courseNumber) {
        if(courseNumber == 1){
            this.courseNumber = 1;
            ArrayList<LatLng> hole1 = new ArrayList<LatLng>();
            hole1.add(new LatLng(42.026855, -93.647630));
            hole1.add(new LatLng(42.026499, -93.647619));
            hole1.add(new LatLng(42.026224, -93.647684));
            hole1.add(new LatLng(42.026377, -93.646026));
            hole1.add(new LatLng(42.026356, -93.645405));
            hole1.add(new LatLng(42.026778, -93.645426));
            hole1.add(new LatLng(42.026814, -93.646231));
            hole1.add(new LatLng(42.026655, -93.646950));

            LatLng hole1Tee = new LatLng(42.026486, -93.647377);

            ArrayList<LatLng> hole1Green = new ArrayList<LatLng>();
            hole1Green.add(new LatLng(42.026633, -93.645787));
            hole1Green.add(new LatLng(42.026406, -93.645795));
            hole1Green.add(new LatLng(42.026370, -93.645495));
            hole1Green.add(new LatLng(42.026677, -93.645500));

            Hole holeToAdd = new Hole();
            holeToAdd.setFairway(hole1);
            holeToAdd.setGreen(hole1Green);
            holeToAdd.setTee(hole1Tee);
            holeToAdd.setHoleLocation(new LatLng(42.06400, -93.645600));

            holes.add(holeToAdd);
            // end course 1
        }
        if(courseNumber == 2){
            this.courseNumber = 2;
            ArrayList<LatLng> hole1 = new ArrayList<LatLng>();
            hole1.add(new LatLng(42.021608, -93.677601));
            hole1.add(new LatLng(42.021606, -93.677515));
            hole1.add(new LatLng(42.021841, -93.677520));
            hole1.add(new LatLng(42.021841, -93.677676));

            LatLng hole1Tee = new LatLng(42.021650, -93.677566);

            ArrayList<LatLng> hole1Green = new ArrayList<LatLng>();
            hole1Green.add(new LatLng(42.021787, -93.677604));
            hole1Green.add(new LatLng(42.021789, -93.677534));
            hole1Green.add(new LatLng(42.021787, -93.677604));
            hole1Green.add(new LatLng(42.021788, -93.677534));

            Hole holeToAdd = new Hole();
            holeToAdd.setFairway(hole1);
            holeToAdd.setGreen(hole1Green);
            holeToAdd.setTee(hole1Tee);
            holeToAdd.setHoleLocation(new LatLng(42.021788, -93.677534));

            holes.add(holeToAdd);
            // end course 1
        }
    }
}

