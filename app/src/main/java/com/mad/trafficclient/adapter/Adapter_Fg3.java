package com.mad.trafficclient.adapter;

/**
 * Created by Administrator on 2017/6/2.
 */

//public class Adapter_Fg3 {
//}
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CheckBox;

import com.mad.trafficclient.R;
import com.mad.trafficclient.model.TrafficLightInfo;

public class Adapter_Fg3 extends BaseAdapter {

    private List<TrafficLightInfo> objects = new ArrayList<TrafficLightInfo>();
    public List<Integer> lists = new ArrayList<Integer>();

    private Context context;
    private LayoutInflater layoutInflater;

    public Adapter_Fg3(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void getData(List<TrafficLightInfo> objects){
        this.objects=objects;
        this.lists.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public TrafficLightInfo getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.adapter_fg3, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((TrafficLightInfo)getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(final TrafficLightInfo object, ViewHolder holder) {
        //TODO implement
        holder.tvFg3AdapterLightId.setText(object.getLightId()+"");
        holder.tvFg3AdapterRed.setText(object.getRed()+"");
        holder.tvFg3AdapterGreen.setText(object.getGreen()+"");
        holder.tvFg3AdapterYellow.setText(object.getYellow()+"");

        holder.cbFg3AdapterSet.setOnCheckedChangeListener(null);
        if (lists.contains(object.getLightId())){
            holder.cbFg3AdapterSet.setChecked(true);
        }else {
            holder.cbFg3AdapterSet.setChecked(false);
        }
        holder.cbFg3AdapterSet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    lists.add(object.getLightId());
                }else {
                    for (int i = 0; i <lists.size() ; i++) {
                        if (lists.get(i)==object.getLightId()){
                            lists.remove(i);
                        }
                    }
                }
            }
        });

    }

    protected class ViewHolder {
        private TextView tvFg3AdapterLightId;
        private TextView tvFg3AdapterRed;
        private TextView tvFg3AdapterGreen;
        private TextView tvFg3AdapterYellow;
        private CheckBox cbFg3AdapterSet;

        public ViewHolder(View view) {
            tvFg3AdapterLightId = (TextView) view.findViewById(R.id.tv_fg3Adapter_lightId);
            tvFg3AdapterRed = (TextView) view.findViewById(R.id.tv_fg3Adapter_red);
            tvFg3AdapterGreen = (TextView) view.findViewById(R.id.tv_fg3Adapter_green);
            tvFg3AdapterYellow = (TextView) view.findViewById(R.id.tv_fg3Adapter_yellow);
            cbFg3AdapterSet = (CheckBox) view.findViewById(R.id.cb_fg3Adapter_set);
        }
    }
}

