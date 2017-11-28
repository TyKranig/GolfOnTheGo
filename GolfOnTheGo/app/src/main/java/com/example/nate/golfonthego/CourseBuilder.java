package com.example.nate.golfonthego;

import android.content.Context;
import android.graphics.Color;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.nate.golfonthego.Controllers.courseBuilderController;
import com.example.nate.golfonthego.Models.Hole;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;

public class CourseBuilder extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private Button btnAdd;
    private RadioGroup rdoGroup;
    private ArrayList<LatLng> stagedAdds;
    private int currentHoleNum;
    private Hole currentHole;
    private String currentBeingEdited;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_builder);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.courseBuilderMap);
        mapFragment.getMapAsync(this);

        //set the buttons
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(btnAddClick());
        rdoGroup = findViewById(R.id.rdoGroupEditorSelector);
        currentBeingEdited = courseBuilderController.Tee;
        rdoGroup.setOnCheckedChangeListener(rdoChange());

        //setup array list to add the locations to that the user wants
        stagedAdds = new ArrayList<>();

        //get the current hole being edited
        currentHoleNum = (int) getIntent().getExtras().get(courseBuilderHoleSelector.tag_current_hole);
        currentHole = courseBuildCourseSelector.courseBuilder.getHole(currentHoleNum);
    }

    //Things for button control
    private View.OnClickListener btnAddClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(stagedAdds.size() > 0) {
                    courseBuildCourseSelector.courseBuilder.addLatLng(stagedAdds, currentHoleNum, rdoGroup.getCheckedRadioButtonId());
                    currentHole = courseBuildCourseSelector.courseBuilder.getHole(currentHoleNum);
                    drawHole();
                    stagedAdds.clear();
                }
            }
        };
    }

    private RadioGroup.OnCheckedChangeListener rdoChange(){
        return new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                ArrayList<LatLng> points = new ArrayList<>();
                if(i == R.id.rdoTee){
                    currentBeingEdited = courseBuilderController.Tee;
                    points.add(currentHole.getTee());
                }else if (i == R.id.rdoFairway){
                    currentBeingEdited = courseBuilderController.Fairway;
                    points = currentHole.getFairway();
                }else if (i == R.id.rdoGreen){
                    currentBeingEdited = courseBuilderController.Green;
                    points = currentHole.getGreen();
                }

                stagedAdds.clear();
               // mMap.clear();

                Log.i("points", points.toString());

                for(int h = 0; h < points.size(); h++){
                    LatLng latLng = points.get(h);
                    mMap.addMarker(new MarkerOptions().position(latLng));
                }

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentHole.getTee(), 16.0f));
            }
        };
    }

    //Google Maps things
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //set the start to the tee if it exists
        LatLng start;
        if(currentHole.getTee() != null){
            start = currentHole.getTee();
        }
        else{
            start = new LatLng(42.026486, -93.647377);
        }

        mMap.addMarker(new MarkerOptions().position(start));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(start, 17.0f));
        mMap.setOnMapClickListener(mapClickListener());

        setMapStyle();
    }

    private GoogleMap.OnMapClickListener mapClickListener() {
        return new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if(rdoGroup.getCheckedRadioButtonId() == R.id.rdoTee){
                    //mMap.clear();
                    stagedAdds.clear();
                }
                if(stagedAdds.size() == 0){
                    //mMap.clear();
                }

                mMap.addMarker(new MarkerOptions().position(latLng));
                stagedAdds.add(latLng);
            }
        };
    }

    private void setMapStyle(){
        boolean mapStyleSuccess = mMap.setMapStyle(new MapStyleOptions(getResources().getString(R.string.map_style)));
        if(!mapStyleSuccess) {
            Context context = getApplicationContext();
            CharSequence text = "Style Input Failed";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    private void drawHole(){
        if(rdoGroup.getCheckedRadioButtonId() == R.id.rdoFairway){
            PolygonOptions fairwayOpt = new PolygonOptions()
                    .addAll(stagedAdds).fillColor(Color.GREEN).strokeJointType(2).strokeWidth((float)10).strokeColor(Color.GREEN);
            Polygon fairway = mMap.addPolygon(fairwayOpt);
            fairway.setZIndex(0);

        }else if(rdoGroup.getCheckedRadioButtonId() == R.id.rdoGreen){
            PolygonOptions greenOpt = new PolygonOptions()
                    .addAll(stagedAdds).fillColor(Color.GREEN);
            Polygon green = mMap.addPolygon(greenOpt);
            green.setZIndex(1);

        }
    }
}
