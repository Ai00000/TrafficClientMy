package com.mad.trafficclient.fragment.fg8_item;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.ListView;

import com.mad.trafficclient.R;
import com.mad.trafficclient.adapter.Adapter_fg8_RechargeLog;
import com.mad.trafficclient.db.RechargeLogMan;

public class RechargeLog extends Fragment {

    private ListView lvShoFg8RechargeLog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_8_rechargelog, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        lvShoFg8RechargeLog = (ListView) view.findViewById(R.id.lv_sho_fg8RechargeLog);
        RechargeLogMan man = new RechargeLogMan(getActivity());
        lvShoFg8RechargeLog.setAdapter(new Adapter_fg8_RechargeLog(getActivity(), man.query()));
        man.db.close();
    }

}
