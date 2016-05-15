package com.lxl.travel.biz;

import android.content.Context;
import android.widget.Toast;
import com.lxl.travel.activity.TravelAltradionsActivity;
import com.thinkland.sdk.android.DataCallBack;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

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
		Parameters params = new Parameters();
		params.add("pageindex", 1);
		params.add("pagesize", numberkey);
		params.add("keyword", keyName);
		final TravelAltradionsActivity t = (TravelAltradionsActivity)context;

		JuheData.executeWithAPI(context,127,
				"http://api2.juheapi.com/xiecheng/senicspot/ticket/search",
				JuheData.POST, params, new DataCallBack() {

					@Override
					public void onSuccess(int statusCode, String responseString) {
						TravelData = responseString;
						//Log.i("responseString = ", TravelData);
						t.test(responseString,numberkey);
					}

					@Override
					public void onFinish() {
						Toast.makeText(context.getApplicationContext(), "finish",Toast.LENGTH_SHORT).show();

					}

					@Override
					public void onFailure(int statusCode, String responseString, Throwable throwable) {
						// TODO Auto-generated method stub

					}
				});
	}
	public void GetTravelDataAdd(){
		numberkey = numberkey +10;
		GetTravelData();
	}
	
}
