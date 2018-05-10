package com.mad.trafficclient.fragment.fg8_item;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.ListView;

import com.android.volley.Response;
import com.google.gson.Gson;
import com.mad.trafficclient.R;
import com.mad.trafficclient.adapter.Adapter_fg8_Personal;
import com.mad.trafficclient.httppost.HttpRequest;
import com.mad.trafficclient.model.CarInfo_fg1;
import com.mad.trafficclient.model.UserInfo_fg1;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Personal extends Fragment {

    private TextView tvNameFg1Lv;
    private TextView tvPidFg1Lv;
    private TextView tvSexFg1Lv;
    private TextView tvPhoneFg1Lv;
    private TextView tvDateFg1Lv;
    private ListView lvShowFg8Per;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_8_personal, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        tvNameFg1Lv = (TextView) view.findViewById(R.id.tv_name_fg1Lv);
        tvPidFg1Lv = (TextView) view.findViewById(R.id.tv_pid_fg1Lv);
        tvSexFg1Lv = (TextView) view.findViewById(R.id.tv_sex_fg1Lv);
        tvPhoneFg1Lv = (TextView) view.findViewById(R.id.tv_phone_fg1Lv);
        tvDateFg1Lv = (TextView) view.findViewById(R.id.tv_date_fg1Lv);
        lvShowFg8Per = (ListView) view.findViewById(R.id.lv_show_fg8Per);
        getUserInfo();
    }

    private void getUserInfo() {
        final JSONObject js = new JSONObject();
        try {
            js.put("UserName", "admin");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpRequest.request("GetSUserInfo", js, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Gson gson = new Gson();
                UserInfo_fg1 beanUs = gson.fromJson(jsonObject.toString(), UserInfo_fg1.class);
                for (UserInfo_fg1.ROWSDETAILBean bean : beanUs.getROWS_DETAIL()) {
                    if (bean.getUsername().equals(HttpRequest.getUserName())) {
                        tvDateFg1Lv.setText("注册日期:" + bean.getPregisterdate());
                        tvNameFg1Lv.setText("姓名:" + bean.getPname());
                        tvPhoneFg1Lv.setText("电话号码:" + bean.getPtel());
                        tvPidFg1Lv.setText("身份证号:" + bean.getPcardid());
                        tvSexFg1Lv.setText("性别:" + bean.getPsex());
                        getCarInfo(bean.getPcardid());
                        break;
                    }
                }
            }
        }, null);
    }

    private void getCarInfo(final String pid) {
        final List<CarInfo_fg1.ROWSDETAILBean> list = new ArrayList<CarInfo_fg1.ROWSDETAILBean>();
        final JSONObject js = new JSONObject();
        try {
            js.put("UserName", "admin");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpRequest.request("GetCarInfo", js, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                if (!isAdded()){
                    return;
                }
                Gson gson = new Gson();
                CarInfo_fg1 beanUs = gson.fromJson(jsonObject.toString(), CarInfo_fg1.class);
                for (CarInfo_fg1.ROWSDETAILBean bean : beanUs.getROWS_DETAIL()) {
                    if (bean.getPcardid().equals(pid)) {
                        list.add(bean);
                    }
                }
                lvShowFg8Per.setAdapter(new Adapter_fg8_Personal(getActivity(), list));
            }
        }, null);
    }

}
