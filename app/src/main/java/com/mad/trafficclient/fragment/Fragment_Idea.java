package com.mad.trafficclient.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mad.trafficclient.R;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/6/2.
 */

public class Fragment_Idea extends Fragment {
    private ProgressBar progressBar_idea;
    private TextView tv_pm_now;
    private ImageView iv_show;

    private Timer timer;

    private int index=0;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (!isAdded()){
                return;
            }
            int pm=getNum();
            if (pm>=200){
                tv_pm_now.setText("当前PM2.5值为："+pm+",空气污染严重，不建议出行");
                tv_pm_now.setTextColor(getResources().getColor(R.color.red));
                progressBar_idea.setProgressDrawable(getResources().getDrawable(R.drawable.bar_red));
            }else {
                tv_pm_now.setText("当前PM2.5值为："+pm+",快出去走走吧");
                tv_pm_now.setTextColor(getResources().getColor(R.color.greeen));
                progressBar_idea.setProgressDrawable(getResources().getDrawable(R.drawable.bar_green));
            }

            Message msm=new Message();
            msm.what=pm;
            if (pm>index){
                msm.arg1=1;
            }else {
                msm.arg1=-1;
            }
            mHandler.sendMessage(msm);
        }
    };

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int p=msg.what;
            Message msm=new Message();
            msm.what=p;
            if (msg.arg1>0){
                index+=1;
                progressBar_idea.setProgress(index);
                msm.arg1=1;
            }else {
                index-=1;
                progressBar_idea.setProgress(index);
                msm.arg1=-1;
            }
            if (p==index){
                return;
            }

            mHandler.sendMessageDelayed(msm,1);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_idea, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AlphaAnimation animation=new AlphaAnimation(1.0f,0.0f);
                animation.setFillAfter(true);
                animation.setDuration(3000);
                iv_show.startAnimation(animation);
                iv_show.setVisibility(View.GONE);
                getPm();
            }
        },2000);
    }

    private void getPm() {
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(1);
            }
        },0,10000);
    }

    private int getNum(){
        Random r=new Random();
        return r.nextInt(399)+1;
    }

    private void initView(View view) {
        progressBar_idea = (ProgressBar) view.findViewById(R.id.progressBar_idea);
        tv_pm_now = (TextView) view.findViewById(R.id.tv_pm_now);
        iv_show = (ImageView) view.findViewById(R.id.iv_show);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer!=null){
            timer.cancel();
        }
    }
}
