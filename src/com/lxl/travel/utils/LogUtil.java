package com.lxl.travel.utils;

import android.util.Log;

import com.lxl.travel.ETGApplication;

/**
 * ͳһ������־
 * baidu android log4j
 * @author tarena
 * 
 */
public class LogUtil {
	public static void i(String tag, Object msg) {
		if (ETGApplication.isRelease) {//判断是否为真机
			return;
		}
		Log.i(tag, String.valueOf(msg));
	}

}
