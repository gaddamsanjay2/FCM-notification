package com.example.fcmnotification;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FcmSender {
    String userFcmToken;
    String title;
    String body;
    Context mContext;
    Activity mActivity;
    private RequestQueue requestQueue;
    private final String postUrl = "https://fcm.googleapis.com/fcm/send";
    private final String fcmServerKey="AAAA9UqyTwY:APA91bGMK98KtS_ebsqB8dmh4_M3Wq2CeNnqvCEbvqRVOyAweyPcyiMzUXY7qRjtiQkyqWuptiu9oR_xaKLIK1iQWFCD4WTu9gKcG4KjOFX2KAB639SlgisQ3YkHKU_RRMTwHwnILaLW";
    public FcmSender(String userFcmToken, String title, String body, Context mContext, Activity mActivity) {
        this.userFcmToken = userFcmToken;
        this.title = title;
        this.body = body;
        this.mContext = mContext;
        this.mActivity = mActivity;
    }

    public void SendNotifications() {

        requestQueue = Volley.newRequestQueue(mActivity);
        JSONObject mainObj = new JSONObject();
        try {
            mainObj.put("to", userFcmToken);
            JSONObject notiObject = new JSONObject();
            notiObject.put("title", title);
            notiObject.put("body", body);
            notiObject.put("icon", "icon"); // enter icon that exists in drawable only


            mainObj.put("notification", notiObject);


            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, postUrl, mainObj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    // code run is got response


                    Toast.makeText(mContext, "" + response, Toast.LENGTH_SHORT).show();
                    Log.d("onResponse", response.toString());


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // code run is got error

                    Toast.makeText(mContext, "all Error"+error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {

                        Toast.makeText(mContext, "Time out or no connection  error", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof AuthFailureError) {
                        //TODO
                        Toast.makeText(mContext, "AuthFailureError  error", Toast.LENGTH_SHORT).show();

                    } else if (error instanceof ServerError) {
                        //TODO
                        Toast.makeText(mContext, "ServerError  error", Toast.LENGTH_SHORT).show();

                    } else if (error instanceof NetworkError) {
                        //TODO
                        Toast.makeText(mContext, "NetworkError  error", Toast.LENGTH_SHORT).show();

                    } else if (error instanceof ParseError) {
                        //TODO
                        Toast.makeText(mContext, "ParseError  error", Toast.LENGTH_SHORT).show();

                    }

                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

                    Map<String, String> header = new HashMap<>();
                    header.put("content-type", "application/json");
                    header.put("authorization", "key=" + fcmServerKey);
                    return header;


                }
            };
            requestQueue.add(request);


        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "Exception"+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }

    }

}