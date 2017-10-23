package com.example.nate.golfonthego;

public class Swinger {

    /*Backswing is the variable keeping track of the stages of the swing.
        *   0: the swing has not started yet.
        *   1: the user has indicated somehow they want to swing. (e.g. button)
        *   2: the user has started the swing by swinging back past the backSwingVal threshold.
        *   3: the user has started to swing forward, so the acc values are being tracked and calculated.
        *   0: when the user has stopped swinging. the done() method will return true after first swing now.*/

    int swingTrack;
    private boolean first;
    float x, y, z;
    private float Xmax, Xmin, Xavg, Ymax, Ymin, Yavg, Zmax, Zmin, Zavg;
    float power, overswing, error, score;
    boolean swingLefty;

    /* scoreArray is a 4 index array where:
        *   0 : power
        *   1 : overswing
        *   2 : error
        *   3 : scoreArray
    */
    float[] scoreArray = new float[4];


    //three parameeters:
    //x, y, and z are acceleration values
    //swingTemp is the backswing value, or int value tracking swing progress.

    public Swinger(boolean orient){
        x = 0;
        y = 0;
        z = 0;
        swingTrack = 0;
        first = true;
        swingLefty = orient;
    }

    public void swang() {

        if(swingTrack == 0){
            return;
        }

        //start the swing logic on a backswing
        if (swingTrack == 1 && (x + z < -20 || x < -15 || z < -15)){
            swingTrack = 2;
            System.out.println("SwingTrack = " + swingTrack);
            return;
        }
        if (swingTrack == 2 && ((x > 0 && z > 0) || x > 10)){
            swingTrack = 3;
            System.out.println("SwingTrack = " + swingTrack);
            return;
        }

        //after the swing starts...
        //calculating max/min/avg
        if (swingTrack == 3) {
            Xavg = (Xavg + x) / 2;
            Yavg = (Yavg + y) / 2;
            Zavg = (Zavg + z) / 2;
            if (x > Xmax) {
                Xmax = x;
            }
            if (x < Xmin) {
                Xmin = x;
            }
            if (y > Ymax) {
                Ymax = y;
            }
            if (y < Ymin) {
                Ymin = y;
            }
            if (z > Zmax) {
                Zmax = z;
            }
            if (z < Zmin) {
                Zmin = z;
            }

        }
        //end swing
        if (swingTrack == 3 && x <= 5 && z <= 5) {

            //calculating power, overswing, error, and score.
            power = Xmax + Zmax;
            overswing = ((Xmax + Zmax) - (Xavg + Zavg)) - 60;

            //error is based solely on Y acceleration
            error = Yavg - 5;
            overswing = overswing < 0 ? 0 : overswing;

            score = power - overswing;

            first = false;
            swingTrack = 0;

            scoreArray[0] = power;
            scoreArray[1] = overswing;
            scoreArray[2] = error;
            scoreArray[3] = score;
        }

    }

    //if swing is over and new swing has not started, returns true
    //else returns false
    public boolean done(){
        System.out.println("" + swingTrack + "  " + first);
        return(!first);
    }

}
