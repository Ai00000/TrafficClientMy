/**
 *
 */
package com.mad.trafficclient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.google.gson.Gson;
import com.mad.trafficclient.R;
import com.mad.trafficclient.activity.Activity_CarPeccancy;
import com.mad.trafficclient.db.DBManag_Fg4;
import com.mad.trafficclient.httppost.HttpRequest;
import com.mad.trafficclient.model.CarPeccancyInfo;
import com.mad.trafficclient.model.PeccancyTypeInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Fragment_4 extends Fragment implements View.OnClickListener {

    private EditText et_fg4_carnumber;
    private Button btn_fg4_queryCar;
    private DBManag_Fg4 manager;
    private List<PeccancyTypeInfo> lists;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout04, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        manager = new DBManag_Fg4(getActivity());
        lists = new ArrayList<PeccancyTypeInfo>();
        queryPeccancyType();
    }

    private void queryPeccancyType() {
        JSONObject jo = new JSONObject();
        try {
            jo.put("UserName", HttpRequest.getUserName());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpRequest.request("GetPeccancyType", jo, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                if (TextUtils.isEmpty(jsonObject.toString())) {
                    Toast.makeText(getActivity(), "违章代码查询失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    if (jsonObject.getString("RESULT").equals("S")) {
                        JSONArray array = new JSONArray(jsonObject.getString("ROWS_DETAIL"));
                        Gson gson = new Gson();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            PeccancyTypeInfo info = gson.fromJson(object.toString(), PeccancyTypeInfo.class);
                            lists.add(info);
                        }
                        if (lists.size() == array.length()) {
                            Log.e("lists", lists.size() + "");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, null);
    }

    private void initView(View view) {
        et_fg4_carnumber = (EditText) view.findViewById(R.id.et_fg4_carnumber);
        btn_fg4_queryCar = (Button) view.findViewById(R.id.btn_fg4_queryCar);

        btn_fg4_queryCar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_fg4_queryCar:
                submit();
                break;
        }
    }

    private void submit() {
        // validate
        if (TextUtils.isEmpty(et_fg4_carnumber.getText().toString().trim())) {
            Toast.makeText(getActivity(), "carnumber不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        // TODO validate success, do something
        final String carnumber = "鲁" + et_fg4_carnumber.getText().toString().trim();
        JSONObject jo = new JSONObject();
        try {
            jo.put("UserName", HttpRequest.getUserName());
            jo.put("carnumber", carnumber);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpRequest.request("GetCarPeccancy", jo, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                if (TextUtils.isEmpty(jsonObject.toString())) {
                    Toast.makeText(getActivity(), "违章车辆查询失败", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    if (jsonObject.getString("RESULT").equals("S")) {
                        JSONArray array = new JSONArray(jsonObject.getString("ROWS_DETAIL"));
                        if (array.length() == 0) {
                            Toast.makeText(getActivity(), "此车无违章记录或车牌号输入错误请重试", Toast.LENGTH_SHORT).show();
                        } else {

                            int countMoney = 0;
                            int countScore = 0;
                            JSONObject jo;
                            String times = String.valueOf(array.length());
                            PeccancyTypeInfo typeInfo;
                            for (int i = 0; i < array.length(); i++) {
                                String str;
                                jo = array.getJSONObject(i);
                                String pcode = jo.getString("pcode");
                                Log.e("pcode", pcode);
                                if (pcode.length() != 4) {
                                    str = pcode.substring(0, 5);
                                } else {
                                    str = pcode;
                                }
                                Log.e("str", str);
                                for (int j = 0; j < lists.size(); j++) {
                                    typeInfo = lists.get(j);
                                    if (TextUtils.equals(str,typeInfo.getPcode())) {
                                        countMoney += typeInfo.getPmoney();
                                        countScore += typeInfo.getPscore();
                                    }
                                    Log.e("j", j + "");
                                }
                            }

                            CarPeccancyInfo info = new CarPeccancyInfo(carnumber, times,
                                    String.valueOf(countMoney), String.valueOf(countScore));
                            if (manager.find(carnumber) == 0) {
                                if (manager.insert(info) > 0) {
                                    startActivity(new Intent(getActivity(), Activity_CarPeccancy.class));
                                } else {
                                    Toast.makeText(getActivity(), "插入失败", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getActivity(), "此车辆信息已存在", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, null);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        manager.closeDB();
    }
}
