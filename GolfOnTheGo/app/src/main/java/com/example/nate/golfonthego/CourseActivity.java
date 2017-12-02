package com.example.nate.golfonthego;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.nate.golfonthego.Models.Course;
import com.example.nate.golfonthego.Models.GolfBag;
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
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.example.nate.golfonthego.R.id.holeNumText;
import static com.example.nate.golfonthego.R.id.map;

public class CourseActivity extends FragmentActivity implements OnMapReadyCallback,
        ConnectionCallbacks, OnConnectionFailedListener, swingFragment.OnFragmentInteractionListener {

    // buttons for the activity
    private Button swingButton;
    // where gameplay logic resides
    private Gameplay SwingGame = Gameplay.getGameplay();
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
    Marker teemarker;

    public TextView clubTextView;
    public TextView holeNumTextView;
    public TextView scoreTextView;
    private int duplicateBall = 0;
    public LatLng ballMarker;
    public Course currentCourse;
    public int currentHole;
    public Marker tempTeeMarker;

    // golf bag with club
    private GolfBag bag;

    /*final LatLng ballMarker, final Course currentCourse,
    final int currentHole, final Marker tempTeeMarker*/

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

        holeNumTextView = findViewById(R.id.holeNumText);
        holeNumTextView.setVisibility(View.INVISIBLE);

        scoreTextView = findViewById(R.id.scoreText);
        scoreTextView.setVisibility(View.INVISIBLE);

        clubTextView = findViewById(R.id.clubText);
        clubTextView.setVisibility(View.INVISIBLE);

        bag = GolfBag.getBag();
    }

    // needed for fragment interaction
    @Override
    public void onFragmentInteraction(Uri uri) {

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
        // set the google map object and attempt to add the map style
        mMap = googleMap;
        setMapStyle();

        //pick a course to load in, eventually will be extended to be based on savedIntsanceState
        currentCourse = new Course(2);
        currentHole = 1;

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentCourse.getTee(1), (float)19.0));
        // this is the instantiation of the player marker, updates position when permissed.
        livePlayerMarker = mMap.addMarker(
                new MarkerOptions().position(currentCourse.getTee(currentHole)).title("You are here"));
        tempTeeMarker = mMap.addMarker(
                new MarkerOptions().position(currentCourse.getTee(currentHole)).title("Move Here to Play"));
        // tee marker on the map
        Bitmap startBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.course_start);
        BitmapDescriptor startBitmapDescriptor = BitmapDescriptorFactory.fromBitmap(startBitmap);
        tempTeeMarker.setIcon(startBitmapDescriptor);

        initializeButtons();
        if(!SwingGame.gamePlayInProgress)
            ballMarker = currentCourse.getTee(currentHole);
            locationBasedContent();
        drawHole(currentCourse, currentHole);
        System.out.println("onmapready completed");
    }

    // sets the style of the google map to the custom style
    public void setMapStyle(){
        boolean mapStyleSuccess = mMap.setMapStyle(new MapStyleOptions(getResources().getString(R.string.map_style)));
        if(!mapStyleSuccess) {
            Context context = getApplicationContext();
            CharSequence text = "Style Input Failed";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    // initializes all the buttons on the screen
    public void initializeButtons(){
        // swing button top left of screen
        swingButton = (Button)findViewById(R.id.swingButton);
        swingButton.setVisibility(View.GONE);
        swingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()))
                        .zoom(20)                   // Sets the zoom
                        .bearing(0)                 // Rotates orientation
                        .tilt(0)                    // Sets the tilt of the camera to 0 degrees
                        .build();                   // Creates a CameraPosition from the builder
                //mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                FragmentManager tempfrag = getSupportFragmentManager();
                SwingGame.executeSwing(swingButton, tempfrag);
            }
        });
    }

    // all content based on the updating location from the user
    public void locationBasedContent(){
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    // set the pin that represents the users location
                    livePlayerMarker.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));
                    // set a marker where the tee is for the given course

                    setScoreText();

                    Location teeLocation = new Location("tmp");
                    if(!currentCourse.getBall().hasTeedOff) {
                        teeLocation.setLatitude(tempTeeMarker.getPosition().latitude);
                        teeLocation.setLongitude(tempTeeMarker.getPosition().longitude);
                    }

                    currentLocation = location;
                    LinearLayout ll = (LinearLayout)findViewById(R.id.swingLayout);

                    // if the distance  between the player and the first tee is less than 20 meters
                    // any more precise may cause issues due to the inconsistency of
                    // fine location gps -nate
                    if(currentCourse.getBall().getCurrentBallLocation().distanceTo(currentCourse.flagLocation) < 10){
                        //TODO : start putting stuff
                        currentCourse.resetScore(currentHole);
                        setScoreText();
                        // check to see if there is a next hole
                        currentHole++;
                        if(!(currentCourse.getTee(currentHole) != null))
                            endGame();
                    }
                    else if(location.distanceTo(teeLocation) < 2000 && !SwingGame.gamePlayInProgress && !currentCourse.getBall().hasTeedOff){
                        if(duplicateBall > 0){
                            teemarker.remove();
                            duplicateBall --;
                        }
                        Bitmap ballMap = BitmapFactory.decodeResource(getResources(), R.mipmap.ballmarker);
                        BitmapDescriptor ballBitmap = BitmapDescriptorFactory.fromBitmap(ballMap);
                        teemarker = mMap.addMarker(
                                new MarkerOptions().position(currentCourse.getTee(currentHole)).title("start here!"));
                        teemarker.setIcon(ballBitmap);
                        tempTeeMarker.remove();
                        // the following needs to be changed to be more modular
                        SwingGame.setParameters(mMap, teemarker, getApplicationContext(),2,1, currentCourse);
                        SwingGame.gamePlayInProgress = true;
                        //button appears
                        swingButton.setVisibility(View.VISIBLE);
                        swingButton.setText("Swing");
                        duplicateBall ++;
                    }
                    else if(location.distanceTo(currentCourse.getBall().getCurrentBallLocation()) < 2000){
                        // the following needs to be changed to be more modular
                        SwingGame.setParameters(mMap, teemarker, getApplicationContext(),2,1, currentCourse);
                        SwingGame.gamePlayInProgress = true;
                        //button appears
                        swingButton.setVisibility(View.VISIBLE);
                        swingButton.setText("Swing");
                    }
                    else{
                        SwingGame.gamePlayInProgress = false;
                        swingButton.setVisibility(View.INVISIBLE);
                        swingButton.setVisibility(View.GONE);
                        mMap.getUiSettings().setAllGesturesEnabled(true);
                    }
                }
            };
        };
    }

    public void endGame() {
        int totalScore = currentCourse.getTotalScore();
        Context context = getApplicationContext();
        CharSequence text = "Mah Dude! you \n"
         + "finished the course with a score of: " + totalScore;
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }

    // draws the current hole that the user is on
    public void drawHole(Course currentCourse, int currentHole){
        PolygonOptions hole1 = new PolygonOptions().addAll(
                currentCourse.getFairway(currentHole)).fillColor(Color.GREEN).strokeJointType(2)
                .strokeWidth((float)10).strokeColor(Color.GREEN);
        Polygon holePolygon1 = mMap.addPolygon(hole1);
        holePolygon1.setZIndex(0);
        PolygonOptions green1 = new PolygonOptions().addAll(currentCourse.getGreen(currentHole))
                .fillColor(Color.rgb((float)19, (float)82, (float)25));
        Polygon greenPolygon1 = mMap.addPolygon(green1);
        greenPolygon1.setZIndex(1);
        mMap.addMarker(new MarkerOptions().position(currentCourse.getHoleLocation(currentHole)));
    }

    public void setScoreText(){
        scoreTextView.setText("" + currentCourse.getScore(currentHole));
        scoreTextView.setVisibility(View.VISIBLE);

        holeNumTextView.setText("Hole: " + currentHole);
        holeNumTextView.setVisibility(View.VISIBLE);

        clubTextView.setText("Club: " + bag.clubName);
        clubTextView.setVisibility(View.VISIBLE);
    }

    // the next section of code includes connection methods for google api calls
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

    /*@Override
    public void onLocationChanged(Location location){
        currentLocation = location;
        LatLng tmp = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(tmp).title("You might be here!"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tmp, (float)19.0));

    }*/

    // The remaining is allowing for persistent location permissions for the app across life cycles
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
