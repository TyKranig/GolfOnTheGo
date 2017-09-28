package com.example.nate.golfonthego;

import java.util.ArrayList;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.Sensor;
import android.app.Activity;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;

import Constants.ConstantURL;


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


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer_test);

        //Sensor manager and accelerometer
        SM = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelSensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        SM.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_GAME);

        //setting up both buttons
        Button testButton = (Button)findViewById(R.id.test);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if(push < 1) {
                    push ++;
                    buttonPush();
                }
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent)
    {
        xAcc = sensorEvent.values[0];
        yAcc = sensorEvent.values[1];
        zAcc = sensorEvent.values[2];

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

        xStat.add("Time, X");
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

                //String swingUrl = ConstantURL.URL_REGISTER + "XMax=\"" + maxX + "\"&XMin=\"" + minX + "\"&XAverage=\"" + avgX
                //        + "YMax=\"" + maxY + "\"&YMin=\"" + minY + "\"&YAverage=\"" + avgY
                //        + "ZMax=\"" + maxZ + "\"&ZMin=\"" + minZ + "\"&ZAverage=\"" + avgZ + "\"";

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

                ArrayAdapter fullAdapter = new ArrayAdapter<String>(AccelerometerTest.this, R.layout.activity_list, R.id.textView, allStat);
                ListView fullList = (ListView) findViewById(R.id.fullList);
                fullList.setAdapter(fullAdapter);

                ArrayAdapter adapter = new ArrayAdapter<String>(AccelerometerTest.this, R.layout.activity_list, R.id.textView, swingStat);
                ListView popup = (ListView) findViewById(R.id.popup);
                popup.setAdapter(adapter);

                swingStat = new ArrayList<String>();
                allStat = new ArrayList<String>();
                xStat = new ArrayList<String>();
                yStat = new ArrayList<String>();
                zStat = new ArrayList<String>();

            }

        };
        timer.start();
        //
    }

}
