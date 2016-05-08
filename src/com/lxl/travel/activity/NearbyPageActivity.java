package com.lxl.travel.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

import com.lxl.travel.base.BaseActivity;
import org.json.JSONException;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lxl.travel.ETGApplication;
import com.lxl.travel.biz.RequestGPSBiz;
import com.lxl.travel.biz.RequestWeatherFromBdiBiz;
import com.lxl.travel.entity.CurrentWeather;
import com.lxl.travel.entity.FurtherWeather;
import com.lxl.travel.entity.WeatherEntity;
import com.lxl.travel.fragment.NearbyCulinaryEntityFragment;
import com.lxl.travel.fragment.NearbyParkingFragment;
import com.lxl.travel.fragment.NearbyWIFIFragment;
import com.lxl.travel.utils.Const;
import com.lxl.trivel.R;

/** 附近页面 */
public class NearbyPageActivity extends BaseActivity {
	/** 附近美食Fragment */
	NearbyCulinaryEntityFragment culinaryfragment;
	/** 附近停车场Fragment */
	NearbyParkingFragment parkingFragment;
	/** 附近wifiFragment */
	NearbyWIFIFragment wifiFragment;

	Fragment[] fragmentArray = new Fragment[3];
	Button[] btnArray = new Button[3];
	/** 当前的fragment编号 */
	int currentFragmentIndex = 0;
	/** 要选择的fragment的编号 */
	int selectedFragmentIndex;
	// 天气控件
	private Button searchCity_Bt;
	private TextView weatherToday;
	private TextView weatherTomorrow;
	private TextView weatherDayAfterTomorrow;
	private TextView tomorrow_Tv;
	private TextView dayAfterTomorrow_Tv;
	private TextView updateTimeTv;
	private ProgressBar progressBar;
	private ImageView weatherImage1;
	private ImageView weatherImage2;
	private ImageView weatherImage3;

