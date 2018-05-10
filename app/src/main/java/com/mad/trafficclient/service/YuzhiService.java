package com.mad.trafficclient.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;

import com.android.volley.Response;
import com.google.gson.Gson;
import com.mad.trafficclient.R;
import com.mad.trafficclient.httppost.HttpRequest;
import com.mad.trafficclient.model.UserInfo_fg1;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/6/2.
 */

public class YuzhiService extends Service {
    Timer timer;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            getBalance();
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        }, 20, 5000);
    }
    private void getBalance(){
        final int yuzhi = getSharedPreferences("setting", Context.MODE_APPEND).getInt("carYuzhi", -1);
        if (yuzhi<0){
            return;
        }

        for (int i = 1; i <5 ; i++) {
            JSONObject js = new JSONObject();
            try {
                js.put("UserName", "admin");
                js.put("CarId", i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            final int finalI = i;
            HttpRequest.request("GetCarAccountBalance", js, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {
                        int balance=jsonObject.getInt("Balance");
                        NotificationManager man=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                        if (balance<yuzhi){
                            NotificationCompat.Builder bu=new NotificationCompat.Builder(YuzhiService.this);
                            bu.setContentText("车号:"+ finalI+"  余额:"+balance+"  阈值:"+yuzhi);
                            bu.setSmallIcon(R.drawable.ic_launcher);
                            man.notify(finalI,bu.build());
                        }else {
                            man.cancel(finalI);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, null);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        getBalance();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        timer.purge();
        timer = null;
    }
}
