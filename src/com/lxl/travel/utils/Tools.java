package com.lxl.travel.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Message;

public class Tools {
	private static ProgressDialog progressDialog;
	/**根据msg，显示ProgressDialog*/
	public static void showProgressDialog(Activity activity, CharSequence msg){
		if (progressDialog == null){
			progressDialog = new ProgressDialog(activity);
			progressDialog.setMessage(msg);
			progressDialog.setCanceledOnTouchOutside(false);
			progressDialog.show();
		}
	}
	/**取消ProgressDialog*/
	public static void closeProgressDialog() {
		if (progressDialog != null){
			progressDialog.cancel();
		}
	}
}
