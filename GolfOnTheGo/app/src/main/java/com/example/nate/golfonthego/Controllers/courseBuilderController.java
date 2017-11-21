package com.example.nate.golfonthego.Controllers;

import com.example.nate.golfonthego.Models.Course;
import com.example.nate.golfonthego.Models.Hole;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by tkranig on 11/21/2017.
 * Used to handle the currently in progress building course
 */

public class courseBuilderController {
    private Course course = null;
    private static courseBuilderController builder = null;

    public static courseBuilderController getInstance(Course course){
        if(builder == null){
            builder = new courseBuilderController();
        }

        //set the course to start on
        builder.setCourse(course);

        return builder;
    }

    public void setCourse(Course course){
        this.course = course;
    }

    private courseBuilderController(){}

    public Hole getHole(int hole){
        return course.holes.get(hole - 1);
    }

    public void setHoleFairway(ArrayList<LatLng> fairway, int hole){
        course.holes.get(hole - 1).setFairway(fairway);
    }
}