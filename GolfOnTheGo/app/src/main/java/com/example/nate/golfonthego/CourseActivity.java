package com.example.nate.golfonthego;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.example.nate.golfonthego.R.id.map;
import static com.example.nate.golfonthego.R.id.swingLayout;

public class CourseActivity extends FragmentActivity implements OnMapReadyCallback,
        ConnectionCallbacks, OnConnectionFailedListener, LocationListener, SensorEventListener {

    //LEO'S VARIABLES
    // put extra sent to accelerometertest
    private ArrayList<String> swingStat;
    //swing bool
    private int push = 0;
    //accelerations vals
    private float xAcc;
    private float yAcc;
    private float zAcc;
    private Button swingButton;
    //logic objects leading to the final swingScore
    private float power;
    private float overswing;
    private float swingScore;
    private float error = 0;
    private float backSwingVal = -20;
    private int backSwing = 0;
    //swing statistics to be tracked
    private float maxX, maxY, maxZ, minX, minY, minZ, avgX, avgY, avgZ = 0;

    // main google map object
    private GoogleMap mMap;
    // api for getting location
    private FusedLocationProviderApi fusedLocationProvider = LocationServices.FusedLocationApi;
    //api for google api's lol
    private GoogleApiClient googleApiClient;
    // for getting locations
    private LocationCallback locationCallback = new LocationCallback();
    // the actual location variable
    Location currentLocation;
    // current location marker
    Marker livePlayerMarker;
    Marker ballmarker;

    //sensor stuff
    private Sensor accelSensor;
    private SensorManager SM;

    // request for location
    LocationRequest locationRequest;
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    protected void createLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    // main on create of the activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Sensor manager and accelerometer
        SM = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelSensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        SM.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_GAME);
        // create the location request
        this.checkLocationPermission();
        this.createLocationRequest();
        // create the api connection
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        //set the map view
        setContentView(R.layout.activity_course);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);

    }

    //Leo's accelerometer stuff
    public void onSensorChanged(SensorEvent sensorEvent)
    {
        /*Backswing is the variable keeping track of the stages of the swing.
        *   0: the swing has not started yet.
        *   1: the user has indicated somehow they want to swing. (e.g. button)
        *   2: the user has started the swing by swinging back past the backSwingVal threshold.
        *   3: the user has started to swing forward, so the acc values are being tracked and calculated.
        *   0: back to 0 when the user has stopped swinging, or started swinging back again ( X + Z <=0 ) */

        //saving accelerometer values
        xAcc = sensorEvent.values[0];
        yAcc = sensorEvent.values[1];
        zAcc = sensorEvent.values[2];

        //start the swing logic on a backswing
        if(backSwing == 1 && xAcc + zAcc < backSwingVal){
            backSwing = 2;
        }
        if(backSwing == 2 && xAcc > 0 && zAcc > 0){
            backSwing = 3;
        }

        //after the swing starts...
        if(backSwing == 3){
            avgX = (avgX + xAcc) / 2;
            avgY = (avgY + yAcc) / 2;
            avgZ = (avgZ + zAcc) / 2;
            if(xAcc > maxX){
                maxX = xAcc;
            }
            if(xAcc < minX) {
                minX = xAcc;
            }
            if(yAcc > maxY){
                maxY = yAcc;
            }
            if(yAcc < minY){
                minY = yAcc;
            }
            if(zAcc > maxZ){
                maxZ = zAcc;
            }
            if(zAcc < minZ){
                minZ = zAcc;
            }

        }
        //end swing
        if(backSwing == 3 && xAcc + zAcc <= 0){

            //calculating power, overswing, error, and swingscore.
            power = maxX + maxZ;
            overswing = ((maxX + maxZ) - (avgX + avgZ)) - 70;

            //error is based solely on Y acceleration
            error = avgY - 10;
            error = error < 0? 0 : error;
            overswing = overswing < 0? 0 : overswing;

            swingScore = power - overswing;

            backSwing = 0;
            push = 0;



            swingStat.add(power + "\n");
            swingStat.add(overswing + "\n");
            swingStat.add(error + "\n");
            swingStat.add(swingScore + "\n");

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i)
    {

    }

    // overriden to start the googleapiclient
    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    // overriden to stop the googleapiclient
    @Override
    protected void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        boolean mapStyleSuccess = mMap.setMapStyle(new MapStyleOptions(getResources().getString(R.string.map_style)));

        if(!mapStyleSuccess) {
            Context context = getApplicationContext();
            CharSequence text = "Style Input Failed";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

        final LatLng TEST = new LatLng(42.021679, -93.677612);

        //pick a course to load in, eventually will be extended to be based on savedIntsanceState
        final Course currentCourse = new Course(1);
        final int currentHole = 1;

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentCourse.getTee(currentHole), (float)19.0));
        // this is the instantiation of the player marker, updates position when permissed.
        livePlayerMarker = mMap.addMarker(
                new MarkerOptions().position(currentCourse.getTee(currentHole)).title("You are here"));
        final Marker tempTeeMarker = mMap.addMarker(
                new MarkerOptions().position(currentCourse.getTee(currentHole)).title("Move Here to Play"));
        // tee marker on the map
        Bitmap startBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.course_start);
        BitmapDescriptor startBitmapDescriptor = BitmapDescriptorFactory.fromBitmap(startBitmap);
        tempTeeMarker.setIcon(startBitmapDescriptor);

        // where the magic happens, location callbacks and updating UI
        // initialize button, make it invisible
        swingButton = (Button)findViewById(R.id.swingButton);
        swingButton.setVisibility(View.GONE);
        swingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                                /*if(push < 1) {
                                    push ++;
                                    float maxX, maxY, maxZ, minX, minY, minZ, avgX, avgY, avgZ = 0;
                                    backSwing = 1;
                                }*/
                Intent swingFinishIntent = new Intent(CourseActivity.this, AccelerometerTest.class);
                swingFinishIntent.putExtra("SwingStats", swingStat);
                startActivity(swingFinishIntent);
            }
        });

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    livePlayerMarker.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));
                    //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tmp, (float)19.0));
                    Location teeLocation = new Location("tmp");
                    teeLocation.setLatitude(TEST.latitude);
                    teeLocation.setLongitude(TEST.longitude);
                    LinearLayout ll = (LinearLayout)findViewById(R.id.swingLayout);
                    // if the distance  between the player and the first tee is less than 10 meters
                    if(location.distanceTo(teeLocation) < 10){
                        Bitmap ballMap = BitmapFactory.decodeResource(getResources(), R.mipmap.ballmarker);
                        BitmapDescriptor ballMarker = BitmapDescriptorFactory.fromBitmap(ballMap);
                        ballmarker = mMap.addMarker(
                                new MarkerOptions().position(currentCourse.getTee(currentHole)).title("start here!"));
                        ballmarker.setIcon(ballMarker);
                        tempTeeMarker.remove();

                        //button appears
                        swingButton.setVisibility(View.VISIBLE);
                        swingButton.setText("Swing");
                    }
                    else{
                        swingButton.setVisibility(View.INVISIBLE);
                        swingButton.setVisibility(View.GONE);
                    }
                }
            };
        };
        PolygonOptions hole1 = new PolygonOptions().addAll(
                currentCourse.getFairway(currentHole)).fillColor(Color.GREEN).strokeJointType(2)
                .strokeWidth((float)10).strokeColor(Color.GREEN);
        Polygon holePolygon1 = mMap.addPolygon(hole1);
        holePolygon1.setZIndex(0);
        PolygonOptions green1 = new PolygonOptions().addAll(currentCourse.getGreen(currentHole))
                .fillColor(Color.rgb((float)19, (float)82, (float)25));
        Polygon greenPolygon1 = mMap.addPolygon(green1);
        greenPolygon1.setZIndex(1);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Context context = getApplicationContext();
        CharSequence text = "Connection Failed";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    @Override
    public void onConnected(Bundle bundle) {
        startLocationUpdates();
    }

    private void startLocationUpdates() {
        try {
            fusedLocationProvider.requestLocationUpdates(googleApiClient, locationRequest,
                    locationCallback,
                    null /* Looper */);
        } catch(SecurityException e) { e.printStackTrace(); }
    }

    @Override
    public void onLocationChanged(Location location){
        currentLocation = location;
        LatLng tmp = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(tmp).title("You might be here!"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tmp, (float)19.0));

    }

    // The remaining is allowing for persistent lcoation permissions for the app across life cycles
    //
    // requesting permissions for fine location
    //
    //
    //
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(CourseActivity.this,
                                        new String[]{ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Restart the api connection because onmapready
                        //throws an error
                        googleApiClient.disconnect();
                        googleApiClient.connect();
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }
}
