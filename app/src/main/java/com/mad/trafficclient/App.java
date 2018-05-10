package com.mad.trafficclient;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2017/6/2.
 */

public class App extends Application {
    public static Context appContext=null;
    @Override
    public void onCreate() {
        super.onCreate();
        appContext=getApplicationContext();
    }
}