	private LocationChangedReceiver receiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nearby);
		getActionBar().hide();
		setupView();
		addListener();
		receiver = new LocationChangedReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Const.ACTION_LOCATION_CHANGED);
		filter.addAction(Const.RECEIVE_WEATHER_DATA);
		registerReceiver(receiver, filter);
		//先判断ETGApplication中是否已经存在天气实体，
		if (ETGApplication.weatherEntity != null) {
			//有就直接设置数据
			setWeatherInfo(ETGApplication.weatherEntity);
		} else {
			//没有就延迟查看，若还是为空则请求GPS获取位置，获取数据
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					if (ETGApplication.weatherEntity != null) {
						//有就直接设置数据
						setWeatherInfo(ETGApplication.weatherEntity);
					}else {
						new RequestGPSBiz(NearbyPageActivity.this).requestGPS();
					}
				}
			}, 2000);
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(receiver);
		super.onDestroy();
	}

	private void setupView() {
		wifiFragment = new NearbyWIFIFragment();
		culinaryfragment = new NearbyCulinaryEntityFragment();
		parkingFragment = new NearbyParkingFragment();

		fragmentArray[0] = culinaryfragment;
		fragmentArray[1] = wifiFragment;
		fragmentArray[2] = parkingFragment;

		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		// 把fragment添加到transaction
		transaction.add(R.id.linlaot_nearby_content, culinaryfragment);
		// 显示
		transaction.show(culinaryfragment);
		// 提交
		transaction.commit();

		btnArray[0] = (Button) findViewById(R.id.btn_nearby_choose_culinary);
		btnArray[1] = (Button) findViewById(R.id.btn_nearby_choose_wifi);
		btnArray[2] = (Button) findViewById(R.id.btn_nearby_choose_parking);
		// 设置当前选中butten
		btnArray[currentFragmentIndex].setSelected(true);

		searchCity_Bt = (Button) findViewById(R.id.searchCity_Bt_nearby);

		// 天气设置控件
		weatherToday = (TextView) findViewById(R.id.weatherDay1_Tv_nearby);
		weatherTomorrow = (TextView) findViewById(R.id.weatherDay2_Tv_nearby);
		weatherDayAfterTomorrow = (TextView) findViewById(R.id.weatherDay3_Tv_nearby);
		tomorrow_Tv = (TextView) findViewById(R.id.Day2_Tv_nearby);
		dayAfterTomorrow_Tv = (TextView) findViewById(R.id.Day3_Tv_nearby);
		updateTimeTv = (TextView) findViewById(R.id.updateTime_TV_nearby);
		weatherImage1 = (ImageView) findViewById(R.id.imageView1_nearby);
		weatherImage2 = (ImageView) findViewById(R.id.ImageView02_nearby);
		weatherImage3 = (ImageView) findViewById(R.id.ImageView03_nearby);
		// today_Tv = (TextView) findViewById(R.id.Day1_Tv);
		progressBar = (ProgressBar) findViewById(R.id.progressBar1_nearby);

	}

	/** 设置标题栏天气状况的显示 */
	public void setWeatherInfo(WeatherEntity weatherEntity) {
		BitmapUtils bitmapUtils = new BitmapUtils(NearbyPageActivity.this);
		// 更改当前城市
		searchCity_Bt.setText(weatherEntity.getCity());
		// 设置今天天气信息
		CurrentWeather currentWeather = weatherEntity.getCurrentWeather();
		String infoToday = "温度 : " + currentWeather.getTemp() + "℃\n" + "湿度 : "
				+ currentWeather.getHumidity();
		weatherToday.setText(infoToday);
		bitmapUtils.display(weatherImage1, currentWeather.getWindStrength());
		// 设置更新时间
		updateTimeTv.setText(currentWeather.getUpdateTime() + "更新");
		// 设置明天天气信息
		FurtherWeather tomorrowWeather = weatherEntity.getTomorrowWeather();
		String infoTomorrowWeather = "温度 : " + tomorrowWeather.getTemp()
				+ "℃\n" + tomorrowWeather.getWeather();
		weatherTomorrow.setText(infoTomorrowWeather);
		bitmapUtils.display(weatherImage2, tomorrowWeather.getWind());
		// 设置后天天气信息
		FurtherWeather dayAfterTomorrow = weatherEntity
				.getDayAftertomorrowWeather();
		String infoDayAfterTomorrow = "温度 : " + dayAfterTomorrow.getTemp()
				+ "℃\n" + dayAfterTomorrow.getWeather();
		weatherDayAfterTomorrow.setText(infoDayAfterTomorrow);
		bitmapUtils.display(weatherImage3, dayAfterTomorrow.getWind());

		try {
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyyMMdd");
			// 设置明天日期
			String Tdate = dateFormat1.format(dateFormat2.parse(tomorrowWeather
					.getDate()));
			tomorrow_Tv.setText(Tdate);
			// 设置后天日期
			String AfterTdate = dateFormat1.format(dateFormat2
					.parse(dayAfterTomorrow.getDate()));
			dayAfterTomorrow_Tv.setText(AfterTdate);
		} catch (ParseException e) {
			Toast.makeText(this, "更新日期失败", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}

	}

	private void addListener() {

		// TODO Auto-generated method stub
		for (Button button : btnArray) {
			button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					switch (v.getId()) {
					case R.id.btn_nearby_choose_culinary:
						selectedFragmentIndex = 0;
						break;
					case R.id.btn_nearby_choose_wifi:
						selectedFragmentIndex = 1;
						break;
					case R.id.btn_nearby_choose_parking:
						selectedFragmentIndex = 2;
						break;

					}
					// 判断单击的button是不是当前的
					if (selectedFragmentIndex != currentFragmentIndex) {
						// 显示被选中fragment
						Fragment showFragment = fragmentArray[selectedFragmentIndex];
						// 判断以前有没有add过
						FragmentTransaction transaction = getSupportFragmentManager()
								.beginTransaction();
						if (showFragment.isAdded() == false) {// 还没添加到transaction中
							transaction.add(R.id.linlaot_nearby_content,
									showFragment);
						}
						// 隐藏当前fragment
						transaction.hide(fragmentArray[currentFragmentIndex]);
						// 显示
						transaction.show(showFragment);
						// 提交
						transaction.commit();
						// 设置被选中的butten的select状态为true
						btnArray[selectedFragmentIndex].setSelected(true);
						btnArray[currentFragmentIndex].setSelected(false);
						// 改变当前选中的index编号
						currentFragmentIndex = selectedFragmentIndex;

					}
				}
			});
		}

		// 设置定位按钮的点击监听
		searchCity_Bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 隐藏城市显示view
				searchCity_Bt.setVisibility(View.INVISIBLE);
				// 显示进度条
				progressBar.setVisibility(View.VISIBLE);
				if (ETGApplication.location != null) {
					requestWeatherData(ETGApplication.location.getLongitude(),
							ETGApplication.location.getLatitude());
				} else {
					Toast.makeText(NearbyPageActivity.this, "定位中，请稍后",
							Toast.LENGTH_SHORT).show();
					ETGApplication.location = new RequestGPSBiz(
							NearbyPageActivity.this).requestGPS();
				}
			}
		});
	}

	/**
	 * 请求天气数据
	 * 
	 * @param lon
	 *            lat 经纬度
	 * */
	public void requestWeatherData(double lon, double lat) {

		searchCity_Bt.setVisibility(View.INVISIBLE);
		// 设置请求参数
		RequestParams params = setParams(lon, lat);
		// 通过广播接收返回的数据
		RequestWeatherFromBdiBiz.getWeatherData(NearbyPageActivity.this, 39,
				"http://apis.baidu.com/showapi_open_bus/weather_showapi/point",
				params);

	}

	/** 设置向服务器端获取天气信息的请求参数 */
	private RequestParams setParams(double lon, double lat) {
		RequestParams params = new RequestParams();
		params.addHeader("apikey", "" + "6a64de48c734680156fbd141f95425ac");
		params.addQueryStringParameter("lng", "" + lon);
		params.addQueryStringParameter("lat", "" + lat);
		params.addQueryStringParameter("from", "5");
		params.addQueryStringParameter("needMoreDay", "0");
		params.addQueryStringParameter("needIndex", "0");
		return params;

	}

	class LocationChangedReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (Const.ACTION_LOCATION_CHANGED.equals(intent.getAction())) {
				if (ETGApplication.location != null){
					requestWeatherData(ETGApplication.location.getLongitude(),
							ETGApplication.location.getLatitude());
				}
			}
			if (Const.RECEIVE_WEATHER_DATA.equals(intent.getAction())) {
				if (Const.WEATHER_DATA_REQUEST_SUCCESSED == intent.getIntExtra(
						Const.WEATHER_DATA_STATUSCODE, -1)) {// ��ȡ��ݳɹ�
					// 解析获取到的数据
					String respString = intent
							.getStringExtra(Const.WEATHER_DATA_RESPONSESTRING);
					try {
						WeatherEntity weatherEntity = RequestWeatherFromBdiBiz
								.parseWeatherData(respString);
						// 将当前查询到的天气信息对象,设为app全局
						ETGApplication.weatherEntity = weatherEntity;
						try {
							setWeatherInfo(weatherEntity);
						} catch (Exception e) {
							// Toast.makeText(HomePageActivity.this, "更新天气信息失败",
							// Toast.LENGTH_SHORT);
						}
					} catch (JSONException e) {
						// Toast.makeText(HomePageActivity.this, "加载数据失败",
						// Toast.LENGTH_LONG).show();
						e.printStackTrace();
					}
					// Toast.makeText(HomePageActivity.this, "加载数据成功",
					// Toast.LENGTH_LONG).show();
					// 设置显示进度条
					progressBar.setVisibility(View.GONE);
					searchCity_Bt.setVisibility(View.VISIBLE);
				} else {// 获取数据失败
					String throwable = intent
							.getStringExtra(Const.WEATHER_DATA_THROWABLE);
					Log.i("info", "throwable :" + throwable);
					// Toast.makeText(HomePageActivity.this,
					// "连接服务器失败: "+throwable, Toast.LENGTH_LONG).show();
				}
			}
		}

	}

}