package com.lxl.travel;

import android.app.Application;
import android.location.Location;

import com.lxl.travel.entity.UserEntity;
import com.lxl.travel.entity.WeatherEntity;
import com.thinkland.sdk.android.JuheSDKInitializer;

public class ETGApplication extends Application{
	private static String host;
	private static int port;
	private static String serviceName;
	public static boolean isRelease = false;
	public static UserEntity userEntity;
	public static WeatherEntity weatherEntity;
	public static ETGApplication instance;
	public static Location location;
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		JuheSDKInitializer.initialize(getApplicationContext());
//		super.onCreate();
//		try {
//			readConfig();
//			connectChatServer();
//		} catch (Exception e) {
//		}
	}

}
