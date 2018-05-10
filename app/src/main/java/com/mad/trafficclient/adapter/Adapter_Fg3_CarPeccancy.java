package com.mad.trafficclient.adapter;

/**
 * Created by Administrator on 2017/6/2.
 */

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mad.trafficclient.R;
import com.mad.trafficclient.activity.Activity_CarPeccancy;
import com.mad.trafficclient.model.CarPeccancyInfo;

public class Adapter_Fg3_CarPeccancy extends BaseAdapter {

    private List<CarPeccancyInfo> objects = new ArrayList<CarPeccancyInfo>();

    private Context context;
    private LayoutInflater layoutInflater;

    public Adapter_Fg3_CarPeccancy(Context context,List<CarPeccancyInfo> objects) {
        this.context = context;
        this.objects=objects;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public CarPeccancyInfo getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.adapter_carpeccancy, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((CarPeccancyInfo)getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(final CarPeccancyInfo object, ViewHolder holder) {
        //TODO implement
        holder.tvFg3AdapterPeccancyCarnumber.setText("车牌号："+object.getCarnumber());
        holder.tvFg3AdapterPeccancyTimes.setText("违章次数："+object.getTimes());
        holder.tvFg3AdapterPeccancyPmoney.setText("总罚款："+object.getPmoney());
        holder.tvFg3AdapterPeccancyPscore.setText("总扣分："+object.getPscore());
        holder.ivDeletecar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((Activity_CarPeccancy)context).delete(object.getCarnumber())>0){
                    if (object.getCarnumber().equals(((Activity_CarPeccancy)context).str)){
                        ((Activity_CarPeccancy)context).listClear();
                    }
                    ((Activity_CarPeccancy)context).initQueryList();
                }else {
                    Toast.makeText(context, "删除失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    protected class ViewHolder {
        private TextView tvFg3AdapterPeccancyCarnumber;
        private TextView tvFg3AdapterPeccancyTimes;
        private TextView tvFg3AdapterPeccancyPmoney;
        private TextView tvFg3AdapterPeccancyPscore;
        private ImageView ivDeletecar;

        public ViewHolder(View view) {
            tvFg3AdapterPeccancyCarnumber = (TextView) view.findViewById(R.id.tv_fg3Adapter_peccancy_carnumber);
            tvFg3AdapterPeccancyTimes = (TextView) view.findViewById(R.id.tv_fg3Adapter_peccancy_times);
            tvFg3AdapterPeccancyPmoney = (TextView) view.findViewById(R.id.tv_fg3Adapter_peccancy_pmoney);
            tvFg3AdapterPeccancyPscore = (TextView) view.findViewById(R.id.tv_fg3Adapter_peccancy_pscore);
            ivDeletecar = (ImageView) view.findViewById(R.id.iv_deletecar);
        }
    }
}

