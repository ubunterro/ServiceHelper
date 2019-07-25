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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBAgent {

    static String baseUrl = "http://ubunterro.ru/service.php";//"http://192.168.0.101/serviceServer/rest.php?code=123";


    //TODO



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

    public static void setContext(Context context) {
        DBAgent.context = context;
        //TODO change to a proper address
        baseUrl = SettingsManager.getServer(context)+"?code=" + SettingsManager.getLogin(context) + "&cmd=list";

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
                     r.setDef(jRepair.getString("def"));
                     r.setDescription(jRepair.getString("desc"));
                     r.setRecv(jRepair.getString("recv"));
                     //r.setStatus();

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
                            Log.d("Volley", "NoConnectionError");
                        } else if (error instanceof AuthFailureError) {
                            Log.d("Volley", "AuthFailureError");
                        } else if (error instanceof ServerError) {
                            Log.d("Volley", "ServerError");
                        } else if (error instanceof NetworkError) {
                            Log.d("Volley", "NetworkError");
                        } else if (error instanceof ParseError) {
                            Log.d("Volley", "ParseError");
                        }


                    }
                });

// Access the RequestQueue through your singleton class.
        queue.add(jsonObjectRequest);
    }



    // A request without a callback
    public static void makePostRequest(String url, final Map<String, String> paramsIn){
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Volley", "post ok");
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Volley", "error post");
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = paramsIn;
                return params;
            }
        };

        queue.add(postRequest);
    }

    public static void getLastRepairs(){
        List<Repair> repairs = new ArrayList<Repair>();
        makeRequest(baseUrl);

    }



    public static void getRepairInfo(){

    }

    public static void setRepairInfo(){

    }
}
