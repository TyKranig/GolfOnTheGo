package com.example.nate.golfonthego;

import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

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

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.example.nate.golfonthego.R.id.map;

public class CourseActivity extends FragmentActivity implements OnMapReadyCallback,
        ConnectionCallbacks, OnConnectionFailedListener, LocationListener {

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

        // markers for drawing the first polygon (hole) and a tee box to check distance to
        LatLng hole1a = new LatLng(42.026855, -93.647630);
        LatLng hole1b = new LatLng(42.026499, -93.647619);
        LatLng hole1c = new LatLng(42.026224, -93.647684);
        LatLng hole1d = new LatLng(42.026377, -93.646026);
        LatLng hole1e = new LatLng(42.026356, -93.645405);
        LatLng hole1f = new LatLng(42.026778, -93.645426);
        LatLng hole1g = new LatLng(42.026814, -93.646231);
        LatLng hole1h = new LatLng(42.026655, -93.646950);
        LatLng hole1Tee = new LatLng(42.026486, -93.647377);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hole1a, (float)19.0));
        // this is the instantiation of the player marker, updates position when permissed.
        livePlayerMarker = mMap.addMarker(new MarkerOptions().position(hole1Tee).title("You are here"));

        // where the magic happens, location callbacks and updating UI
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    livePlayerMarker.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));
                    //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tmp, (float)19.0));
                    Location teeLocation = new Location("tmp");
                    teeLocation.setLatitude(hole1Tee.latitude);
                    teeLocation.setLongitude(hole1Tee.longitude);

                    // if the distance  between the player and the first tee is less than 10 meters
                    if(location.distanceTo(teeLocation) < 10){
                        Bitmap ballMap = BitmapFactory.decodeResource(getResources(), R.mipmap.ballmarker);
                        BitmapDescriptor ballMarker = BitmapDescriptorFactory.fromBitmap(ballMap);
                        Marker ballmarker = mMap.addMarker(new MarkerOptions().position(hole1Tee).title("start here!"));
                        ballmarker.setIcon(ballMarker);
                        mMap.addCircle(new CircleOptions().center(hole1Tee).radius((double)10).fillColor(Color.CYAN));
                    }
                }
            };
        };
        PolygonOptions hole1 = new PolygonOptions().add(hole1a, hole1b, hole1c, hole1d, hole1e, hole1f,
                hole1g, hole1h).fillColor(Color.GREEN).strokeJointType(2)
                .strokeWidth((float)10).strokeColor(Color.GREEN);
        Polygon holePolygon1 = mMap.addPolygon(hole1);
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

    //
    //requesting permissions for fine location
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
