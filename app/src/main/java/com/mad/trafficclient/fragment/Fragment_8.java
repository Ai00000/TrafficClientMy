package com.mad.trafficclient.fragment;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.mad.trafficclient.R;
import com.mad.trafficclient.fragment.fg8_item.Personal;
import com.mad.trafficclient.fragment.fg8_item.RechargeLog;
import com.mad.trafficclient.fragment.fg8_item.Yuzhi;

public class Fragment_8 extends Fragment implements View.OnClickListener {

    private RelativeLayout rlFgContainerFg8;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_8, container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btn_personal_fg8).setOnClickListener(this);
        view.findViewById(R.id.btn_log_fg8).setOnClickListener(this);
        view.findViewById(R.id.btn_yuzhi_fg8).setOnClickListener(this);
        rlFgContainerFg8 = (RelativeLayout) view.findViewById(R.id.rl_fgContainer_fg8);
        if (getArguments()!=null){
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.rl_fgContainer_fg8, new RechargeLog()).commit();
        }else {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.rl_fgContainer_fg8, new Personal()).commit();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_personal_fg8:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.rl_fgContainer_fg8, new Personal()).commit();
                break;
            case R.id.btn_log_fg8:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.rl_fgContainer_fg8, new RechargeLog()).commit();
                break;
            case R.id.btn_yuzhi_fg8:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.rl_fgContainer_fg8, new Yuzhi()).commit();
                break;
        }
    }
}
