//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : EasyToTravel
//  @ File Name : RequestDataBiz.java
//  @ Date : 2015/10/12
//  @ Author : 
//
//



package com.lxl.travel.biz;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;
import com.lxl.travel.adapter.BusAdapter;
import com.lxl.travel.adapter.FlightAdapter;
import com.lxl.travel.adapter.TrainAdapter;
import com.lxl.travel.entity.BusEntity;
import com.lxl.travel.entity.FlightEntity;
import com.lxl.travel.entity.TrainEntity;
import com.lxl.travel.utils.JsonParser;
import com.thinkland.sdk.android.DataCallBack;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

public class RequestDataBiz {
	private Context context;
	private TextView textView;
	private ListView listView;
	public List<FlightEntity> flights;
	public List<TrainEntity> trains;
	public ArrayList<String> citys;
	protected List<BusEntity> buses;
	public AutoCompleteTextView startTv, endTv;
	public RequestDataBiz (Context context, ListView listView, AutoCompleteTextView tv1,
			AutoCompleteTextView tv2, TextView textView){
		this.context = context;
		this.listView = listView;
		this.textView = textView;
		startTv = tv1;
		endTv = tv2;
	}
	public JSONArray ary;
	public void loadData(){
		/**
		 * 请不要添加key参数.
		 */
		Parameters params = new Parameters();
		params.add("start", startTv.getText().toString());
		params.add("end", endTv.getText().toString());
		params.add("date", textView.getText().toString());
		params.add("dtype", "json");
		/**
		 * 请求的方法 参数: 第一个参数 当前请求的context 第二个参数 接口id 第三二个参数 接口请求的url 第四个参数 接口请求的方式
		 * 第五个参数 接口请求的参数,键值对com.thinkland.sdk.android.Parameters类型; 第六个参数
		 * 请求的回调方法,com.thinkland.sdk.android.DataCallBack;
		 * 
		 */
		JuheData.executeWithAPI(context, 20,"http://apis.juhe.cn/plan/bc",JuheData.GET, params, new DataCallBack() {
		/**
		 * 请求成功时调用的方法 statusCode为http状态码,responseString请求返回数据.
		 */
		@Override
		public void onSuccess(int statusCode, String responseString) {
			try {
				flights = new ArrayList<FlightEntity>();
				JSONObject obj = new JSONObject(responseString);
				ary = obj.getJSONArray("result");
				flights = JsonParser.parseFlight(ary);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			listView.setAdapter(new FlightAdapter(context, flights));	
		}

		/**
		 * 请求完成时调用的方法,无论成功或者失败都会调用.
		 */
		@Override
		public void onFinish() {
		}
		/**
		 * 请求失败时调用的方法,statusCode为http状态码,throwable为捕获到的异常
		* statusCode:30002 没有检测到当前网络. 30003 没有进行初始化. 0
		* 未明异常,具体查看Throwable信息. 其他异常请参照http状态码.
		*/
		@Override
		public void onFailure(int statusCode,String responseString, Throwable throwable) {
			Log.i("info", "string:"+responseString);
			Log.i("info", "throwable:"+throwable);
			}
		});
	}
	public void loadTrainData(){
		/**
		 * 请不要添加key参数.该数据的获得并不需要时间
		 */
		Parameters params = new Parameters();
		params.add("start", startTv.getText().toString());
		params.add("end", endTv.getText().toString());
		params.add("dtype", "json");
		
		JuheData.executeWithAPI(context, 22,"http://apis.juhe.cn/train/s2swithprice",JuheData.GET, params, new DataCallBack() {
			
			@Override
			public void onSuccess(int statusCode, String responseString) {
				try {
					trains = new ArrayList<TrainEntity>();
					JSONObject obj = new JSONObject(responseString);
					ary = obj.getJSONObject("result").getJSONArray("list");
					trains = JsonParser.parseTrainList(ary);
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				listView.setAdapter(new TrainAdapter(context, trains));
			}
			
			/**
			 * 请求完成时调用的方法,无论成功或者失败都会调用.
			 */
			@Override
			public void onFinish() {
			}
			/**
			 * 请求失败时调用的方法,statusCode为http状态码,throwable为捕获到的异常
			 * statusCode:30002 没有检测到当前网络. 30003 没有进行初始化. 0
			 * 未明异常,具体查看Throwable信息. 其他异常请参照http状态码.
			 */
			@Override
			public void onFailure(int statusCode,String responseString, Throwable throwable) {
				Log.i("info", "string:"+responseString);
				Log.i("info", "throwable:"+throwable);
			}
		});
	}
	public void loadCity(){
		JuheData.executeWithAPI(context, 20,"http://apis.juhe.cn/plan/city",JuheData.GET, null, new DataCallBack() {
			/**
			 * 请求成功时调用的方法 statusCode为http状态码,responseString请求返回数据.
			 */
			@Override
			public void onSuccess(int statusCode, String responseString) {
				try {
					citys = new ArrayList<String>();
					JSONObject obj = new JSONObject(responseString);
					ary = obj.getJSONArray("result");
					citys = JsonParser.parseCity(ary);
					
					Intent intent = new Intent("com.travel.loadCity");
					intent.putExtra("city", citys);
					context.sendBroadcast(intent);
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			/**
			 * 请求完成时调用的方法,无论成功或者失败都会调用.
			 */
			@Override
			public void onFinish() {
			}
			/**
			 * 请求失败时调用的方法,statusCode为http状态码,throwable为捕获到的异常
			* statusCode:30002 没有检测到当前网络. 30003 没有进行初始化. 0
			* 未明异常,具体查看Throwable信息. 其他异常请参照http状态码.
			*/
			@Override
			public void onFailure(int statusCode,String responseString, Throwable throwable) {
				Log.i("info", "string:"+responseString);
				Log.i("info", "throwable:"+throwable);
				}
			});
	}
	public void loadBusData() {
		Parameters params = new Parameters();
		params.add("from", startTv.getText().toString());
		params.add("to", endTv.getText().toString());
		params.add("dtype", "json");
		
		JuheData.executeWithAPI(context, 82,"http://op.juhe.cn/onebox/bus/query_ab",JuheData.GET, params, new DataCallBack() {
			
			@Override
			public void onSuccess(int statusCode, String responseString) {
				try {
					buses = new ArrayList<BusEntity>();
					JSONObject obj = new JSONObject(responseString);
					ary = obj.getJSONObject("result").getJSONArray("list");
					buses = JsonParser.parseBusList(ary);
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				listView.setAdapter(new BusAdapter(context, buses));
			}
			
			/**
			 * 请求完成时调用的方法,无论成功或者失败都会调用.
			 */
			@Override
			public void onFinish() {
				
			}
			/**
			 * 请求失败时调用的方法,statusCode为http状态码,throwable为捕获到的异常
			 * statusCode:30002 没有检测到当前网络. 30003 没有进行初始化. 0
			 * 未明异常,具体查看Throwable信息. 其他异常请参照http状态码.
			 */
			@Override
			public void onFailure(int statusCode,String responseString, Throwable throwable) {
				Log.i("info", "string:"+responseString);
				Log.i("info", "throwable:"+throwable);
			}
		});
	}

}
