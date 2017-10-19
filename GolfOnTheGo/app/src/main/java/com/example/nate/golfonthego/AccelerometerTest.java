package com.example.nate.golfonthego;

import java.util.ArrayList;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import Constants.ConstantURL;
import VolleyAPI.RequestQueueSingleton;


public class AccelerometerTest extends AppCompatActivity implements SensorEventListener{

    private Button testButton;

    private ArrayAdapter adapter;
    private ArrayList<String> swingStat;

    private ListView popup;

    //Sensor objects
    private Sensor accelSensor;
    private SensorManager SM;

    //swinger object
    private Swinger playerSwing;

    //logic objects
    private float power;
    private float overswing;
    private float swingScore;
    private float error;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer_test);

        //Sensor manager and accelerometer
        SM = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelSensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        SM.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_GAME);

        playerSwing = new Swinger();
        //setting up both buttons
        Button testButton = (Button)findViewById(R.id.test);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                    buttonPush();
            }
        });

        Button backButtonSwing = (Button)findViewById(R.id.testToMain);
        backButtonSwing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent testToMain = new Intent(AccelerometerTest.this, MainActivity.class);
                startActivity(testToMain);
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent)
    {

        //saving accelerometer values
        playerSwing.x = sensorEvent.values[0];
        playerSwing.y = sensorEvent.values[1];
        playerSwing.z = sensorEvent.values[2];

        if(playerSwing.swingTrack != 0) {
            playerSwing.swang();
        }

        if(playerSwing.done()){
            swingStat = new ArrayList<String>();
            swingStat.add("Power:      " + playerSwing.power);
            swingStat.add("Overswing:  " + playerSwing.overswing);
            swingStat.add("Error:      " + playerSwing.error);
            swingStat.add("Score:      " + playerSwing.score);
            ArrayAdapter adapter = new ArrayAdapter<String>(AccelerometerTest.this, R.layout.activity_list, R.id.textView, swingStat);
            ListView popup = (ListView) findViewById(R.id.popup);
            popup.setAdapter(adapter);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i)
    {

    }

    //when either button is pushed
    public void buttonPush()
    {

        if (playerSwing.swingTrack == 0) {
            playerSwing.swingTrack = 1;
        }


        /*xStat.add("Time, X");
        yStat.add("Time, Y");
        zStat.add("Time, Z");

        timer = new CountDownTimer(2000, 50) {
            float maxX, maxY, maxZ, minX, minY, minZ, avgX, avgY, avgZ = 0;

            //on tick:
            //update float vals
            @Override
            public void onTick(long millisUntilFinished) {

                int tick = (int) (2000 - millisUntilFinished);

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


                xStat.add(tick + " ms,   " + xAcc);
                yStat.add(tick + " ms,   " + yAcc);
                zStat.add(tick + " ms,   " + zAcc);
            }

            //after countdown, add series to graph and change button text.
            @Override
            public void onFinish() {

                push = 0;

                //Swing stats logic
                swingStat.add("Max X Value: " + maxX);
                swingStat.add("Min X Value: " + minX);
                swingStat.add("Avg X Value: " + avgX);

                swingStat.add("Max Y Value: " + maxY);
                swingStat.add("Min Y Value: " + minY);
                swingStat.add("Avg Y Value: " + avgY);

                swingStat.add("Max Z Value: " + maxZ);
                swingStat.add("Min Z Value: " + minZ);
                swingStat.add("Avg Z Value: " + avgZ);

                //putting stats into the all stat ArrayList
                while(!xStat.isEmpty()){
                    allStat.add(xStat.get(0));
                    xStat.remove(0);
                }
                while(!yStat.isEmpty()){
                    allStat.add(yStat.get(0));
                    yStat.remove(0);
                }
                while(!zStat.isEmpty()){
                    allStat.add(zStat.get(0));
                    zStat.remove(0);
                }

                String swingUrl = ConstantURL.URL_STATS + "XMax=\""+maxX+"\"&XMin=\""+minX+"\"&XAverage=\""+avgX+"\"&YMax=\""+maxY+"\"&YMin=\""+minY+"\"&YAverage=\""+avgY+"\"&ZMax=\""+maxZ+"\"&ZMin=\""+minZ+"\"&ZAverage=\""+avgZ+"\"";
                JsonObjectRequest registerRequest = new JsonObjectRequest
                        (Request.Method.GET, swingUrl, null, new Response.Listener<JSONObject>(){
                            @Override
                            public void onResponse(JSONObject response) {
                                try{
                                    System.out.println(response.toString());
                                    if(response.getInt("result") == 1){
                                        finish();
                                    }
                                } catch (Exception e){
                                    System.out.println(e.toString());
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                System.out.println(error.toString());
                            }
                        });

                RequestQueueSingleton.getInstance(AccelerometerTest.this).addToRequestQueue(registerRequest);

                //adapters filling the listviews with text
                ArrayAdapter fullAdapter = new ArrayAdapter<String>(AccelerometerTest.this, R.layout.activity_list, R.id.textView, allStat);
                ListView fullList = (ListView) findViewById(R.id.fullList);
                fullList.setAdapter(fullAdapter);

                ArrayAdapter adapter = new ArrayAdapter<String>(AccelerometerTest.this, R.layout.activity_list, R.id.textView, swingStat);
                ListView popup = (ListView) findViewById(R.id.popup);
                popup.setAdapter(adapter);

                //reinitializing all ArrayLists
                swingStat = new ArrayList<String>();
                allStat = new ArrayList<String>();
                xStat = new ArrayList<String>();
                yStat = new ArrayList<String>();
                zStat = new ArrayList<String>();*/

            }

        }
        //timer.start();




