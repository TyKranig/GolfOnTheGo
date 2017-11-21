package com.example.nate.golfonthego;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
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
    public static courseBuilderController courseBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_build_course_selector);

        courses = new ArrayList<>();
        courses.add(new Course(1));
        courses.add(new Course(2));

        courseAdapter = new courseListAdapter(this, courses);
        courseListView = findViewById(R.id.lstBuiltHoles);
        courseListView.setAdapter(courseAdapter);

        btnAddCourse = findViewById(R.id.btnAddCourse);
        btnAddCourse.setOnClickListener(newCourseClick());
    }

    View.OnClickListener newCourseClick(){
        return new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Course newCourse = new Course(courses.size());
                courseBuilder = courseBuilderController.getInstance(newCourse);
                Intent intent = new Intent(courseBuildCourseSelector.this, courseBuilderHoleSelector.class);
                startActivity(intent);
            }
        };
    }
}
