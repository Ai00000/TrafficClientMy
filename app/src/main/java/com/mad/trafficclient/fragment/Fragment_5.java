package com.mad.trafficclient.fragment;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import com.android.volley.Response;
import com.google.gson.Gson;
import com.mad.trafficclient.R;
import com.mad.trafficclient.httppost.HttpRequest;
import com.mad.trafficclient.model.UserInfo_fg1;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class Fragment_5 extends Fragment implements View.OnClickListener {

    private TextView tv1rFg5;
    private TextView tv1r2Fg5;
    private TextView tv1r3Fg5;
    private TextView tv2rFg5;
    private TextView tv3rFg5;
    private TextView tv4rFg5;
    private TextView tv5rFg5;
    private TextView tv6rFg5;
    private TextView tv7rFg5;
    private TextView tv7r2Fg5;
    private TextView tv7r3Fg5;
    private TextView tvPmFg5;
    private TextView tvCo2Fg5;
    private TextView tvLightFg5;
    private TextView tvTempFg5;
    private TextView tvHumdFg5;
    private ImageView im;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_5, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        tv1rFg5 = (TextView) view.findViewById(R.id.tv_1r_fg5);
        tv1r2Fg5 = (TextView) view.findViewById(R.id.tv_1r2_fg5);
        tv1r3Fg5 = (TextView) view.findViewById(R.id.tv_1r3_fg5);
        tv2rFg5 = (TextView) view.findViewById(R.id.tv_2r_fg5);
        tv3rFg5 = (TextView) view.findViewById(R.id.tv_3r_fg5);
        tv4rFg5 = (TextView) view.findViewById(R.id.tv_4r_fg5);
        tv5rFg5 = (TextView) view.findViewById(R.id.tv_5r_fg5);
        tv6rFg5 = (TextView) view.findViewById(R.id.tv_6r_fg5);
        tv7rFg5 = (TextView) view.findViewById(R.id.tv_7r_fg5);
        tv7r2Fg5 = (TextView) view.findViewById(R.id.tv_7r2_fg5);
        tv7r3Fg5 = (TextView) view.findViewById(R.id.tv_7r3_fg5);
        view.findViewById(R.id.btn_fg5).setOnClickListener(this);
        tvPmFg5 = (TextView) view.findViewById(R.id.tv_pm_fg5);
        tvCo2Fg5 = (TextView) view.findViewById(R.id.tv_co2_fg5);
        tvLightFg5 = (TextView) view.findViewById(R.id.tv_light_fg5);
        tvTempFg5 = (TextView) view.findViewById(R.id.tv_temp_fg5);
        tvHumdFg5 = (TextView) view.findViewById(R.id.tv_humd_fg5);
        im = (ImageView) view.findViewById(R.id.im_fg5);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        }, 20, 5000);
        getSense();
        handlerIm.sendEmptyMessage(0);
    }

    boolean flag = true;
    Handler handlerIm = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (!flag) {
                return;
            }
            if (msg.what == 0) {
                im.setImageResource(R.drawable.touxiang_1);
                handlerIm.sendEmptyMessageDelayed(1,1000);
            } else if (msg.what == 1) {
                im.setImageResource(R.drawable.touxiang_2);
                handlerIm.sendEmptyMessageDelayed(0,1000);
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_fg5:
                getSense();
                break;
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            getRoad();
        }
    };
    Timer timer;

    @Override
    public void onStop() {
        super.onStop();
        timer.cancel();
        timer.purge();
        timer = null;
        flag=false;
    }

    private void getSense() {
        JSONObject js = new JSONObject();
        try {
            js.put("UserName", HttpRequest.getUserName());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpRequest.request("GetAllSense", js, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    tvPmFg5.setText("PM2.5:" + jsonObject.getInt("pm2.5"));
                    tvCo2Fg5.setText("CO2:" + jsonObject.getInt("co2"));
                    tvLightFg5.setText("光照值:" + jsonObject.getInt("LightIntensity"));
                    tvTempFg5.setText("温度:" + jsonObject.getInt("temperature"));
                    tvHumdFg5.setText("湿度:" + jsonObject.getInt("humidity"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, null);
    }

    String c[] = new String[]{"#3366CC", "#00CC00", "#FFCC00", "#FF6600", "#CC0000"};

    private void getRoad() {
        for (int i = 1; i < 8; i++) {
            JSONObject js = new JSONObject();
            try {
                js.put("UserName", HttpRequest.getUserName());
                js.put("RoadId", i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            final int finalI = i;
            HttpRequest.request("GetRoadStatus", js, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {
                        int status = jsonObject.getInt("Status");
                        TextView tag = null;
                        switch (finalI) {
                            case 2:
                                tag = tv2rFg5;
                                break;
                            case 3:
                                tag = tv3rFg5;
                                break;
                            case 4:
                                tag = tv4rFg5;
                                break;
                            case 5:
                                tag = tv5rFg5;
                                break;
                            case 6:
                                tag = tv6rFg5;
                                break;
                        }
                        if (tag != null) {
                            tag.setBackgroundColor(Color.parseColor(c[status]));
                        }
                        if (finalI == 1) {
                            tv1rFg5.setBackgroundColor(Color.parseColor(c[status]));
                            tv1r2Fg5.setBackgroundColor(Color.parseColor(c[status]));
                            tv1r3Fg5.setBackgroundColor(Color.parseColor(c[status]));
                        } else if (finalI == 7) {
                            tv7rFg5.setBackgroundColor(Color.parseColor(c[status]));
                            tv7r2Fg5.setBackgroundColor(Color.parseColor(c[status]));
                            tv7r3Fg5.setBackgroundColor(Color.parseColor(c[status]));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, null);
        }

    }
}
