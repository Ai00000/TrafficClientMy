package com.mad.trafficclient.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mad.trafficclient.R;


public class Activity_Reg extends Activity {

	EditText accountET, pwdET, mspwdET;
	Button regBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reg);
		initView();
		initLsenter();
	}

	private void initView() {
		accountET = (EditText) findViewById(R.id.accountET);
		pwdET = (EditText) findViewById(R.id.pwdET);
		mspwdET = (EditText) findViewById(R.id.mspwdET);
		regBtn = (Button) findViewById(R.id.regBtn);
	}

	private void initLsenter() {
		regBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

}
