package com.mad.trafficclient.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.mad.trafficclient.R;

/**
 * Created by Administrator on 2017/6/2.
 */

public class Activity_PeccancyImg extends Activity implements View.OnClickListener {
    private ImageView iv_img1;
    private ImageView iv_img2;
    private ImageView iv_img3;
    private ImageView iv_img4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peccancy_img);
        initView();
    }

    private void initView() {
        iv_img1 = (ImageView) findViewById(R.id.iv_img1);
        iv_img2 = (ImageView) findViewById(R.id.iv_img2);
        iv_img3 = (ImageView) findViewById(R.id.iv_img3);
        iv_img4 = (ImageView) findViewById(R.id.iv_img4);
        iv_img1.setOnClickListener(this);
        iv_img2.setOnClickListener(this);
        iv_img3.setOnClickListener(this);
        iv_img4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        intent.setClass(Activity_PeccancyImg.this,Activity_Img.class);
        Bundle bundle=new Bundle();
        int id=0;
        switch (v.getId()){
            case R.id.iv_img1:
                id=1;
                break;
            case R.id.iv_img2:
                id=2;
                break;
            case R.id.iv_img3:
                id=3;
                break;
            case R.id.iv_img4:
                id=4;
                break;
        }
        bundle.putInt("img",id);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
