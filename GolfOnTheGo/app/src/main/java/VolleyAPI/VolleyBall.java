package VolleyAPI;

import android.app.Activity;
import android.content.Context;
import android.provider.Settings;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nate.golfonthego.MainScreen;

import org.json.JSONObject;

/**
 * Created by Tyler on 9/23/17 volley stuffs.
 * Used to call the volley utility
 */

public class VolleyBall {
    private JSONObject theStuff;
    public boolean Login(Context context, String userName, String password){
        VolleyCallback callback = new VolleyCallback() {
            @Override
            public void setResult(JSONObject result) {
                theStuff = result;
                System.out.println(result.toString());
                System.out.println("---------->" + theStuff.toString());
            }
        };

        System.out.println(userName + " " + password);
        getResponse(context, callback, "http://proj-309-am-c-1.cs.iastate.edu/login.php?userName=\"" + userName + "\"&password=\"" + password + "\"");

        int result = 0;
        try{
            System.out.println(theStuff.toString());
            result = (int)theStuff.get("result");
            System.out.println(result);
        }
        catch (Exception e){
            System.out.println(e.toString());
        }

        return result == 1;
    }

    public static void getResponse(Context context, final VolleyCallback callback, String url){
        final JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());
                        try{
                            callback.setResult(response);
                        }
                        catch (Exception e){
                            System.out.println(e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.toString());
                    }
                });

        RequestQueueSingleton.getInstance(context).addToRequestQueue(jsObjRequest);
    }

    public interface VolleyCallback {
        void setResult(JSONObject result);
    }
}
