package com.mad.trafficclient.fragment;

import android.app.AlertDialog;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;

import com.android.volley.Response;
import com.mad.trafficclient.R;
import com.mad.trafficclient.db.RechargeLogMan;
import com.mad.trafficclient.httppost.HttpRequest;
import com.mad.trafficclient.model.RechargeLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Fragment_2 extends Fragment implements View.OnClickListener {

    private TextView tvAllFg2;
    private TextView tv11dFg2;
    private TextView tv11tFg2;
    private TextView tv12dFg2;
    private TextView tv12tFg2;
    private TextView tv21dFg2;
    private TextView tv21tFg2;
    private TextView tv22dFg2;
    private TextView tv22tFg2;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            getAllca();
            getBustation();
        }
    };
    Timer timer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_2, container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        tvAllFg2 = (TextView) view.findViewById(R.id.tv_all_fg2);
        view.findViewById(R.id.btn_details_fg2).setOnClickListener(this);
        tv11dFg2 = (TextView) view.findViewById(R.id.tv_11d_fg2);
        tv11tFg2 = (TextView) view.findViewById(R.id.tv_11t_fg2);
        tv12dFg2 = (TextView) view.findViewById(R.id.tv_12d_fg2);
        tv12tFg2 = (TextView) view.findViewById(R.id.tv_12t_fg2);
        tv21dFg2 = (TextView) view.findViewById(R.id.tv_21d_fg2);
        tv21tFg2 = (TextView) view.findViewById(R.id.tv_21t_fg2);
        tv22dFg2 = (TextView) view.findViewById(R.id.tv_22d_fg2);
        tv22tFg2 = (TextView) view.findViewById(R.id.tv_22t_fg2);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        }, 20, 5000);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_details_fg2:
                getDetails();
                break;
        }
    }

    private void getAllca() {
        final int[] number = {0};
        final int[] all = {0};
        for (int i = 1; i < 16; i++) {
            JSONObject js = new JSONObject();
            try {
                js.put("UserName", HttpRequest.getUserName());
                js.put("BusId", i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpRequest.request("GetBusCapacity", js, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {
                        int ca = jsonObject.getInt("BusCapacity");
                        all[0] += ca;
                        number[0]++;
                        if (number[0] == 15) {
                            tvAllFg2.setText("车载总容量为:" + all[0] + "人");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, null);
        }

    }



    private void getDetails() {
        final int[] number_gd = {0};
        AlertDialog.Builder bu = new AlertDialog.Builder(getActivity());
        final ListView lv = new ListView(getActivity());
        bu.setView(lv);
        bu.show();
        final String s[] = new String[15];
        for (int i = 1; i < 16; i++) {
            JSONObject js = new JSONObject();
            try {
                js.put("UserName", HttpRequest.getUserName());
                js.put("BusId", i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            final int finalI = i;
            HttpRequest.request("GetBusCapacity", js, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    if (!isAdded()) {
                        return;
                    }
                    try {
                        int ca = jsonObject.getInt("BusCapacity");
                        s[finalI - 1] = finalI + "号车容量为:" + ca + "人";
                        number_gd[0]++;
                        if (number_gd[0] == 15) {
                            lv.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, s));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, null);
        }
    }

    private void getBustation() {
        for (int i = 1; i < 3; i++) {
            JSONObject js = new JSONObject();
            try {
                js.put("UserName", HttpRequest.getUserName());
                js.put("BusStationId", i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            final int finalI = i;
            HttpRequest.request("GetBusStationInfo", js, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {
                        JSONArray jsa = jsonObject.getJSONArray("ROWS_DETAIL");
                        int d1 = jsa.getJSONObject(0).getInt("Distance");
                        int d2 = jsa.getJSONObject(1).getInt("Distance");

                        int t1 = (int) (d1 / (float) 10 / (float) 20000 * 60);
                        int t2 = (int) (d2 / (float) 10 / (float) 20000 * 60);
                        if (finalI == 1) {
                            tv11dFg2.setText("距离站台:" +(int) (d1/(float)10) + "m");
                            tv11tFg2.setText("剩余" + t1 + "分钟到达");
                            tv12dFg2.setText("距离站台:" + (int) (d2/(float)10)  + "m");
                            tv12tFg2.setText("剩余" + t2 + "分钟到达");
                        } else {
                            tv21dFg2.setText("距离站台:" + (int) (d1/(float)10)  + "m");
                            tv21tFg2.setText("剩余" + t1 + "分钟到达");
                            tv22dFg2.setText("距离站台:" + (int) (d2/(float)10)  + "m");
                            tv22tFg2.setText("剩余" + t2 + "分钟到达");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, null);
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        timer.cancel();
        timer.purge();
        timer=null;
    }
}
