package com.lxl.travel.biz;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.lxl.travel.activity.TravelAltradionsActivity;
import com.lxl.travel.entity.Net;

public class CmTravelBiz {   //这个BIZ类作用是：查询旅游景点信息，由聚合返回。
	private Context context;
	//private AltradionsEntity entity;
	private String TravelData;
	private String keyName;
	private int numberkey = 15;  //开始加载10条，下拉刷新时调用GetTravelDataAdd（）再加10条。
	private String TAG = "CmTravelBiz";

	public CmTravelBiz(Context context,String name) {
		this.context = context;
		this.keyName = name ;
	}
	public void GetTravelData(){
		final TravelAltradionsActivity t = (TravelAltradionsActivity)context;
		Parameters para = new Parameters();
		para.put("id", "1361653183");
		ApiStoreSDK.execute(Net.URL_QUERY_QUNAERTICKET,
				ApiStoreSDK.GET,
				para,
				new ApiCallBack() {
					@Override
					public void onSuccess(int status, String responseString) {
						Log.i(TAG, "onSuccess = "+ responseString);
						TravelData = responseString;
						//Log.i("responseString = ", TravelData);
						t.test(responseString,numberkey);
					}

					@Override
					public void onComplete() {
						Log.i(TAG, "onComplete");
					}

					@Override
					public void onError(int status, String responseString, Exception e) {
						Log.i(TAG, "onError, status: " + status);
						Log.i(TAG, "errMsg: " + (e == null ? "" : e.getMessage()));
					}

				});


	}
	public void GetTravelDataAdd(){
		numberkey = numberkey +10;
		GetTravelData();
	}
	
}
