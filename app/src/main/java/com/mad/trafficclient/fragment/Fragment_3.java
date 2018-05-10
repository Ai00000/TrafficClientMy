/**
 *
 */
package com.mad.trafficclient.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.mad.trafficclient.App;
import com.mad.trafficclient.R;
import com.mad.trafficclient.adapter.Adapter_Fg3;
import com.mad.trafficclient.httppost.HttpRequest;
import com.mad.trafficclient.model.TrafficLightInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class Fragment_3 extends Fragment implements View.OnClickListener {

    private Button btn_fg3_setLight;
    private Spinner spinner_fg3;
    private Button btn_fg3_quertLight;
    private ListView listView_fg3;

    private Adapter_Fg3 adapter;
    private int index=0;

    private Comparator<TrafficLightInfo> mComparator;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout03, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter=new Adapter_Fg3(getActivity());
        listView_fg3.setAdapter(adapter);
        quertLight();
    }

    private void initView(View view) {
        btn_fg3_setLight = (Button) view.findViewById(R.id.btn_fg3_setLight);
        spinner_fg3 = (Spinner) view.findViewById(R.id.spinner_fg3);
        btn_fg3_quertLight = (Button) view.findViewById(R.id.btn_fg3_quertLight);
        listView_fg3 = (ListView) view.findViewById(R.id.listView_fg3);

        btn_fg3_setLight.setOnClickListener(this);
        btn_fg3_quertLight.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_fg3_setLight:
                dialogLight();
                break;
            case R.id.btn_fg3_quertLight:
                quertLight();
                break;
        }
    }

    private void dialogLight() {
        if (adapter.lists.size()==0){
            Toast.makeText(getActivity(), "请选择红绿灯灯编号", Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("红绿灯设置");
        View view=LayoutInflater.from(getActivity()).inflate(R.layout.dialog_trafficlight,null);
        builder.setView(view);
        builder.setPositiveButton("确定",null);
        builder.setNegativeButton("取消",null);

        TextView tv_dialog_setLightId;
        final EditText et_dialog_red,et_dialog_green,et_dialog_yellow;
        tv_dialog_setLightId= (TextView) view.findViewById(R.id.tv_dialog_setLightId);
        et_dialog_red= (EditText) view.findViewById(R.id.et_dialog_red);
        et_dialog_green= (EditText) view.findViewById(R.id.et_dialog_green);
        et_dialog_yellow= (EditText) view.findViewById(R.id.et_dialog_yellow);

        StringBuilder sb=new StringBuilder();
        for (int i = 0; i <adapter.lists.size() ; i++) {
            sb.append(adapter.lists.get(i)+"号红绿灯"+"\t");
        }
        tv_dialog_setLightId.setText(sb.toString());

        final AlertDialog dialog=builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String red=et_dialog_red.getText().toString();
                String green=et_dialog_green.getText().toString();
                String yellow=et_dialog_yellow.getText().toString();
                if (TextUtils.isEmpty(red)||TextUtils.isEmpty(green)||TextUtils.isEmpty(yellow)){
                    Toast.makeText(getActivity(), "不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    for (int i = 0; i <adapter.lists.size() ; i++) {
                        setTrafficLight(adapter.lists.get(i),red,green,yellow);
                    }
                    dialog.dismiss();
                }
            }
        });
    }

    private void setTrafficLight(Integer id, String red, String green, String yellow) {
        JSONObject jo=new JSONObject();
        try {
            jo.put("TrafficLightId",id);
            jo.put("RedTime",red);
            jo.put("GreenTime",green);
            jo.put("YellowTime",yellow);
            jo.put("UserName",HttpRequest.getUserName());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpRequest.request("SetTrafficLightConfig", jo, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                if (TextUtils.isEmpty(jsonObject.toString())){
                    Toast.makeText(getActivity(), "设置失败，请重试", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    if (jsonObject.getString("RESULT").equals("S")){
                        index++;
                        if (index==adapter.lists.size()){
                            quertLight();
                            index=0;
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },null);
    }

    private void quertLight() {
        final List<TrafficLightInfo> lists=new ArrayList<TrafficLightInfo>();
        for (int i = 1; i <6 ; i++) {
            JSONObject jo=new JSONObject();
            try {
                jo.put("UserName", HttpRequest.getUserName());
                jo.put("TrafficLightId",i);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            final int finalI = i;
            HttpRequest.request("GetTrafficLightConfigAction", jo, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    if (TextUtils.isEmpty(jsonObject.toString())){
                        Toast.makeText(getActivity(), finalI +"号红绿灯查询失败,请重试", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try {
                        if (jsonObject.getString("RESULT").equals("S")){
                            TrafficLightInfo info=new TrafficLightInfo();
                            info.setLightId(finalI);
                            info.setRed(jsonObject.getInt("RedTime"));
                            info.setGreen(jsonObject.getInt("GreenTime"));
                            info.setYellow(jsonObject.getInt("YellowTime"));
                            lists.add(info);

                            if (lists.size()==5){
                                comparatorLight(lists);
                                adapter.getData(lists);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },null);
        }
    }

    private void comparatorLight(List<TrafficLightInfo> lists) {
        switch (spinner_fg3.getSelectedItemPosition()){
            case 0:
                mComparator=new Comparator<TrafficLightInfo>() {
                    @Override
                    public int compare(TrafficLightInfo lhs, TrafficLightInfo rhs) {
                        int num1=lhs.getLightId();
                        int num2=rhs.getLightId();
                        if (num1>num2){
                            return 1;
                        }else if (num1==num2){
                            return 0;
                        }else {
                            return -1;
                        }
                    }
                };
                break;
            case 1:
                mComparator=new Comparator<TrafficLightInfo>() {
                    @Override
                    public int compare(TrafficLightInfo lhs, TrafficLightInfo rhs) {
                        int num1=lhs.getLightId();
                        int num2=rhs.getLightId();
                        if (num1>num2){
                            return -1;
                        }else if (num1==num2){
                            return 0;
                        }else {
                            return 1;
                        }
                    }
                };
                break;
            case 2:
                mComparator=new Comparator<TrafficLightInfo>() {
                    @Override
                    public int compare(TrafficLightInfo lhs, TrafficLightInfo rhs) {
                        int num1=lhs.getRed();
                        int num2=rhs.getRed();
                        if (num1>num2){
                            return 1;
                        }else if (num1==num2){
                            return 0;
                        }else {
                            return -1;
                        }
                    }
                };
                break;
            case 3:
                mComparator=new Comparator<TrafficLightInfo>() {
                    @Override
                    public int compare(TrafficLightInfo lhs, TrafficLightInfo rhs) {
                        int num1=lhs.getRed();
                        int num2=rhs.getRed();
                        if (num1>num2){
                            return -1;
                        }else if (num1==num2){
                            return 0;
                        }else {
                            return 1;
                        }
                    }
                };
                break;
            case 4:
                mComparator=new Comparator<TrafficLightInfo>() {
                    @Override
                    public int compare(TrafficLightInfo lhs, TrafficLightInfo rhs) {
                        int num1=lhs.getGreen();
                        int num2=rhs.getGreen();
                        if (num1>num2){
                            return 1;
                        }else if (num1==num2){
                            return 0;
                        }else {
                            return -1;
                        }
                    }
                };
                break;
            case 5:
                mComparator=new Comparator<TrafficLightInfo>() {
                    @Override
                    public int compare(TrafficLightInfo lhs, TrafficLightInfo rhs) {
                        int num1=lhs.getGreen();
                        int num2=rhs.getGreen();
                        if (num1>num2){
                            return -1;
                        }else if (num1==num2){
                            return 0;
                        }else {
                            return 1;
                        }
                    }
                };
                break;
            case 6:
                mComparator=new Comparator<TrafficLightInfo>() {
                    @Override
                    public int compare(TrafficLightInfo lhs, TrafficLightInfo rhs) {
                        int num1=lhs.getYellow();
                        int num2=rhs.getYellow();
                        if (num1>num2){
                            return 1;
                        }else if (num1==num2){
                            return 0;
                        }else {
                            return -1;
                        }
                    }
                };
                break;
            case 7:
                mComparator=new Comparator<TrafficLightInfo>() {
                    @Override
                    public int compare(TrafficLightInfo lhs, TrafficLightInfo rhs) {
                        int num1=lhs.getYellow();
                        int num2=rhs.getYellow();
                        if (num1>num2){
                            return -1;
                        }else if (num1==num2){
                            return 0;
                        }else {
                            return 1;
                        }
                    }
                };
                break;
        }

        Collections.sort(lists,mComparator);
    }
}
