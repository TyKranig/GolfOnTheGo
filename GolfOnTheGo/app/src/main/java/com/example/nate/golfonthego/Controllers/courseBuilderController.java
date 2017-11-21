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
    private Course course;

    public courseBuilderController(Course course){
        this.course = course;
    }

    public Hole getHole(int hole){
        return course.holes.get(hole - 1);
    }

    public void setCourseFairway(ArrayList<LatLng> fairway){
        
    }

}