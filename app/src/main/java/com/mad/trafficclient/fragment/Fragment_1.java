package com.mad.trafficclient.fragment;

import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.google.gson.Gson;
import com.mad.trafficclient.R;
import com.mad.trafficclient.activity.Activity_Main;
import com.mad.trafficclient.adapter.Adapter_fg1;
import com.mad.trafficclient.db.RechargeLogMan;
import com.mad.trafficclient.httppost.HttpRequest;
import com.mad.trafficclient.model.CarInfo_fg1;
import com.mad.trafficclient.model.RechargeLog;
import com.mad.trafficclient.model.UserInfo_fg1;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Fragment_1 extends Fragment implements View.OnClickListener {

    private ListView lvShowFg1;
    private Adapter_fg1 adpter;
    RechargeLogMan man;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_1, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        view.findViewById(R.id.btn_batch_fg1).setOnClickListener(this);
        view.findViewById(R.id.btn_log_fg1).setOnClickListener(this);
        lvShowFg1 = (ListView) view.findViewById(R.id.lv_show_fg1);
        man = new RechargeLogMan(getActivity());
        initLv();
    }

    private void initLv() {
        adpter = new Adapter_fg1(getActivity());
        lvShowFg1.setAdapter(adpter);

        final List<CarInfo_fg1.ROWSDETAILBean> cars = new ArrayList<CarInfo_fg1.ROWSDETAILBean>();
        JSONObject js = new JSONObject();
        try {
            js.put("UserName", "admin");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpRequest.request("GetCarInfo", js, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Gson gson = new Gson();
                CarInfo_fg1 car = gson.fromJson(jsonObject.toString(), CarInfo_fg1.class);
                for (CarInfo_fg1.ROWSDETAILBean bean : car.getROWS_DETAIL()) {
                    if (bean.getNumber() <= 4) {
                        cars.add(bean);
                        if (cars.size() == 4) {
                            break;
                        }
                    }
                }
                getAllCarBalance(cars);
            }
        }, null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_batch_fg1:
                if (adpter.selects.size() == 0) {
                    Toast.makeText(getActivity(), "您尚未选中车辆", Toast.LENGTH_SHORT).show();
                    return;
                }
                showDialog();
                break;
            case R.id.btn_log_fg1:
                Fragment_8 fragment_8 = new Fragment_8();
                Bundle bundle = new Bundle();
                fragment_8.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.maincontent, fragment_8).commit();
                ((Activity_Main) getActivity()).tV_title.setText("个人中心");
                break;
        }
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = View.inflate(getActivity(), R.layout.dialog_fg1, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();

        final EditText ed = (EditText) view.findViewById(R.id.ed_number_fg1Dia);
        view.findViewById(R.id.btn_s_fg1Dia).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(ed.getText())) {
                    Toast.makeText(getActivity(), "输入为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                int recharge = Integer.valueOf(ed.getText().toString());
                GetThenRecharge(recharge);
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.btn_c_fg1Dia).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void GetThenRecharge(final int recharge) {
        final int number_r[] = {0};
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
                for (CarInfo_fg1.ROWSDETAILBean beanc : adpter.selects) {
                    for (UserInfo_fg1.ROWSDETAILBean beanu : beanUs.getROWS_DETAIL()) {
                        if (beanu.getPcardid().equals(beanc.getPcardid())) {
                            recharge(number_r,beanc.getNumber(), beanu.getUsername(), recharge);
                        }
                    }
                }
            }
        }, null);
    }

    private void recharge(final int number_r[],final int number, final String userName, final int recharge) {
        JSONObject js = new JSONObject();
        try {
            js.put("UserName", userName);
            js.put("CarId", number);
            js.put("Money", recharge);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpRequest.request("SetCarAccountRecharge", js, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                int newBalance = adpter.balance[number - 1] + recharge;
                adpter.balance[number - 1] = newBalance;
                number_r[0]++;
                man.add(new RechargeLog(HttpRequest.getUserName(), userName, recharge, newBalance, System.currentTimeMillis(), number));
                if (number_r[0] == adpter.selects.size()) {
                    adpter.notifyDataSetChanged();
                }
            }
        }, null);
    }

    private void getAllCarBalance(final List<CarInfo_fg1.ROWSDETAILBean> cars) {
        JSONObject js = new JSONObject();
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
                getOneBalance(cars, beanUs.getROWS_DETAIL());
            }
        }, null);
    }

    private void getOneBalance(List<CarInfo_fg1.ROWSDETAILBean> cars, List<UserInfo_fg1.ROWSDETAILBean> beans) {
        int number[]={0};
        int balance[] = new int[cars.size()];
        for (int i = 0; i < cars.size(); i++) {
            CarInfo_fg1.ROWSDETAILBean bean = cars.get(i);
            for (UserInfo_fg1.ROWSDETAILBean beanu : beans) {
                if (beanu.getPcardid().equals(bean.getPcardid())) {
                    getBalance(number,cars, i, balance, bean.getNumber(), beanu.getUsername());
                }
            }
        }
    }

    private void getBalance(final int numbers[], final List<CarInfo_fg1.ROWSDETAILBean> cars, final int postion, final int balance[], final int number, String userName) {

        final JSONObject js = new JSONObject();
        try {
            js.put("UserName", userName);
            js.put("CarId", number);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpRequest.request("GetCarAccountBalance", js, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                if (!isAdded()) {
                    return;
                }
                Log.d("tag", "onResponse: "+jsonObject.toString());
                try {
                    balance[postion] = jsonObject.getInt("Balance");
                    Log.d("tag", "balance[ "+postion+"]"+"="+jsonObject.getInt("Balance"));
                    numbers[0]++;
                    if (numbers[0] == 4) {
                        adpter.set(cars, balance);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, null);
    }

}
