package ru.ubunterro.servicehelper.engine;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
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
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.ubunterro.servicehelper.ui.MainActivity;
import ru.ubunterro.servicehelper.ui.OrdersActivity;
import ru.ubunterro.servicehelper.ui.WarehouseActivity;
import ru.ubunterro.servicehelper.models.Order;
import ru.ubunterro.servicehelper.models.Part;
import ru.ubunterro.servicehelper.models.Repair;

public class DBAgent {

    static String baseUrl = "";//http://ubunterro.ru/service.php";//"http://192.168.0.101/serviceServer/rest.php?code=123";
    static String updateUrl = "http://zip46.ru/servicehelper/version.json";

    // версия сборки
    static final int version = 2;

    //TODO

    public static JSONArray jsonArrayRepairs;

    private static RequestQueue queue;
    public static Context context;
    private static MainActivity activity;

    public static void setActivity(MainActivity activity) {
        DBAgent.activity = activity;
    }

    public static void setOrdersActivity(OrdersActivity ordersActivity) {
        DBAgent.ordersActivity = ordersActivity;
    }

    public static void setWarehouseActivity(WarehouseActivity warehouseActivity) {
        DBAgent.warehouseActivity = warehouseActivity;
    }

    private static OrdersActivity ordersActivity;
    private static WarehouseActivity warehouseActivity;

    public static int initRequestQueue(){
        queue = Volley.newRequestQueue(context);
        return 0;
    }

    public static void setContext(Context context) {
        DBAgent.context = context;
        updateBaseUrl();
    }

    public static void updateBaseUrl(){
        baseUrl = SettingsManager.getServer(context);
        Log.d("SHLP", "Set base url to " + baseUrl);
    }

    @Deprecated
    public static void switchToSecondServer(){
        /*baseUrl = SettingsManager.getServer2(context)+"?code=" + SettingsManager.getLogin(context) + "&cmd=list";
        Log.d("SHelper", "Switched to the second server " + baseUrl);*/
    }

    public static Context getContext(){
        return DBAgent.context;
    }

    private static void requestCallback(JSONObject response){
        try {

            Log.d("SHLP", response.toString());

             String type = response.get("type").toString();
             /*String errorMsg = response.get("error").toString();

             if (!errorMsg.isEmpty()){
                 Log.e("SHLP", errorMsg);
             }
              */

             if (type.equals("lastRepairs")){
                 Log.w("Volley", type);
                 List<Repair> repairs = new ArrayList<>();

                 JSONArray jRepairs = response.getJSONArray("repairs");
                 jsonArrayRepairs = jRepairs;

                 for (int i = 0; i < jRepairs.length(); i++){
                     Repair r = new Repair();
                     // TODO convert to GSON
                     JSONObject jRepair = jRepairs.getJSONObject(i);

                     r.setId(jRepair.getInt("id"));
                     r.setClient(jRepair.getString("client"));
                     r.setName(jRepair.getString("name"));
                     r.setDef(jRepair.getString("def"));
                     r.setDescription(jRepair.getString("desc"));
                     r.setRecv(jRepair.getString("recv"));

                     //TODO wtf
                     try {
                     r.setStatus(Repair.Status.values()[jRepair.getInt("status")]);
                     } catch (JSONException e){
                         r.setStatus(Repair.Status.IN_WORK);
                     }
                     //r.setStatus();

                     repairs.add(r);
                 }

                 Storage.setRepairs(repairs);
                 Log.w("redrawr", "list");
                 activity.redrawList();

                 // смотрим версию
             }
             else if(type.equals("getVersion")){
                 Log.w("Volley", type);
                 Integer lastVersion = Integer.parseInt(response.get("version").toString());
                 Log.e("UPDTR", lastVersion.toString());
                 if (lastVersion > version){
                     activity.doUpdate();
                 }
             } else if (type.equals("editRepair")){
                 Log.d("SHLP", response.get("result").toString());
             } else if (type.equals(("lastOrders"))){
                 //try {
                     List<Order> orders = new ArrayList<>();
                     Gson gsonOrder = new Gson();

                     Type ordersArray = new TypeToken<ArrayList<Order>>(){}.getType();

                     //test
                     //orders.add(new Order(1, "text", new Date(1623075797), "asd"));
                     //orders.add(new Order(2, "text1", new Date(1623075720), "asdf"));

                     Log.d("SHLP", gsonOrder.toJson(orders));

                     orders = gsonOrder.fromJson(String.valueOf(response.getJSONArray("orders")), ordersArray);
                     Storage.setOrders(orders);

                     ordersActivity.redrawList(orders);

                 /*} catch (Exception e){
                     Log.e("SHLP", e.getMessage());
                 }*/
             } else if (type.equals("addOrder")){
                Log.d("SHLP", response.toString());
             } else if (type.equals("lastParts")){
                 List<Part> parts = new ArrayList<>();
                 Gson gsonOrder = new Gson();

                 Type partsArray = new TypeToken<ArrayList<Part>>(){}.getType();

                 Log.d("SHLP", gsonOrder.toJson(parts));

                 parts = gsonOrder.fromJson(String.valueOf(response.getJSONArray("parts")), partsArray);
                 Storage.setParts(parts);

                 warehouseActivity.redrawList(parts);
             } else if (type.equals("auth")){
                 Log.d("SHLP", "authenticated!");
                 SettingsManager.setName(context, response.get("name").toString());
                 SettingsManager.setStatus(context, Integer.parseInt(response.get("status").toString()));

             } else {
                 // no callback yet
                 Log.e("SHLP", "RETURNED " + response.toString());
             }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    /**
     * @param URL request url
     * @param method get or post
     * @param request null if no request body
     */
    public static void makeRequest(String URL, int method, JSONObject request){

        // FIXME big OOF conversion
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (method, URL, request, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //textView.setText("Response: " + response.toString());
                        Log.d("Volley", response.toString());
                        requestCallback(response);
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "" + error.getMessage());

                        try {
                            if (error != null) {
                                if (!error.getMessage().equals("null")) {
                                    MainActivity.showError("NetworkError: " + error.getMessage());
                                }

                                if (error instanceof NoConnectionError) {
                                    Log.d("Volley", "NoConnectionError");
                                } else if (error instanceof TimeoutError) {
                                    Log.d("Volley", "TimeoutError");
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
                        } catch (NullPointerException e){
                            Log.d("Volley", "Null error");
                        }
                    }


                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                // TODO proper auth from settings
                String login = SettingsManager.getLogin(context);
                String password = SettingsManager.getPassword(context);

                String creds = String.format("%s:%s", login, password);
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                params.put("Authorization", auth);
                return params;
            }
        };

        queue.add(jsonObjectRequest);
    }



    // A request without a callback
    @Deprecated
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
        //List<Repair> repairs = new ArrayList<Repair>();

        makeRequest(baseUrl + "/api/", Request.Method.GET, null);

    }

