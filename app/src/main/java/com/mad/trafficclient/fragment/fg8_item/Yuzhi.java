package com.mad.trafficclient.fragment.fg8_item;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.mad.trafficclient.R;
import com.mad.trafficclient.service.YuzhiService;

public class Yuzhi extends Fragment implements View.OnClickListener {

    private TextView tvThisYuzhiFg8Yuzhi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_8_yuzhi, container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvThisYuzhiFg8Yuzhi = (TextView) view.findViewById(R.id.tv_thisYuzhi_fg8Yuzhi);
        view.findViewById(R.id.btn_ed_fg8Yuzhi).setOnClickListener(this);

        int yuzhi = getActivity().getSharedPreferences("setting", Context.MODE_APPEND).getInt("carYuzhi", -1);
        if (yuzhi >= 0) {
            tvThisYuzhiFg8Yuzhi.setText("当前阈值为:" + yuzhi + "元");
            getActivity().startService(new Intent(getActivity(), YuzhiService.class));
        } else {
            tvThisYuzhiFg8Yuzhi.setText("当前阈值为:元");
        }

    }

    private EditText getEdFg8Yuzhi() {
        return (EditText) getView().findViewById(R.id.ed_fg8Yuzhi);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ed_fg8Yuzhi:
                if (TextUtils.isEmpty(getEdFg8Yuzhi().getText())){
                    Toast.makeText(getActivity(), "请输入数值", Toast.LENGTH_SHORT).show();
                    return;
                }
                int yuzhi=Integer.valueOf(getEdFg8Yuzhi().getText().toString());
                if (getActivity().getSharedPreferences("setting", Context.MODE_APPEND).edit().putInt("carYuzhi",yuzhi).commit()){
                    Toast.makeText(getActivity(), "设置成功", Toast.LENGTH_SHORT).show();
                    tvThisYuzhiFg8Yuzhi.setText("当前阈值为:" + yuzhi + "元");
                    getActivity().startService(new Intent(getActivity(), YuzhiService.class));
                }
                break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().stopService(new Intent(getActivity(), YuzhiService.class));
    }
}
