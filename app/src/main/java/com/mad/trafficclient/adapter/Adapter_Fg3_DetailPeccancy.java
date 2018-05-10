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
import android.widget.TextView;

import com.mad.trafficclient.R;
import com.mad.trafficclient.model.DetailPeccancyInfo;

public class Adapter_Fg3_DetailPeccancy extends BaseAdapter {

    private List<DetailPeccancyInfo> objects = new ArrayList<DetailPeccancyInfo>();

    private Context context;
    private LayoutInflater layoutInflater;

    public Adapter_Fg3_DetailPeccancy(Context context,List<DetailPeccancyInfo> objects) {
        this.context = context;
        this.objects=objects;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public DetailPeccancyInfo getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.adapter_detail_peccancy, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((DetailPeccancyInfo)getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(DetailPeccancyInfo object, ViewHolder holder) {
        //TODO implement
        holder.tvFg3AdapterDetailCarnumber.setText("车牌号："+object.getCarnumber());
        holder.tvFg3AdapterDetailPcode.setText("违章代码："+object.getPcode());
        holder.tvFg3AdapterDetailPaddr.setText("违章地点："+object.getPaddr());
        holder.tvFg3AdapterDetailPdatetime.setText("违章时间："+object.getPdatetime());
    }

    protected class ViewHolder {
        private TextView tvFg3AdapterDetailCarnumber;
        private TextView tvFg3AdapterDetailPcode;
        private TextView tvFg3AdapterDetailPaddr;
        private TextView tvFg3AdapterDetailPdatetime;

        public ViewHolder(View view) {
            tvFg3AdapterDetailCarnumber = (TextView) view.findViewById(R.id.tv_fg3Adapter_detail_carnumber);
            tvFg3AdapterDetailPcode = (TextView) view.findViewById(R.id.tv_fg3Adapter_detail_pcode);
            tvFg3AdapterDetailPaddr = (TextView) view.findViewById(R.id.tv_fg3Adapter_detail_paddr);
            tvFg3AdapterDetailPdatetime = (TextView) view.findViewById(R.id.tv_fg3Adapter_detail_pdatetime);
        }
    }
}

