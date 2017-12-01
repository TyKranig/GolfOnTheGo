package com.example.nate.golfonthego;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.nate.golfonthego.Controllers.Adapters.courseListAdapter;
import com.example.nate.golfonthego.Controllers.courseBuilderController;
import com.example.nate.golfonthego.Models.Course;

import java.util.ArrayList;

public class courseBuildCourseSelector extends AppCompatActivity {
    private ArrayList<Course> courses;
    private ArrayAdapter<Course> courseAdapter;
    private ListView courseListView;
    private Button btnAddCourse;
    private Button btnSaveCourse;
    public static courseBuilderController courseBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_build_course_selector);

        //courses array setup
        courses = new ArrayList<>();
        courses.add(new Course(1));
        courses.add(new Course(2));

        //set the data for the courses list
        courseAdapter = new courseListAdapter(this, courses);
        courseListView = findViewById(R.id.lstBuiltCourses);

        //set button click listener
        btnAddCourse = findViewById(R.id.btnAddCourse);
        btnSaveCourse = findViewById(R.id.btnSaveCourse);

        setupButtons();
    }

    private void setupButtons(){
        courseListView.setAdapter(courseAdapter);
        courseListView.setOnItemClickListener(courseClick());
        btnAddCourse.setOnClickListener(newCourseClick());
        btnSaveCourse.setOnClickListener(saveCourseClick());
    }

    private View.OnClickListener newCourseClick(){
        return new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Course newCourse = new Course(courses.size());
                courses.add(newCourse);
                courseBuilder = courseBuilderController.getInstance(newCourse);
                Intent intent = new Intent(courseBuildCourseSelector.this, courseBuilderHoleSelector.class);
                startActivity(intent);

                //update the courses list
                courseAdapter.notifyDataSetChanged();
            }
        };
    }

    private AdapterView.OnItemClickListener courseClick() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                courseBuilder = courseBuilderController.getInstance((Course)adapterView.getItemAtPosition(position));
                Intent intent = new Intent(courseBuildCourseSelector.this, courseBuilderHoleSelector.class);
                startActivity(intent);
            }
        };
    }

    private View.OnClickListener saveCourseClick(){
        return new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                courseBuilder.getCourse().saveCourse(getBaseContext());
            }
        };
    }
}
