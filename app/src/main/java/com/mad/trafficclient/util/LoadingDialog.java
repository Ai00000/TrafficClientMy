package com.mad.trafficclient.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.mad.trafficclient.R;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.Toast;

public class LoadingDialog {

	public static Dialog dialog;
	public static Date data;
	public static SimpleDateFormat format;
	
	public static void showToast(Context context,String msg){
		Toast.makeText(context,msg, 1).show();
	}
	
	public static void showDialog(Context context){
		dialog=new Dialog(context,R.style.dialog);
		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.loading_dialog);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
	}
	public static void disDialog(){
		dialog.dismiss();
	}
	
	public static String getTime(){
		data=new Date(System.currentTimeMillis());
		format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String string=format.format(data);
		return string;
	}
}
