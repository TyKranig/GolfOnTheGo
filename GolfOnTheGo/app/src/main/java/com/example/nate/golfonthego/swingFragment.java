package com.example.nate.golfonthego;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.Toast;

import static android.content.Context.SENSOR_SERVICE;

public class swingFragment extends Fragment implements SensorEventListener{

    private Button swingFragButton;
    private Button swingFragBackButton;
    public CourseActivity Course;
    private OnFragmentInteractionListener mListener;    //sensor stuff
    private Sensor accelSensor;
    private SensorManager SM;
    private Swinger playerSwing;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //Sensor manager and accelerometer
        SM = (SensorManager)getContext().getSystemService(SENSOR_SERVICE);
        accelSensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        SM.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_GAME);
        Course = (CourseActivity) getActivity();

        playerSwing = new Swinger();
        View swingFragView = inflater.inflate(R.layout.fragment_swing, container, false);
        System.out.println("SwingTrack = " + playerSwing.swingTrack);
        swingFragButton = (Button)swingFragView.findViewById(R.id.swingFragSwing);
        swingFragButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                
                if (playerSwing.swingTrack == 0) {
                    playerSwing.swingTrack = 1;
                    System.out.println("SwingTrack = " + playerSwing.swingTrack);
                }
            }
        });
        swingFragBackButton = (Button)swingFragView.findViewById(R.id.swingFragBack);
        swingFragBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                getActivity().getFragmentManager().popBackStack();
            }
        });
        return swingFragView;
    }

    //Leo's accelerometer stuff
    @Override
    public void onSensorChanged(SensorEvent sensorEvent)
    {
        //saving accelerometer values
        playerSwing.x = sensorEvent.values[0];
        playerSwing.y = sensorEvent.values[1];
        playerSwing.z = sensorEvent.values[2];

        playerSwing.swang();

        if(playerSwing.done()){
            CharSequence text = "Power:     " + playerSwing.power +
                    "\nOverswing:  " + playerSwing.overswing +
                    "\nError:      " + playerSwing.error +
                    "\nScore:      " + playerSwing.score;
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(getContext(), text, duration);
            toast.show();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i)
    {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        System.out.println("Attached");
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;

        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
