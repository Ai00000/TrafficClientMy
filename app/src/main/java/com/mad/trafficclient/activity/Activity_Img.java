package com.mad.trafficclient.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mad.trafficclient.R;

/**
 * Created by Administrator on 2017/6/2.
 */

public class Activity_Img extends Activity {
    private ImageView iv_peccancy;
    private LinearLayout linear;
    private float x1,x2,y1,y2,delX,delY;
    private float beishuX=1.0f;
    private float beishuY=1.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img);
        initView();
        int id=this.getIntent().getIntExtra("img",0);
        switch (id){
            case 1:
                iv_peccancy.setImageDrawable(getResources().getDrawable(R.drawable.weizhang01));
                break;
            case 2:
                iv_peccancy.setImageDrawable(getResources().getDrawable(R.drawable.weizhang02));
                break;
            case 3:
                iv_peccancy.setImageDrawable(getResources().getDrawable(R.drawable.weizhang03));
                break;
            case 4:
                iv_peccancy.setImageDrawable(getResources().getDrawable(R.drawable.weizhang04));
                break;
        }

        linear.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        x1=event.getX(0);
                        x1=event.getX(0);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        x1=event.getX(0);
                        x1=event.getX(0);
                        if (event.getPointerCount()==1){
                            return true;
                        }
                        x2=event.getX(1);
                        x2=event.getX(1);
                        if (Math.abs(x1-x2)>delX||Math.abs(y1-y2)>delY){
                            ScaleAnimation animation=new ScaleAnimation(beishuX,beishuX+=0.1f,
                                    beishuY,beishuY+=0.1f, Animation.RELATIVE_TO_SELF,0.5f,
                                    Animation.RELATIVE_TO_SELF,0.5f);
                            animation.setFillAfter(true);
                            iv_peccancy.startAnimation(animation);
                        }else if (Math.abs(x1-x2)<delX||Math.abs(y1-y2)<delY){
                            if (beishuX<0.5f||beishuY<0.5f){
                                return true;
                            }
                            ScaleAnimation animation=new ScaleAnimation(beishuX,beishuX-=0.1f,
                                    beishuY,beishuY-=0.1f, Animation.RELATIVE_TO_SELF,0.5f,
                                    Animation.RELATIVE_TO_SELF,0.5f);
                            animation.setFillAfter(true);
                            iv_peccancy.startAnimation(animation);
                        }
                        break;
                    case MotionEvent.ACTION_POINTER_2_DOWN:
                        x2=event.getX(1);
                        x2=event.getX(1);
                        delX=Math.abs(x1-x2);
                        delY=Math.abs(y1-y2);
                        break;
                }
                return true;
            }
        });

    }

    private void initView() {
        iv_peccancy = (ImageView) findViewById(R.id.iv_peccancy);
        linear = (LinearLayout) findViewById(R.id.linear);
    }
}
