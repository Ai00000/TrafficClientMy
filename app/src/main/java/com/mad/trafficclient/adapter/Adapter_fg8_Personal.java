package com.mad.trafficclient.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mad.trafficclient.R;
import com.mad.trafficclient.model.CarInfo_fg1;

public class Adapter_fg8_Personal extends BaseAdapter {

    private List<CarInfo_fg1.ROWSDETAILBean> objects = new ArrayList<CarInfo_fg1.ROWSDETAILBean>();

    private Context context;
    private LayoutInflater layoutInflater;

    public Adapter_fg8_Personal(Context context,List<CarInfo_fg1.ROWSDETAILBean> objects) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.objects=objects;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public CarInfo_fg1.ROWSDETAILBean getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.lv_fg8_personal, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews(getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(CarInfo_fg1.ROWSDETAILBean object, ViewHolder holder) {
        holder.imBrandFg1Lv.setImageResource(context.getResources().getIdentifier(object.getCarbrand(), "drawable", context.getPackageName()));
        holder.tvBrandFg1Lv.setText("车辆品牌:" + object.getCarbrand());
        holder.tvCarNumberFg1Lv.setText("车牌号:" + object.getCarnumber());
    }

    protected class ViewHolder {
        private ImageView imBrandFg1Lv;
        private TextView tvCarNumberFg1Lv;
        private TextView tvBrandFg1Lv;

        public ViewHolder(View view) {
            imBrandFg1Lv = (ImageView) view.findViewById(R.id.im_brand_fg1Lv);
            tvCarNumberFg1Lv = (TextView) view.findViewById(R.id.tv_carNumber_fg1Lv);
            tvBrandFg1Lv = (TextView) view.findViewById(R.id.tv_brand_fg1Lv);
        }
    }
}
