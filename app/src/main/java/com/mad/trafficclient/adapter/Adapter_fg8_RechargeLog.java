package com.mad.trafficclient.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mad.trafficclient.R;
import com.mad.trafficclient.model.RechargeLog;

public class Adapter_fg8_RechargeLog extends BaseAdapter {

    private List<RechargeLog> objects = new ArrayList<RechargeLog>();

    private Context context;
    private LayoutInflater layoutInflater;

    public Adapter_fg8_RechargeLog(Context context, List<RechargeLog> objects) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.objects = objects;
    }

    @Override
    public int getCount() {
        if (objects.size()==0){
            return 1;
        }
        return objects.size();
    }

    @Override
    public RechargeLog getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (objects.size()==0){
            TextView tv=new TextView(context);
            tv.setText("暂无历史充值记录");
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
            tv.setTextSize(30);
            return tv;
        }
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.lv_fg8_rechargelog, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews(position+1,getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(int position,RechargeLog object, ViewHolder holder) {
        holder.tvBalanceFg8RechLv.setText(object.getBalance() + "");
        holder.tvPostionFg8RechLv.setText(position+"");
        holder.tvNumberFg8RechLv.setText(object.getNumber() + "");
        holder.tvUserNameFg8RechLv.setText(object.getUserName() + "");
        holder.tvMoneyFg8RechLv.setText(object.getMoney() + "");
        holder.tvDateFg8RechLv.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(object.getDate())) + "");
    }

    protected class ViewHolder {
        private TextView tvPostionFg8RechLv;
        private TextView tvNumberFg8RechLv;
        private TextView tvUserNameFg8RechLv;
        private TextView tvMoneyFg8RechLv;
        private TextView tvBalanceFg8RechLv;
        private TextView tvDateFg8RechLv;

        public ViewHolder(View view) {
            tvPostionFg8RechLv = (TextView) view.findViewById(R.id.tv_postion_fg8RechLv);
            tvNumberFg8RechLv = (TextView) view.findViewById(R.id.tv_number_fg8RechLv);
            tvUserNameFg8RechLv = (TextView) view.findViewById(R.id.tv_userName_fg8RechLv);
            tvMoneyFg8RechLv = (TextView) view.findViewById(R.id.tv_money_fg8RechLv);
            tvBalanceFg8RechLv = (TextView) view.findViewById(R.id.tv_balance_fg8RechLv);
            tvDateFg8RechLv = (TextView) view.findViewById(R.id.tv_date_fg8RechLv);
        }
    }
}
