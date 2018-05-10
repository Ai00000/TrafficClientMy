package com.mad.trafficclient.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.mad.trafficclient.R;

public class Activity_Guide extends Activity {

    RelativeLayout guide_RL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean b = this.getSharedPreferences("setting", Context.MODE_PRIVATE).getBoolean("isFirstIn", false);
        if (b) {
            Intent intent = new Intent(Activity_Guide.this,
                    Activity_Login.class);
            startActivity(intent);
            finish();
            return;
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guide);

        this.getSharedPreferences("setting", Context.MODE_PRIVATE).edit().putBoolean("isFirstIn",true).apply();

        guide_RL = (RelativeLayout) findViewById(R.id.guide_RL);
        guide_RL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Guide.this,
                        Activity_Login.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
