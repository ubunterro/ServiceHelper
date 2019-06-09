package ru.ubunterro.servicehelper;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DBAgent {

    final static String baseUrl = "http://ubunterro.ru/service.php";//"http://192.168.0.101/serviceServer/rest.php?code=123";

    private static RequestQueue queue;
    public static Context context;

    public static void setActivity(MainActivity activity) {
        DBAgent.activity = activity;
    }

    private static MainActivity activity;

    public static int initRequestQueue(){
        queue = Volley.newRequestQueue(context);
        return 0;
    }

    private static void requestCallback(JSONObject response){
        try {
             String type = response.get("type").toString();

             if (type.equals("lastRepairs")){
                 Log.w("Volley", type);
                 List<Repair> repairs = new ArrayList<>();

                 JSONArray jRepairs = response.getJSONArray("repairs");

                 for (int i = 0; i < jRepairs.length(); i++){
                     Repair r = new Repair();
                     JSONObject jRepair = jRepairs.getJSONObject(i);

                     r.setId(jRepair.getInt("id"));
                     r.setClient(jRepair.getString("client"));
                     r.setName(jRepair.getString("name"));

                     repairs.add(r);
                 }

                 RepairsStorage.setRepairs(repairs);
                 Log.w("redrawr", "list");
                 activity.redrawList();

             }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public static void makeRequest(String URL){

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //textView.setText("Response: " + response.toString());
                        Log.d("Volley", response.toString());
                        requestCallback(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Log.d("Volley", "pasdloho");
                        } else if (error instanceof AuthFailureError) {
                            Log.d("Volley", "plasdoho");
                        } else if (error instanceof ServerError) {
                            Log.d("Volley", "plohdfgo");
                        } else if (error instanceof NetworkError) {
                            Log.d("Volley", "plodasdho");
                        } else if (error instanceof ParseError) {
                            Log.d("Volley", "pldsadoho");
                        }


                        //Log.d("Volley", "ploho");

                    }
                });

// Access the RequestQueue through your singleton class.
        queue.add(jsonObjectRequest);
    }

    public static void getLastRepairs(){
        List<Repair> repairs = new ArrayList<Repair>();

        makeRequest(baseUrl);//+"&request=lastRepairs"); //TODO govno


    }



    public static void getRepairInfo(){

    }

    public static void setRepairInfo(){

    }
}
