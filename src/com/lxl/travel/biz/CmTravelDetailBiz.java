package com.lxl.travel.biz;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.lxl.travel.activity.AltradionsDetialActivity;
import com.lxl.travel.activity.TravelAltradionsActivity;
import com.thinkland.sdk.android.DataCallBack;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

public class CmTravelDetailBiz {  //这个BIZ为景点的详细信息

	private Context context;
	private int TravelID;
	
	public CmTravelDetailBiz(Context context,int TravelID){
		this.context = context;
		this.TravelID = TravelID ;
	}
	public void GetTravelDetail(){
		Parameters params = new Parameters();
		params.add("scenicspotid", TravelID);

		final AltradionsDetialActivity t = (AltradionsDetialActivity)context;
		
		JuheData.executeWithAPI(context,127, 
				"http://api2.juheapi.com/xiecheng/senicspot/ticket/info",
				JuheData.POST, params, new DataCallBack() {
					
					@Override
					public void onSuccess(int statusCode, String responseString) {
						String TravelDetail = responseString;
						//Log.i("responseString = ", TravelDetail);
						t.test(responseString);
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
}