    public static void getLastOrders(){
        makeRequest(baseUrl + "/order/api/", Request.Method.GET, null);
    }

    public static void checkForUpdates(){
        makeRequest(updateUrl, Request.Method.GET, null);
    }


    public static void getRepairInfo(){

    }

    // устанавливает на сервере новые значения
    public static void setRepairInfo(Repair r){
        makeRequest(baseUrl + "/edit", Request.Method.POST, r.toJSONObject());
        Log.e("SHLP", "made req " + r.toJSONObject().toString());

    }

    public static void makeOrder(Order o){
        makeRequest(baseUrl + "/order/create", Request.Method.POST, o.toJSONObject());
        Log.e("SHLP", "made req " + o.toJSONObject().toString());
    }

    public static void getLastParts(){
        makeRequest(baseUrl + "/warehouse/api/", Request.Method.GET, null);
    }

    public static void auth(){
        makeRequest(baseUrl + "/auth/", Request.Method.GET, null);
    }

    public static void setPartInfo(Part part){
        makeRequest(baseUrl + "/warehouse/edit/", Request.Method.POST, part.toJSONObject());
        Log.e("SHLP", "made req set part  " + part.toJSONObject().toString());
    }

    public static void createPart(Part part){
        makeRequest(baseUrl + "/warehouse/create/", Request.Method.POST, part.toJSONObject());
        Log.e("SHLP", "made req create part" + part.toJSONObject().toString());
    }

    public static void createRepair(Repair r) {
        makeRequest(baseUrl + "/create/", Request.Method.POST, r.toJSONObject());
        Log.e("SHLP", "made req create repair" + r.toJSONObject().toString());
    }

    public static byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static void uploadBitmap(final Bitmap bitmap, int id) {
        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, baseUrl + "/warehouse/uploadImg/" + Integer.toString(id),
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            Toast.makeText(context, obj.getString("result"), Toast.LENGTH_SHORT).show();
                            Glide.with(context).load(baseUrl + "/storage/" + obj.getString("filename"));
                            Log.d("SHLP", "tried to load " + baseUrl + "/storage/partImg/" + obj.getString("filename"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {


            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("file", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        //adding the request to volley
        queue.add(volleyMultipartRequest);
    }
}
