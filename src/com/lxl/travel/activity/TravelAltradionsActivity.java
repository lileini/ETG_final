//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : EasyToTravel
//  @ File Name : TravelAltradionsActivity.java
//  @ Date : 2015/10/12
//  @ Author : 
//
//
/**
 * @author 程蒙
 * */


package com.lxl.travel.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.lxl.travel.base.BaseActivity;
import com.lxl.travel.entity.Net;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.BitmapUtils;
import com.lxl.travel.adapter.TravelAltradionsAdapter;
import com.lxl.travel.biz.CmTravelBiz;
import com.lxl.travel.biz.RequestWeatherDataBiz;
import com.lxl.travel.entity.AltradionsEntity;
import com.lxl.travel.utils.Const;
import com.lxl.travel.utils.JsonParser;
import com.lxl.trivel.R;
import com.thinkland.sdk.android.Parameters;
/** 	这个Activity为主界面点击旅游后跳转的界面，主要是为了显示旅游景点的一些信息。
 * 	其中包含1.根据输入的城市名字查询当地2.根据不同的条件排序显示3.点击item后跳转入详细页面
 * 4.点击的查询的同时查询天气的值，并更新入最上方的天气栏 */

public class TravelAltradionsActivity extends BaseActivity {
	public EditText search;  //点击的查询按钮
	public Button button1;   //三个button对应三种不同的查询排序方式
	public Button button2;
	public Button button3;
	private ImageView iv01;
	private ImageView iv02;
	private ImageView iv03;
	public PullToRefreshListView listview;
	public ArrayList<AltradionsEntity> data = new ArrayList<AltradionsEntity>(); //data盛放景点信息的实体类
	public ArrayList<String> weatherdata = new ArrayList<String>();
	public BitmapUtils bitmapUtils;
	//private ArrayList<String> sortway = new ArrayList<String>();
	private TravelAltradionsAdapter adapter;
	private MyWeatherDataReceiver receiver;
	private Intent intentfile;
	private CmTravelBiz cm;
	private ArrayList<AltradionsEntity> travelData; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.altradions_layout);
		getActionBar().hide();
		intentfile = getIntent();

		/*sortway.add("默认排序");
		sortway.add("价格排序");
		sortway.add("星级排序");
		sortway.add("评分排序");*/
		setviews();
		//String data = cm.GetTravelData();
		//Log.i("TravelData", "data = "+data);
		setlistener();
		//注册广播接收器
		receiver = new MyWeatherDataReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Const.RECEIVE_WEATHER_DATA);     //接收返回的天气信息
		registerReceiver(receiver, filter);
	}
	public void onClick(View view){
		switch (view.getId()) {
		case R.id.TravelAltradionsActivitybutton1:
			if(!data.isEmpty()){
				sortByPrice();}
			break;
		case R.id.TravelAltradionsActivitybutton2:
			if(!data.isEmpty()){
				sortByGrade();}

			break;
		case R.id.TravelAltradionsActivitybutton3:
			if(!data.isEmpty()){
				sortByStar();}

			break;
		default:
			break;
		}
	}

	public void doClick(View view){     //点击搜索时，调用旅游和天气的BIZ业务类。
		Log.i("TGA = ", "doClick");
		String name = search.getText().toString();
		cm = new CmTravelBiz(TravelAltradionsActivity.this,name);
		cm.GetTravelData();	
		//获取天气信息
		Parameters params = setParams();
		RequestWeatherDataBiz.getWeatherData(TravelAltradionsActivity.this, 39, Net.URL_WEATHER, params);
	}
	private Parameters setParams() {  //设置聚合需要的参数
		/*
		 * 请不要添加key参数.
		 */
		Parameters params = new Parameters();
		params.add("cityname", search.getText().toString());
		params.add("format", 2);
		params.add("dtype", "json");
		return params;
	}

	private void setlistener() {
		listview.setOnItemClickListener(new OnItemClickListener() { //设置点击事件，点击跳转详细界面

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(TravelAltradionsActivity.this,AltradionsDetialActivity.class);
				intent.putExtra("TravelDetail", data.get(position));
				intent.putExtra("timeFrom", intentfile.getStringExtra("fromDate"));
				intent.putExtra("timeto", intentfile.getStringExtra("toDate"));
				startActivity(intent);	
			}
		});
		listview.setOnRefreshListener(new OnRefreshListener2<ListView>() { //设置下拉刷新，加载新的10条数据进listview

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {


			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				if(cm != null){					
					cm.GetTravelDataAdd();
				}else {
					listview.onRefreshComplete();
				}

			}
		});
	}

	private void setviews() {
		search = (EditText)findViewById(R.id.TravelAltradionsActivityeditText);
		listview = (PullToRefreshListView)findViewById(R.id.TravelAltradionsActivitylistView1);
		listview.setMode(Mode.PULL_FROM_END);
		bitmapUtils = new BitmapUtils(this); 
		//TravelAltradionsSpinnerAdapter adapter = new TravelAltradionsSpinnerAdapter(this, sortway);
		button1 = (Button)findViewById(R.id.TravelAltradionsActivitybutton1);
		button2 = (Button)findViewById(R.id.TravelAltradionsActivitybutton2);
		button3 = (Button)findViewById(R.id.TravelAltradionsActivitybutton3);
		iv01 = (ImageView)findViewById(R.id.imageViewTravelWeatherToday);
		iv02 = (ImageView)findViewById(R.id.imageViewTravelWeatherToday2);
		iv03 = (ImageView)findViewById(R.id.imageViewTravelWeatherToday3);

	}

	public void test(String s,int num){ //由TRAVELBIZ回传回的数据  S，再把S（json字符串）解析为集合data
		if(adapter==null){			//	再把data数据传给adapter显示出来
			//Log.i("TravelData", "data = "+s);
			travelData = JsonParser.parser(s,num);
			data = travelData;
			Log.i("TAG_01", travelData.toString());
			adapter = new TravelAltradionsAdapter(TravelAltradionsActivity.this,data,bitmapUtils);
			listview.setAdapter(adapter);
		}else{
			travelData = JsonParser.parser(s,num);
			data = travelData;
			Log.i("TAG_02", travelData.toString());
			adapter.notifyDataSetChanged();
			listview.onRefreshComplete();
		}
	}

	public void sortByPrice() { //价格排序
		Collections.sort(data, new Comparator<AltradionsEntity>() {

			@Override
			public int compare(AltradionsEntity entity01, AltradionsEntity entity02) {
				return Integer.parseInt(entity01.getTravelPrice())-Integer.parseInt(entity02.getTravelPrice());
			}
		});
		adapter.notifyDataSetChanged();
	}

	public void sortByGrade() { //评价排序
		Collections.sort(data, new Comparator<AltradionsEntity>() {

			@Override
			public int compare(AltradionsEntity entity01, AltradionsEntity entity02) {
				int k = (int)(Double.parseDouble(entity02.getCommentGrade())*100-Double.parseDouble(entity01.getCommentGrade())*100);
				//Log.i("tag", k+"");
				return k;
			}
		});
		adapter.notifyDataSetChanged();
	}

	public void sortByStar() { //星级排序
		Collections.sort(data, new Comparator<AltradionsEntity>() {

			@Override
			public int compare(AltradionsEntity entity01, AltradionsEntity entity02) {
				return Integer.parseInt(entity02.getTravelStar())-Integer.parseInt(entity01.getTravelStar());
			}
		});
		adapter.notifyDataSetChanged();
	}


	public void updateWatcher() {

	}

	public void setListView() {

	}

	/**广播接收类,接收发送请求之后返回的信息*/
	class MyWeatherDataReceiver extends BroadcastReceiver{
		public ImageView t;
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if(Const.RECEIVE_WEATHER_DATA.equals(action)){
				if(Const.WEATHER_DATA_REQUEST_SUCCESSED == intent.getIntExtra(Const.WEATHER_DATA_STATUSCODE, -1)){//获取数据成功
					//解析获取到的数据
					String respString = intent.getStringExtra(Const.WEATHER_DATA_RESPONSESTRING);
					//变为天气集合
					PaserForWeatherJson(respString);

				}else{//获取数据失败
					String throwable = intent.getStringExtra(Const.WEATHER_DATA_THROWABLE);
					Log.i("info", "throwable :"+throwable);
					Toast.makeText(TravelAltradionsActivity.this, "连接服务器失败: "+throwable, Toast.LENGTH_LONG).show();
				}
			}
		}

		private void PaserForWeatherJson(String respString) {   //这个方法用于将天气信息匹配对应的图片（例：“雨”对应W03）
			try {
				JSONObject obj = new JSONObject(respString);
				JSONArray ary = obj.getJSONObject("result").getJSONArray("future");
				String today = ary.getJSONObject(0).getString("weather");
				weatherdata.add(today);
				String mingtian = ary.getJSONObject(1).getString("weather");
				weatherdata.add(mingtian);
				String houtian = ary.getJSONObject(2).getString("weather");
				weatherdata.add(houtian);
				for (int i = 0; i < weatherdata.size(); i++) {
					if(i == 0){t = iv01;}else if (i == 1) {	t = iv02;}else if (i == 2) {t = iv03;}
					String s = weatherdata.get(i);
					if(s.indexOf("雨")!=-1){
						t.setImageResource(R.drawable.w03);
					}else if(s.indexOf("阴")!=-1){
						t.setImageResource(R.drawable.w02);
					}else if(s.indexOf("多云")!=-1){
						t.setImageResource(R.drawable.w01);
					}else if(s.indexOf("晴")!=-1){
						t.setImageResource(R.drawable.w00);
					}
				}



			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);
	}
}



