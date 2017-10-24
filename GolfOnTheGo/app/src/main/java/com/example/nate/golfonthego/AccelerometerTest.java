package com.example.nate.golfonthego;

import java.util.ArrayList;
<<<<<<< HEAD

import android.content.Intent;
=======
>>>>>>> f7577ed2daf10b26d6618611403dc0513fc3ce18
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.Sensor;
<<<<<<< HEAD
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.app.Activity;
=======
import android.app.Activity;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
>>>>>>> f7577ed2daf10b26d6618611403dc0513fc3ce18
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;
<<<<<<< HEAD
=======
import android.widget.Toast;
>>>>>>> f7577ed2daf10b26d6618611403dc0513fc3ce18

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
    private ArrayAdapter fullAdapter;
    private ArrayList<String> swingStat;
    private ArrayList<String> xStat;
    private ArrayList<String> yStat;
    private ArrayList<String> zStat;
    private ArrayList<String> allStat;

    private ListView popup;
    private CountDownTimer timer;

    //Sensor objects
    private Sensor accelSensor;
    private SensorManager SM;

    //swing bool
    private int push = 0;

    private float xAcc;
    private float yAcc;
    private float zAcc;

<<<<<<< HEAD
    //logic objects
    private float power;
    private float overswing;
    private float swingScore;
    private float error = 0;
    private float backSwingVal = -15;
    private int backSwing = 0;

    private float maxX, maxY, maxZ, minX, minY, minZ, avgX, avgY, avgZ = 0;
=======
    private float swingThroughVal;
    private float backSwingVal;
    private int swingThroughCount;
    private boolean backSwing = false;
>>>>>>> f7577ed2daf10b26d6618611403dc0513fc3ce18


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer_test);

        //Sensor manager and accelerometer
        SM = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelSensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        SM.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_GAME);

<<<<<<< HEAD
=======
        backSwingVal = - 25;
        swingThroughVal = 25;
        swingThroughCount = 0;

>>>>>>> f7577ed2daf10b26d6618611403dc0513fc3ce18
        //setting up both buttons
        Button testButton = (Button)findViewById(R.id.test);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
<<<<<<< HEAD
                    buttonPush();
            }
        });

        Button backButton = (Button)findViewById(R.id.testToMain);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent testToMain = new Intent(AccelerometerTest.this, MainActivity.class);
                startActivity(testToMain);
=======
                if(push < 1) {
                    push ++;
                    buttonPush();
                }
>>>>>>> f7577ed2daf10b26d6618611403dc0513fc3ce18
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent)
    {
<<<<<<< HEAD
        //saving accelerometer values
=======
>>>>>>> f7577ed2daf10b26d6618611403dc0513fc3ce18
        xAcc = sensorEvent.values[0];
        yAcc = sensorEvent.values[1];
        zAcc = sensorEvent.values[2];

        //start the swing logic on a backswing
<<<<<<< HEAD
        if(xAcc + zAcc < backSwingVal && backSwing == 1){
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
        if(backSwing == 3 && xAcc <= 0 && zAcc <= 0){

            //calculating power, overswing, error, and swingscore.
            power = maxX + maxZ;
            overswing = ((maxX + maxZ) - (avgX + avgZ)) - 70;

            //error is based solely on Y acceleration
            error = avgY - 10;
            overswing = overswing < 0? 0 : overswing;

            swingScore = power - overswing;

            backSwing = 0;
            push = 0;

            swingStat.add("Power:       " + power + "\n");
            swingStat.add("Overswing:   " + overswing + "\n");
            swingStat.add("Error:       " + error + "\n");
            swingStat.add("Score:       " + swingScore + "\n");

            ArrayAdapter adapter = new ArrayAdapter<String>(AccelerometerTest.this, R.layout.activity_list, R.id.textView, swingStat);
            ListView popup = (ListView) findViewById(R.id.popup);
            popup.setAdapter(adapter);
=======
        if(xAcc < backSwingVal){
            backSwing = true;
        }

        //after the swing starts...
        if(backSwing && xAcc > swingThroughVal){
            swingThroughVal -= 5;
            swingThroughCount ++;
        }
        else{
            swingThroughCount --;
>>>>>>> f7577ed2daf10b26d6618611403dc0513fc3ce18
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i)
    {

    }

    //when either button is pushed
    public void buttonPush()
    {
        swingStat = new ArrayList<String>();
        allStat = new ArrayList<String>();
        xStat = new ArrayList<String>();
        yStat = new ArrayList<String>();
        zStat = new ArrayList<String>();

<<<<<<< HEAD
        float maxX, maxY, maxZ, minX, minY, minZ, avgX, avgY, avgZ = 0;

        backSwing = 1;
        /*xStat.add("Time, X");
=======
        backSwing = false;
        xStat.add("Time, X");
>>>>>>> f7577ed2daf10b26d6618611403dc0513fc3ce18
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

<<<<<<< HEAD
                //putting stats into the all stat ArrayList
=======
>>>>>>> f7577ed2daf10b26d6618611403dc0513fc3ce18
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

<<<<<<< HEAD
                //adapters filling the listviews with text
=======
>>>>>>> f7577ed2daf10b26d6618611403dc0513fc3ce18
                ArrayAdapter fullAdapter = new ArrayAdapter<String>(AccelerometerTest.this, R.layout.activity_list, R.id.textView, allStat);
                ListView fullList = (ListView) findViewById(R.id.fullList);
                fullList.setAdapter(fullAdapter);

                ArrayAdapter adapter = new ArrayAdapter<String>(AccelerometerTest.this, R.layout.activity_list, R.id.textView, swingStat);
                ListView popup = (ListView) findViewById(R.id.popup);
                popup.setAdapter(adapter);

<<<<<<< HEAD
                //reinitializing all ArrayLists
=======


>>>>>>> f7577ed2daf10b26d6618611403dc0513fc3ce18
                swingStat = new ArrayList<String>();
                allStat = new ArrayList<String>();
                xStat = new ArrayList<String>();
                yStat = new ArrayList<String>();
<<<<<<< HEAD
                zStat = new ArrayList<String>();*/

            }

        }
        //timer.start();




=======
                zStat = new ArrayList<String>();

            }

        };
        timer.start();
        //
    }

}
>>>>>>> f7577ed2daf10b26d6618611403dc0513fc3ce18
