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

import com.example.nate.golfonthego.Controllers.Adapters.holeListAdapter;
import com.example.nate.golfonthego.Models.Hole;

import java.util.ArrayList;

public class courseBuilderHoleSelector extends AppCompatActivity {
    private ArrayList<Hole> holes;
    private ArrayAdapter<Hole> holeAdapter;
    private ListView holeListView;
    private Button btnAddHole;
    private Button btnSaveCourse;
    public static final String tag_current_hole = "currentHole";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_builder_hole_selector);

        holes = courseBuildCourseSelector.courseBuilder.getHoles();
        holeAdapter = new holeListAdapter(this, holes);
        holeListView = findViewById(R.id.lstBuiltHoles);
        btnAddHole = findViewById(R.id.btnAddHole);
        btnSaveCourse = findViewById(R.id.btnSaveCourse);

        setupButtons();
    }

    private void setupButtons(){
        holeListView.setAdapter(holeAdapter);
        holeListView.setOnItemClickListener(holeClick());
        btnAddHole.setOnClickListener(newHoleClick());
        btnSaveCourse.setOnClickListener(saveCourseClick());
    }


    private View.OnClickListener newHoleClick(){
        return new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Hole newHole = new Hole(courseBuildCourseSelector.courseBuilder.getCourse());
                courseBuildCourseSelector.courseBuilder.AddHole(newHole);
                Intent intent = new Intent(courseBuilderHoleSelector.this, CourseBuilder.class);
                intent.putExtra(tag_current_hole, holes.size() - 1);
                startActivity(intent);

                //update the courses list
                holeAdapter.notifyDataSetChanged();
            }
        };
    }

    private AdapterView.OnItemClickListener holeClick() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(courseBuilderHoleSelector.this, CourseBuilder.class);
                intent.putExtra(tag_current_hole, position);
                startActivity(intent);
            }
        };
    }

    private View.OnClickListener saveCourseClick(){
        return new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                courseBuildCourseSelector.courseBuilder.getCourse().saveCourse(getBaseContext());
            }
        };
    }
}
