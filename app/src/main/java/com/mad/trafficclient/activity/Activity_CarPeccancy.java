package com.mad.trafficclient.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.google.gson.Gson;
import com.mad.trafficclient.R;
import com.mad.trafficclient.adapter.Adapter_Fg3_CarPeccancy;
import com.mad.trafficclient.adapter.Adapter_Fg3_DetailPeccancy;
import com.mad.trafficclient.db.DBManag_Fg4;
import com.mad.trafficclient.httppost.HttpRequest;
import com.mad.trafficclient.model.DetailPeccancyInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/2.
 */

public class Activity_CarPeccancy extends Activity {
    private TextView tv_addCar;
    private ListView listView_carPeccancy;
    private ListView listView_detail;
    private DBManag_Fg4 manager;
    private Adapter_Fg3_CarPeccancy adapter1;
    private Adapter_Fg3_DetailPeccancy adapter2;
    private List<DetailPeccancyInfo> lists=new ArrayList<DetailPeccancyInfo>();

    public String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_peccancy);
        manager=new DBManag_Fg4(Activity_CarPeccancy.this);
        initView();
        adapter2=new Adapter_Fg3_DetailPeccancy(Activity_CarPeccancy.this,lists);
        listView_detail.setAdapter(adapter2);
        detailPeccancy(manager.query().get(0).getCarnumber());

        str=manager.query().get(0).getCarnumber();

        initQueryList();

        listView_carPeccancy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                detailPeccancy(manager.query().get(position).getCarnumber());
                str=manager.query().get(position).getCarnumber();
            }
        });
        tv_addCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        listView_detail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(Activity_CarPeccancy.this,Activity_PeccancyImg.class));
            }
        });
    }

    public long delete(String carnumber){
        return manager.delete(carnumber);
    }

    public void listClear(){
        lists.clear();
        adapter2.notifyDataSetChanged();
    }
    public void initQueryList() {
        adapter1=new Adapter_Fg3_CarPeccancy(Activity_CarPeccancy.this,manager.query());
        listView_carPeccancy.setAdapter(adapter1);
    }

    private void detailPeccancy(String carnumber) {
        lists.clear();
        JSONObject jo=new JSONObject();
        try {
            jo.put("carnumber",carnumber);
            jo.put("UserName", HttpRequest.getUserName());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpRequest.request("GetCarPeccancy", jo, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    if (jsonObject.getString("RESULT").equals("S")){
                        JSONArray array=new JSONArray(jsonObject.getString("ROWS_DETAIL"));
                        Gson gson=new Gson();
                        JSONObject object;
                        for (int i = 0; i <array.length() ; i++) {
                            object=array.getJSONObject(i);
                            DetailPeccancyInfo info=gson.fromJson(object.toString(),DetailPeccancyInfo.class);
                            lists.add(info);
                            if (lists.size()==array.length()){
                                adapter2.notifyDataSetChanged();
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },null);
    }



    private void initView() {
        tv_addCar = (TextView) findViewById(R.id.tv_addCar);
        listView_carPeccancy = (ListView) findViewById(R.id.listView_carPeccancy);
        listView_detail = (ListView) findViewById(R.id.listView_detail);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.closeDB();
    }
}
