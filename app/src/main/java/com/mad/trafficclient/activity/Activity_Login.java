package com.mad.trafficclient.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mad.trafficclient.App;
import com.mad.trafficclient.R;
import com.mad.trafficclient.util.LoadingDialog;
import com.mad.trafficclient.util.UrlBean;
import com.mad.trafficclient.util.Util;

import org.json.JSONException;
import org.json.JSONObject;


public class Activity_Login extends Activity {

    private UrlBean urlBean;
    private String urlHttp;
    private String urlPort = "80";

    EditText accountET, pwdET;
    CheckBox jzpwdCB, autologCB;
    Button loginBtn, regBtn;
    TextView tv_ipSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initLiserter();

        boolean b = this.getSharedPreferences("setting", Context.MODE_PRIVATE).getBoolean("isChecked", true);
        jzpwdCB.setChecked(b);
        String name= App.appContext.getSharedPreferences("setting", Context.MODE_APPEND).getString("userName", null);
        String pwd= App.appContext.getSharedPreferences("setting", Context.MODE_APPEND).getString("userPwd", null);
        if (b){
            accountET.setText(name);
            pwdET.setText(pwd);
        }else {
            accountET.setText("");
            pwdET.setText("");
        }

        jzpwdCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Activity_Login.this.getSharedPreferences("setting", Context.MODE_PRIVATE).edit().putBoolean("isChecked",isChecked).apply();
            }
        });
    }

    private void initView() {
        // TODO Auto-generated method stub
        accountET = (EditText) findViewById(R.id.accountET);
        pwdET = (EditText) findViewById(R.id.pwdET);
        jzpwdCB = (CheckBox) findViewById(R.id.jzpwdCB);
        autologCB = (CheckBox) findViewById(R.id.autologCB);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        regBtn = (Button) findViewById(R.id.regBtn);
        tv_ipSet = (TextView) findViewById(R.id.tv_ipSet);

        urlBean = Util.loadSetting(Activity_Login.this);


    }

    private void initLiserter() {
        // TODO Auto-generated method stub
        regBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Activity_Login.this,
                        Activity_Reg.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final String account = accountET.getText().toString();
                final String pwd = pwdET.getText().toString();

                LoadingDialog.showDialog(Activity_Login.this);
                JSONObject params = new JSONObject();
                try {
                    params.put("UserName", account);
                    params.put("UserPwd", pwd);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                Log.d("TAG", params.toString());

                String strUrl = "http://" + urlBean.getUrl() + ":" + urlBean.getPort() + "/transportservice/action/user_login.do";

                Log.d("TAG", strUrl);

                RequestQueue mQueue = Volley.newRequestQueue(Activity_Login.this);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, strUrl, params, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("RESULT").equals("S")){
                                String userRole=response.getString("UserRole");
                                Util.saveUser(account,pwd,userRole,Activity_Login.this);
                                Intent intent = new Intent(Activity_Login.this,Activity_Main.class);
                                startActivity(intent);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        LoadingDialog.disDialog();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        LoadingDialog.disDialog();
                    }
                });
                mQueue.add(jsonObjectRequest);
            }
        });

        tv_ipSet.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Dialog urlSettingDialog = new Dialog(Activity_Login.this);
                urlSettingDialog.show();
                urlSettingDialog.setTitle("Setting");
                urlSettingDialog.getWindow().setContentView(R.layout.login_setting);
                final EditText edit_urlHttp = (EditText) urlSettingDialog.getWindow().findViewById(R.id.edit_setting_url);
                final EditText edit_urlPort = (EditText) urlSettingDialog.getWindow().findViewById(R.id.edit_setting_port);

                edit_urlHttp.setText(urlBean.getUrl());
                edit_urlPort.setText(urlBean.getPort());
                Button save = (Button) urlSettingDialog.getWindow().findViewById(R.id.save);
                Button cancel = (Button) urlSettingDialog.getWindow().findViewById(R.id.cancel);
                save.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        urlHttp = edit_urlHttp.getText().toString();
                        urlPort = edit_urlPort.getText().toString();

                        if (urlHttp == null || urlHttp.equals("")) {
                            Toast.makeText(Activity_Login.this, R.string.error_ip, Toast.LENGTH_LONG).show();
                        } else {
                            Util.saveSetting(urlHttp, urlPort, Activity_Login.this);
                            urlBean = Util.loadSetting(Activity_Login.this);
                            urlSettingDialog.dismiss();
                        }
                    }
                });
                cancel.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        urlSettingDialog.dismiss();
                    }
                });
                urlSettingDialog.show();

            }
        });


    }


}
