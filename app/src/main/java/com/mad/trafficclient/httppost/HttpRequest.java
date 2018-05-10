package com.mad.trafficclient.httppost;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mad.trafficclient.App;

import org.json.JSONObject;

/**
 * Created by Administrator on 2017/6/2.
 */

public class HttpRequest {

    private static final RequestQueue mQueue = Volley.newRequestQueue(App.appContext);

    public static void request(String action, JSONObject jso, Response.Listener<JSONObject> ok, Response.ErrorListener er) {
        SharedPreferences sp = App.appContext.getSharedPreferences("setting", Context.MODE_APPEND);
        String ip = sp.getString("ipUrl", "192.168.1.101");
        String port = sp.getString("ipPort", "8880");
        String urls = "http://" + ip + ":" + port + "/transportservice/action/" + action + ".do";
        if (ok == null) {
            ok = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {

                }
            };
        }
        if (er == null) {
            er = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }
            };
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, urls, jso, ok, er);
        mQueue.add(jsonObjectRequest);
    }



    public static String getUserName() {
        SharedPreferences sp = App.appContext.getSharedPreferences("setting", Context.MODE_APPEND);
        return sp.getString("userName", "admin");
    }

}


