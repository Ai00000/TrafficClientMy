package com.mad.trafficclient.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.gson.Gson;
import com.mad.trafficclient.R;
import com.mad.trafficclient.httppost.HttpRequest;
import com.mad.trafficclient.model.XActionInfo;
import com.mad.trafficclient.model.XCarInfo;
import com.mad.trafficclient.model.XPecInfo;
import com.mad.trafficclient.model.XPecType;
import com.mad.trafficclient.model.XUserInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Fragment_7 extends Fragment {
    private FragmentPagerAdapter pagerAdapter;
    private List<Fragment> data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_7, container, false);
    }

    private ViewPager pager;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pager = (ViewPager) view.findViewById(R.id.pager);
        initValues();
        data = new ArrayList<Fragment>();
        data.add(new PecFragment());
        data.add(new RepeatPecFragment());
        data.add(new SexFragment());
        data.add(new NumPecFragment());
        data.add(new AgePecFragment());
        data.add(new TimePecFragment());
        data.add(new ActionPecFragment());
        pagerAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return data.get(position);
            }

            @Override
            public int getCount() {
                return data.size();
            }
        };
        pager.setAdapter(pagerAdapter);
    }

    final int TAG1 = 1;
    final int TAG2 = 2;
    final int TAG3 = 3;
    final int TAG4 = 4;
    final int TAG5 = 5;
    final int TAG6 = 6;
    final int TAG7 = 7;
    final int TAG8 = 8;
    final int TAG9 = 9;
    final int TAG10 = 10;
    final int TAG11 = 11;

    private Gson gson;
    private static List<XPecInfo> pecInfoList;
    private List<XPecType> pecTypeList;
    private List<XUserInfo> userInfoList;
    private List<XCarInfo> carInfoList;

    private static List<XUserInfo> userHasPecList;
    private static List<XUserInfo> userNotHasPecList;

    private static int hasRepeatNum;
    private static int notHasRepeatNum;

    private static List<Integer> repeatNumList;

    private static List<Integer> ageHasPecList;
    private static List<Integer> ageNotHasPecList;

    private static List<Integer> timePecList;

    private static List<XActionInfo> actionInfoList;

    private static Handler pecHandler;
    private static Handler repeatHandler;
    private static Handler numHandler;
    private static Handler ageHandler;
    private static Handler timeHandler;
    private static Handler actionHandler;
    private static Handler sexHandler;

    private static List<XCarInfo> hasPecList;//包含黑牌小车的所有违章车辆
    private static List<XCarInfo> notHasPecList;//包含黑牌小车的所有违章车辆

    private static List<XPecInfo> notRepeatPecList;

    private static int manHasPec;
    private static int womanHasPec;

    private static int manNotHasPec;
    private static int womanNotHasPec;
    public void initValues() {
        gson = new Gson();
        pecTypeList = new ArrayList<XPecType>();
        pecInfoList = new ArrayList<XPecInfo>();
        userInfoList = new ArrayList<XUserInfo>();
        carInfoList = new ArrayList<XCarInfo>();
        userHasPecList = new ArrayList<XUserInfo>();
        userNotHasPecList = new ArrayList<XUserInfo>();
        hasRepeatNum = 0;
        notHasRepeatNum = 0;
        repeatNumList = new ArrayList<Integer>();
        ageHasPecList = new ArrayList<Integer>();
        ageNotHasPecList = new ArrayList<Integer>();
        timePecList = new ArrayList<Integer>();
        actionInfoList = new ArrayList<XActionInfo>();
        hasPecList = new ArrayList<XCarInfo>();
        notHasPecList = new ArrayList<XCarInfo>();
        notRepeatPecList = new ArrayList<XPecInfo>();
        manHasPec = 0;
        womanHasPec=0;
        handler.sendEmptyMessage(TAG1);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TAG1:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            getPecInfoList();
                        }
                    }).start();
                    break;
                case TAG2:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            getPecTypeList();
                        }
                    }).start();
                    break;
                case TAG3:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            getCarInfoList();
                        }
                    }).start();
                    break;
                case TAG4:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            getUserInfoList();
                        }
                    }).start();
                    break;
                case TAG5:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            getHasPecList();
                        }
                    }).start();
                    break;
                case TAG6:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            getAgeHasPecList();
                        }
                    }).start();
                    break;
                case TAG7:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            getTimePecList();
                        }
                    }).start();
                    break;
                case TAG8:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            getXActionInfoList();
                        }
                    }).start();
                    break;
                case TAG9:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            getHasBlackRepeatList();
                        }
                    }).start();
                    break;
                case TAG10:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            getHasBlackPecList();
                        }
                    }).start();
                    break;
                case TAG11:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            getSexPecList();
                        }
                    }).start();
                    break;
            }
        }
    };

    public void getPecInfoList() {
        try {
            HttpRequest.request("GetAllCarPeccancy", new JSONObject().put("UserName", "admin"), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    if (TextUtils.isEmpty(jsonObject.toString()))
                        return;
                    try {
                        String ROWS_DETAIL = jsonObject.getString("ROWS_DETAIL");
                        JSONArray array = new JSONArray(ROWS_DETAIL);
                        for (int i = 0; i < array.length(); i++) {
                            pecInfoList.add(gson.fromJson(array.getJSONObject(i).toString(), XPecInfo.class));
                        }
                        Log.e("pecInfoList", pecInfoList.size() + "");
                        handler.sendEmptyMessage(TAG2);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getPecTypeList() {
        try {
            HttpRequest.request("GetPeccancyType", new JSONObject().put("UserName", "admin"), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    if (TextUtils.isEmpty(jsonObject.toString()))
                        return;
                    try {
                        String ROWS_DETAIL = jsonObject.getString("ROWS_DETAIL");
                        JSONArray array = new JSONArray(ROWS_DETAIL);
                        for (int i = 0; i < array.length(); i++) {
                            pecTypeList.add(gson.fromJson(array.getJSONObject(i).toString(), XPecType.class));
                        }
                        Log.e("pecTypeList", pecTypeList.size() + "");
                        handler.sendEmptyMessage(TAG3);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getCarInfoList() {
        try {
            HttpRequest.request("GetCarInfo", new JSONObject().put("UserName", "admin"), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    if (TextUtils.isEmpty(jsonObject.toString()))
                        return;
                    try {
                        String ROWS_DETAIL = jsonObject.getString("ROWS_DETAIL");
                        JSONArray array = new JSONArray(ROWS_DETAIL);
                        for (int i = 0; i < array.length(); i++) {
                            carInfoList.add(gson.fromJson(array.getJSONObject(i).toString(), XCarInfo.class));
                        }
                        Log.e("carInfoList", carInfoList.size() + "");
                        handler.sendEmptyMessage(TAG4);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getUserInfoList() {
        try {
            HttpRequest.request("GetSUserInfo", new JSONObject().put("UserName", "admin"), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    if (TextUtils.isEmpty(jsonObject.toString()))
                        return;
                    try {
                        String ROWS_DETAIL = jsonObject.getString("ROWS_DETAIL");
                        JSONArray array = new JSONArray(ROWS_DETAIL);
                        for (int i = 0; i < array.length(); i++) {
                            userInfoList.add(gson.fromJson(array.getJSONObject(i).toString(), XUserInfo.class));
                        }
                        Log.e("userInfoList", userInfoList.size() + "");
                        handler.sendEmptyMessage(TAG5);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void sendHandler(Handler handler) {
        if (handler != null)
            handler.sendEmptyMessage(1);
    }

    public void getHasPecList() {
        XUserInfo userInfo;
        XCarInfo carInfo;
        int count;
        for (int i = 0; i < userInfoList.size(); i++) {
            userInfo = userInfoList.get(i);
            count = 0;
            for (int j = 0; j < carInfoList.size(); j++) {
                carInfo = carInfoList.get(j);
                if (TextUtils.equals(userInfo.getPcardid(), carInfo.getPcardid())) {
                    for (int k = 0; k < pecInfoList.size(); k++) {
                        if (TextUtils.equals(carInfo.getCarnumber(), pecInfoList.get(k).getCarnumber())) {
                            count++;
                        }
                    }
                }
            }
            if (count == 0) {
                userNotHasPecList.add(userInfo);
            } else {
                userHasPecList.add(userInfo);
            }
        }

        Log.e("userHasPecList", userHasPecList.size() + "");
        Log.e("userNotHasPecList", userNotHasPecList.size() + "");

        handler.sendEmptyMessage(TAG6);
    }

    public void getAgeHasPecList() {
        int age;
        int age50 = 0, age60 = 0, age70 = 0, age80 = 0, age90 = 0;

        for (int i = 0; i < userHasPecList.size(); i++) {
            age = Integer.parseInt(userHasPecList.get(i).getPcardid().substring(8, 10));
            if (age >= 50 && age < 60) {
                age50++;
            } else if (age >= 60 && age < 70) {
                age60++;
            } else if (age >= 70 && age < 80) {
                age70++;
            } else if (age >= 80 && age < 90) {
                age80++;
            } else if (age >= 90) {
                age90++;
            }
        }
        ageHasPecList.add(age50);
        ageHasPecList.add(age60);
        ageHasPecList.add(age70);
        ageHasPecList.add(age80);
        ageHasPecList.add(age90);

        age50 = 0;
        age60 = 0;
        age70 = 0;
        age80 = 0;
        age90 = 0;
        for (int i = 0; i < userNotHasPecList.size(); i++) {
            age = Integer.parseInt(userNotHasPecList.get(i).getPcardid().substring(8, 10));
            if (age >= 50 && age < 60) {
                age50++;
            } else if (age >= 60 && age < 70) {
                age60++;
            } else if (age >= 70 && age < 80) {
                age70++;
            } else if (age >= 80 && age < 90) {
                age80++;
            } else if (age >= 90) {
                age90++;
            }
        }
        ageNotHasPecList.add(age50);
        ageNotHasPecList.add(age60);
        ageNotHasPecList.add(age70);
        ageNotHasPecList.add(age80);
        ageNotHasPecList.add(age90);

        Log.e("ageHasPecList", ageHasPecList.size() + "\t\t" + ageHasPecList.toString());
        Log.e("ageNotHasPecList", ageNotHasPecList.size() + "\t\t" + ageNotHasPecList.toString());
        handler.sendEmptyMessage(TAG7);
        sendHandler(ageHandler);
    }

    public void getTimePecList() {
        int time;
        int time02 = 0, time24 = 0, time46 = 0, time68 = 0, time810 = 0, time1012 = 0, time1214 = 0, time1416 = 0,
                time1618 = 0, time1820 = 0, time2022 = 0, time2224 = 0;
        String timeStr;
        int index;
        for (int i = 0; i < pecInfoList.size(); i++) {
            timeStr = pecInfoList.get(i).getPdatetime();
            index = timeStr.indexOf(" ");
            time = Integer.parseInt(timeStr.substring(index + 1, index + 3));
            if (time >= 0 && time < 2) {
                time02++;
            } else if (time >= 2 && time < 4) {
                time24++;
            } else if (time >= 4 && time < 6) {
                time46++;
            } else if (time >= 6 && time < 8) {
                time68++;
            } else if (time >= 8 && time < 10) {
                time810++;
            } else if (time >= 10 && time < 12) {
                time1012++;
            } else if (time >= 12 && time < 14) {
                time1214++;
            } else if (time >= 14 && time < 16) {
                time1416++;
            } else if (time >= 16 && time < 18) {
                time1618++;
            } else if (time >= 18 && time < 20) {
                time1820++;
            } else if (time >= 20 && time < 22) {
                time2022++;
            } else if (time >= 22 && time < 24) {
                time2224++;
            }
        }
        timePecList.add(time02);
        timePecList.add(time24);
        timePecList.add(time46);
        timePecList.add(time68);
        timePecList.add(time810);
        timePecList.add(time1012);
        timePecList.add(time1214);
        timePecList.add(time1416);
        timePecList.add(time1618);
        timePecList.add(time1820);
        timePecList.add(time2022);
        timePecList.add(time2224);
        int sum = 0;
        for (int i = 0; i < timePecList.size(); i++) {
            sum += timePecList.get(i);
        }
        Log.e("timePecList", timePecList.size() + "\t\t" + timePecList.toString() + "\t\t" + sum);
        handler.sendEmptyMessage(TAG8);
        sendHandler(timeHandler);
    }


    public void getXActionInfoList() {
        XPecType type;
        int count;
        for (int i = 0; i < pecTypeList.size(); i++) {
            type = pecTypeList.get(i);
            count = 0;
            for (int j = 0; j < pecInfoList.size(); j++) {
                if (TextUtils.equals(type.getPcode(), pecInfoList.get(j).getPcode())) {
                    count++;
                }
            }
            actionInfoList.add(new XActionInfo(type.getPcode(), count));
        }
        Collections.sort(actionInfoList);
        Collections.reverse(actionInfoList);
        Log.e("actionInfoList", actionInfoList.size() + "\t\t" + actionInfoList.toString());
        sendHandler(actionHandler);
        handler.sendEmptyMessage(TAG9);
    }

    public void getHasBlackRepeatList() {
        XPecInfo pecInfo;
        boolean isExists;
        for (int i = 0; i < pecInfoList.size(); i++) {
            pecInfo = pecInfoList.get(i);
            isExists = false;
            for (int j = 0; j < notRepeatPecList.size(); j++) {
                if (TextUtils.equals(pecInfo.getCarnumber(), notRepeatPecList.get(j).getCarnumber())) {
                    isExists = true;
                    break;
                }
            }
            if (isExists)
                continue;
            notRepeatPecList.add(pecInfo);
        }
        Log.e("notRepeatPecList", notRepeatPecList.size() + "\t\t" + notRepeatPecList.toString());

        int count;
        int pec12 = 0, pec35 = 0, pec6 = 0, hasRepeatPec = 0, notHasRepeatPec = 0;
        for (int i = 0; i < notRepeatPecList.size(); i++) {
            pecInfo = notRepeatPecList.get(i);
            count = 0;
            for (int j = 0; j < pecInfoList.size(); j++) {
                if (TextUtils.equals(pecInfo.getCarnumber(), pecInfoList.get(j).getCarnumber())) {
                    count++;
                }
            }
            if (count >= 1 && count <= 2) {
                pec12++;
            } else if (count >= 3 && count <= 5) {
                pec35++;
            } else if (count >= 6) {
                pec6++;
            }

            if (count == 1) {
                notHasRepeatPec++;
            } else if (count > 1) {
                hasRepeatPec++;
            }
        }
        repeatNumList.add(pec12);
        repeatNumList.add(pec35);
        repeatNumList.add(pec6);

        hasRepeatNum = hasRepeatPec;
        notHasRepeatNum = notHasRepeatPec;

        Log.e("repeatNumList", repeatNumList.size() + "\t\t" + repeatNumList.toString());
        Log.e("hasRepeatNum", hasRepeatNum + "");
        Log.e("notHasRepeatNum", notHasRepeatNum + "");

        handler.sendEmptyMessage(TAG10);
        sendHandler(repeatHandler);
        sendHandler(numHandler);
    }

    public void getHasBlackPecList() {
        XCarInfo info;
        boolean isExists;
        for (int i = 0; i < carInfoList.size(); i++) {
            info = carInfoList.get(i);
            isExists = false;
            for (int j = 0; j < pecInfoList.size(); j++) {
                if (TextUtils.equals(info.getCarnumber(), pecInfoList.get(j).getCarnumber())) {
                    isExists = true;
                    hasPecList.add(info);
                    break;
                }
            }
            if (!isExists)
                notHasPecList.add(info);
        }
        Log.e("hasPecList", hasPecList.size() + "\t\t" + hasPecList.toString());
        Log.e("notHasPecList", notHasPecList.size() + "\t\t" + notHasPecList.toString());
        handler.sendEmptyMessage(TAG11);
        sendHandler(pecHandler);
    }

    public void getSexPecList() {
        String sex;
        int man = 0, woman = 0;
        for (int i = 0;i<userHasPecList.size();i++){
            sex = userHasPecList.get(i).getPsex();
            if (TextUtils.equals(sex,"男")){
                man++;
            }else{
                woman++;
            }
        }
        manHasPec = man;
        womanHasPec = woman;
        sendHandler(sexHandler);
        Log.e("manHasPec", manHasPec+"");
        Log.e("womanHasPec", womanHasPec+"");

         man = 0; woman = 0;
        for (int i = 0;i<userNotHasPecList.size();i++){
            sex = userNotHasPecList.get(i).getPsex();
            if (TextUtils.equals(sex,"男")){
                man++;
            }else{
                woman++;
            }
        }
        manNotHasPec = man;
        womanNotHasPec = woman;
        sendHandler(sexHandler);
        Log.e("manNotHasPec", manNotHasPec+"");
        Log.e("womanNotHasPec", womanNotHasPec+"");


    }

    public static DecimalFormat format = new DecimalFormat("###,###,###,##0.00");

    public static class PecFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_7_pec, container, false);
        }

        private PieChart pieChart;
        private String[] titles = {"有违章车辆", "无违章车辆"};
        private List<Integer> list;

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            pieChart = (PieChart) view.findViewById(R.id.pieChart);

            pieChart.setDrawHoleEnabled(false);
            pieChart.setUsePercentValues(true);
            pieChart.setDescription("");

            Legend legend = pieChart.getLegend();
            legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
            legend.setTextSize(15);
            list = new ArrayList<Integer>();
            list.add(notRepeatPecList.size());
            list.add(notHasPecList.size());
            setData(list);
            pecHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    list.clear();
                    list.add(notRepeatPecList.size());
                    list.add(notHasPecList.size());
                    setData(list);
                }
            };
        }

        public void setData(List<Integer> list) {
            List<String> xVals = new ArrayList<String>();
            List<Entry> yVals = new ArrayList<Entry>();
            for (int i = 0; i < list.size(); i++) {
                xVals.add(titles[i]);
                yVals.add(new Entry(list.get(i), i));
            }
            PieDataSet dataSet = new PieDataSet(yVals, "有无违章车辆占比分布图");
            dataSet.addColor(ColorTemplate.VORDIPLOM_COLORS[0]);
            dataSet.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float v, Entry entry, int i, ViewPortHandler viewPortHandler) {
                    return format.format(v) + "%" + "\t" + (int) entry.getVal() + "辆";
                }
            });
            PieData data = new PieData(xVals, dataSet);

            pieChart.setData(data);
            pieChart.invalidate();
        }
    }

    public static class RepeatPecFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_7_repeat, container, false);
        }

        private PieChart pieChart;
        private String[] titles = {"有重复违章车辆", "无重复违章车辆"};
        private List<Integer> list;

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            pieChart = (PieChart) view.findViewById(R.id.pieChart);

            pieChart.setDrawHoleEnabled(false);
            pieChart.setUsePercentValues(true);
            pieChart.setDescription("");

            Legend legend = pieChart.getLegend();
            legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
            legend.setTextSize(15);
            list = new ArrayList<Integer>();
            list.add(hasRepeatNum);
            list.add(notHasRepeatNum);
            setData(list);
            repeatHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    list.clear();
                    list.add(hasRepeatNum);
                    list.add(notHasRepeatNum);
                    setData(list);
                }
            };
        }

        public void setData(List<Integer> list) {
            List<String> xVals = new ArrayList<String>();
            List<Entry> yVals = new ArrayList<Entry>();
            for (int i = 0; i < list.size(); i++) {
                xVals.add(titles[i]);
                yVals.add(new Entry(list.get(i), i));
            }
            PieDataSet dataSet = new PieDataSet(yVals, "有无重复违章车辆占比分布图");
            dataSet.addColor(ColorTemplate.VORDIPLOM_COLORS[1]);
            dataSet.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float v, Entry entry, int i, ViewPortHandler viewPortHandler) {
                    return format.format(v) + "%" + "\t" + (int) entry.getVal() + "辆";
                }
            });
            PieData data = new PieData(xVals, dataSet);

            pieChart.setData(data);
            pieChart.invalidate();
        }
    }
    public static class SexFragment extends Fragment{
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_7_sex,container,false);
        }

        private PieChart pieChart;
        private String[] titles = {"违章男性", "违章女性"};
        private List<Integer> list;

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            pieChart = (PieChart) view.findViewById(R.id.pieChart);

            pieChart.setDrawHoleEnabled(false);
            pieChart.setUsePercentValues(true);
            pieChart.setDescription("");

            Legend legend = pieChart.getLegend();
            legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
            legend.setTextSize(15);

            list = new ArrayList<Integer>();
            list.add(manHasPec);
            list.add(womanHasPec);
            setData(list);
            sexHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    list.clear();
                    list.add(manHasPec);
                    list.add(womanHasPec);
                    setData(list);
                }
            };
        }

        public void setData(List<Integer> list) {
            List<String> xVals = new ArrayList<String>();
            List<Entry> yVals = new ArrayList<Entry>();
            for (int i = 0; i < list.size(); i++) {
                xVals.add(titles[i]);
                yVals.add(new Entry(list.get(i), i));
            }
            PieDataSet dataSet = new PieDataSet(yVals, "男女司机违章占比分布图");
            dataSet.addColor(ColorTemplate.VORDIPLOM_COLORS[1]);
            dataSet.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float v, Entry entry, int i, ViewPortHandler viewPortHandler) {
                    return format.format(v) + "%" + "\t" + (int) entry.getVal() + "人";
                }
            });
            PieData data = new PieData(xVals, dataSet);

            pieChart.setData(data);
            pieChart.invalidate();
        }
    }
    public static class NumPecFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_7_num, container, false);
        }

        private HorizontalBarChart barChart;
        private String[] titles = {"1-2条违章记录", "3-5条违章记录", "5条以上违章记录"};

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            barChart = (HorizontalBarChart) view.findViewById(R.id.barChart);

            barChart.setDrawBarShadow(false);
            barChart.setDescription("");

            XAxis xAxis = barChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setTextSize(12);
            xAxis.setDrawGridLines(false);

            barChart.getAxisLeft().setEnabled(false);
            YAxis rightAxis = barChart.getAxisRight();
            rightAxis.setTextSize(12);
            rightAxis.setValueFormatter(new PercentFormatter());

            Legend legend = barChart.getLegend();
            legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
            legend.setTextSize(15);

            setData(repeatNumList);

            numHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    setData(repeatNumList);
                }
            };
        }

        public void setData(final List<Integer> list) {
            int sum = 0;
            for (int i = 0; i < list.size(); i++) {
                sum += list.get(i);
            }
            List<String> xValues = new ArrayList<String>();
            List<BarEntry> yValues = new ArrayList<BarEntry>();
            for (int i = 0; i < list.size(); i++) {
                xValues.add(titles[i]);
                yValues.add(new BarEntry((float) (Math.round(list.get(i) * 10000 / sum) / 100.0), i));
            }
            BarDataSet dataSet = new BarDataSet(yValues, "违章次数占比分布图");
            dataSet.setValueTextSize(14);
            dataSet.setValueTextColor(ColorTemplate.getHoloBlue());
            dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
            dataSet.setBarSpacePercent(30);
            dataSet.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float v, Entry entry, int i, ViewPortHandler viewPortHandler) {
                    return format.format(v) + "%\t" + list.get(entry.getXIndex()) + "辆";
                }
            });

            BarData data = new BarData(xValues, dataSet);

            barChart.setData(data);
            barChart.invalidate();
        }
    }

    public static class AgePecFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_7_age, container, false);
        }

        private BarChart barChart;
        private String[] titles = {"50后", "60后", "70后", "80后", "90后"};

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            barChart = (BarChart) view.findViewById(R.id.barChart);
            barChart.setDrawBarShadow(false);
            barChart.setDescription("");

            XAxis xAxis = barChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setTextSize(12);
            xAxis.setDrawGridLines(false);

            barChart.getAxisRight().setEnabled(false);
            YAxis leftAxis = barChart.getAxisLeft();
            leftAxis.setTextSize(12);

            Legend legend = barChart.getLegend();
            legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
            legend.setTextSize(15);

            setData(ageHasPecList, ageNotHasPecList);

            ageHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    setData(ageHasPecList, ageNotHasPecList);
                }
            };
        }

        public void setData(List<Integer> list1, List<Integer> list2) {

            List<String> xValues = new ArrayList<String>();
            List<BarEntry> yValues = new ArrayList<BarEntry>();
            for (int i = 0; i < list1.size(); i++) {
                xValues.add(titles[i]);
                yValues.add(new BarEntry(new float[]{list2.get(i), list1.get(i)}, i));
            }
            BarDataSet dataSet = new BarDataSet(yValues, "各年龄段有无违章占比图");
            dataSet.setValueTextSize(14);
            dataSet.setValueTextColor(ColorTemplate.getHoloBlue());
            dataSet.addColor(ColorTemplate.VORDIPLOM_COLORS[4]);
            dataSet.setBarSpacePercent(20);
            dataSet.setStackLabels(new String[]{"无违章", "有违章"});
            dataSet.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float v, Entry entry, int i, ViewPortHandler viewPortHandler) {
                    return Math.round(v * 10000 / entry.getVal()) / 100.0 + "%";
                }
            });

            BarData data = new BarData(xValues, dataSet);

            barChart.setData(data);
            barChart.invalidate();
        }

    }

    public static class TimePecFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_7_time, container, false);
        }

        private BarChart barChart;
        private String[] titles = {"0:00-2:00", "2:00-4:00", "4:00-6:00", "6:00-8:00", "8:00-10:00", "10:00-12:00",
                "12:00-14:00", "14:00-16:00", "16:00-18:00", "18:00-20:00", "20:00-22:00", "22:00-24:00"};

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            barChart = (BarChart) view.findViewById(R.id.barChart);
            barChart.setDrawBarShadow(false);
            barChart.setDescription("");

            XAxis xAxis = barChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setTextSize(12);
            xAxis.setDrawGridLines(false);

            barChart.getAxisRight().setEnabled(false);
            YAxis leftAxis = barChart.getAxisLeft();
            leftAxis.setTextSize(12);
            leftAxis.setValueFormatter(new PercentFormatter());

            Legend legend = barChart.getLegend();
            legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
            legend.setTextSize(15);

            setData(timePecList);

            timeHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    setData(timePecList);
                }
            };
        }

        public void setData(final List<Integer> list) {
            int sum = 0;
            for (int i = 0; i < list.size(); i++) {
                sum += list.get(i);
            }
            List<String> xValues = new ArrayList<String>();
            List<BarEntry> yValues = new ArrayList<BarEntry>();
            for (int i = 0; i < list.size(); i++) {
                xValues.add(titles[i]);
                yValues.add(new BarEntry((float) (Math.round(list.get(i) * 10000 / sum) / 100.0), i));
            }
            BarDataSet dataSet = new BarDataSet(yValues, "各时间段违章占比图");
            dataSet.setValueTextSize(14);
            dataSet.setValueTextColor(ColorTemplate.getHoloBlue());
            dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
            dataSet.setBarSpacePercent(20);
            dataSet.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float v, Entry entry, int i, ViewPortHandler viewPortHandler) {
                    return format.format(v) + "%\t" + list.get(entry.getXIndex()) + "次";
                }
            });

            BarData data = new BarData(xValues, dataSet);

            barChart.setData(data);
            barChart.invalidate();
        }
    }

    public static class ActionPecFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_7_action, container, false);
        }

        private HorizontalBarChart barChart;
        private List<XActionInfo> list;

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            barChart = (HorizontalBarChart) view.findViewById(R.id.barChart);

            barChart.setDrawBarShadow(false);
            barChart.setDescription("");

            XAxis xAxis = barChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setTextSize(14);
            xAxis.setDrawGridLines(false);

            barChart.getAxisLeft().setEnabled(false);
            YAxis rightAxis = barChart.getAxisRight();
            rightAxis.setTextSize(12);
            rightAxis.setValueFormatter(new PercentFormatter());

            Legend legend = barChart.getLegend();
            legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
            legend.setTextSize(15);

            list = new ArrayList<XActionInfo>();
            for (int i = 0; i < 10; i++) {
                list.add(actionInfoList.get(i));
            }
            Collections.reverse(list);

            setData(list);

            actionHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    setData(list);
                }
            };
        }

        public void setData(final List<XActionInfo> list) {
            int sum = pecInfoList.size();
            List<String> xValues = new ArrayList<String>();
            List<BarEntry> yValues = new ArrayList<BarEntry>();
            for (int i = 0; i < list.size(); i++) {
                xValues.add(list.get(i).getAction());
                yValues.add(new BarEntry((float) (Math.round(list.get(i).getCount() * 10000 / sum) / 100.0), i));
            }
            BarDataSet dataSet = new BarDataSet(yValues, "排名前十交通违法行为l占比分布图");
            dataSet.setValueTextSize(14);
            dataSet.setValueTextColor(ColorTemplate.getHoloBlue());
            dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
            dataSet.setBarSpacePercent(30);
            dataSet.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float v, Entry entry, int i, ViewPortHandler viewPortHandler) {
                    return format.format(v) + "%\t" + list.get(entry.getXIndex()).getCount() + "次";
                }
            });

            BarData data = new BarData(xValues, dataSet);

            barChart.setData(data);
            barChart.invalidate();
        }
    }
}
