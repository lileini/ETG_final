//
//
 //  Generated by StarUML(tm) Java Add-In
//
//  @ Project : EasyToTravel
//  @ File Name : HomePageActivity.java
//  @ Date : 2015/10/12
//  @ Author : 
//
//
/**
 * @author �׾�
 * */

package com.lxl.travel.activity;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lxl.travel.ETGApplication;
import com.lxl.travel.activity.DragLayout.DragListener;
import com.lxl.travel.activityanim.ActivityChangeAnim;
import com.lxl.travel.biz.RequestGPSBiz;
import com.lxl.travel.biz.RequestWeatherFromBdiBiz;
import com.lxl.travel.entity.CurrentWeather;
import com.lxl.travel.entity.FurtherWeather;
import com.lxl.travel.entity.WeatherEntity;
import com.lxl.travel.fragment.HomePageFragment;
import com.lxl.travel.fragment.PersonalFragment;
import com.lxl.travel.fragment.TravelPlanFragment;
import com.lxl.travel.utils.CameraForImageUtil;
import com.lxl.travel.utils.Const;
import com.lxl.travel.utils.ImageCompress;
import com.lxl.travel.utils.SharedPreferencesUtil;
import com.lxl.travel.utils.ImageCompress.CompressOptions;
import com.lxl.travel.utils.LogUtil;
import com.lxl.travel.utils.Tools;
import com.lxl.trivel.R;

public class HomePageActivity extends FragmentActivity {

	private static final long serialVersionUID = 1L;
	public Button btn_city;
	public ViewPager viewPager;
	private RadioButton top_radioB;
	private RadioButton plan_radioB;
	private RadioButton mine_radioB;
	private MyWeatherDataReceiver receiver;
	private RadioGroup radioGroup;
	private Button searchCity_Bt;
	private TextView weatherToday;
	private TextView weatherTomorrow;
	private TextView weatherDayAfterTomorrow;
	private TextView tomorrow_Tv;
	private TextView dayAfterTomorrow_Tv;
	private TextView updateTimeTv;
	private ProgressBar progressBar;
	private Location location;
	private ImageView weatherImage1;
	private ImageView weatherImage2;
	private ImageView weatherImage3;

	Fragment[] fragmentArray = new Fragment[3];
	RadioButton[] btnArray = new RadioButton[3];
	/** 当前的fragment编号 */
	int currentFragmentIndex = 0;
	/** 要选择的fragment的编号 */
	int selectedFragmentIndex;
	HomePageFragment homePageFragment;
	TravelPlanFragment planFragment;
	PersonalFragment personalFragment;
	private PullToRefreshListView lv;
	private TextView tv_homepage_nickname;
	private ImageView iv_homepage_header;
	// 侧滑
	private DragLayout dl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homepage);
		getActionBar().hide();
		setViews();
		setListener();
		// 注册广播接收器
		receiver = new MyWeatherDataReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Const.RECEIVE_WEATHER_DATA);
		filter.addAction(Const.ACTION_LOCATION_CHANGED);
		registerReceiver(receiver, filter);
		// 侧滑
		initDragLayout();
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				location = new RequestGPSBiz(HomePageActivity.this)
						.requestGPS();
			}
		}, 1000);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		if (ETGApplication.userEntity != null) {
			tv_homepage_nickname.setText(ETGApplication.userEntity
					.getNickname());
			File file = CameraForImageUtil
					.getOutputMediaFile(ETGApplication.userEntity.getUsername());
			if (file.exists()) {
				ImageCompress.CompressOptions options = new CompressOptions();
				options.uri = Uri.fromFile(file);
				options.maxHeight = 78;
				options.maxWidth = 78;

				ImageCompress imageCompress = new ImageCompress();
				Bitmap bitmap = imageCompress.compressFromUri(
						HomePageActivity.this, options);
				/*
				 * bitmap = BitmapUtil.loadBitmap(file.getAbsolutePath(), 48,
				 * 48);
				 */
				iv_homepage_header.setImageBitmap(bitmap);
			}else {
				iv_homepage_header.setImageResource(R.drawable.icon_account);
			}
		}
	}

	private void initDragLayout() {
		dl = (DragLayout) findViewById(R.id.dl);
		dl.setDragListener(new DragListener() {
			@Override
			public void onOpen() {
				LogUtil.i("initDragLayout", "onOpen()");
			}

			@Override
			public void onClose() {
				LogUtil.i("initDragLayout", "onClose()");
			}

			@Override
			public void onDrag(float percent) {
				// ViewHelper.setAlpha(iv_icon, 1 - percent);
				LogUtil.i("initDragLayout", "onDrag:percent=" + percent);
				// 设透明度
				// ViewHelper.setAlpha(iv_icon, 1 - percent);
			}
		});

		lv = (PullToRefreshListView) findViewById(R.id.lv);
		lv.setAdapter(new ArrayAdapter<String>(HomePageActivity.this,
				R.layout.item_text, new String[] { "开通会员", "旅行日记", "旅游闲聊",
						"软件升级", "关于我们", "更改密码","更换账号", "退出账号" }));
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// Toast

				switch (position) {
				case 1:
					if (ETGApplication.instance != null) {
						t(getApplicationContext(), "亲，会员功能测试中~\n您已享有全部会员功能 ");
					} else {
						t(getApplicationContext(), "亲，您还没有登录! ");
					}
					break;

				case 2:
					if (ETGApplication.userEntity != null) {
						Intent intent = new Intent(HomePageActivity.this,
								NotePadListActivity.class);
						startActivity(intent);
					} else {
						Toast.makeText(HomePageActivity.this, "请先登录，再使用日志功能",
								Toast.LENGTH_SHORT).show();
					}
					break;
				case 3:
					t(getApplicationContext(), "亲，程序员哥哥正在努力开发中!");
					break;
				case 4:
					Tools.showProgressDialog(HomePageActivity.this,
							"检查中，请稍后...");
					Timer timer = new Timer();
					timer.schedule(new TimerTask() {

						@Override
						public void run() {
							runOnUiThread(new Runnable() {
								public void run() {
									Tools.closeProgressDialog();
									t(HomePageActivity.this, "已经是最新版本了");
								}
							});
						}
					}, 3000);
					break;
				case 5:
					Intent intent2 = new Intent(HomePageActivity.this,
							AboutUSActivity.class);
					startActivity(intent2);
					break;
				case 6:
					t(HomePageActivity.this, "修改密码服务已关闭！\n请速速与管理员联系！");
					break;
				case 7:
					Intent intent3= new Intent(HomePageActivity.this,
							LoginActivity.class);
					startActivity(intent3);
					break;
				case 8:

					if (ETGApplication.userEntity == null) {
						t(HomePageActivity.this, "未登录");
						return;
					}
					AlertDialog.Builder builder = new AlertDialog.Builder(
							HomePageActivity.this);
					builder.setTitle("退出当前账号");
					builder.setPositiveButton("确定", positiveListener);
					builder.setNegativeButton("取消", negativeListener);
					builder.setCancelable(false);
					builder.show();

					break;
				}
			}
		});

	}

	private android.content.DialogInterface.OnClickListener positiveListener = new android.content.DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			String[] key = new String[] { "username", "md5password" };
			Map<String, String> map = SharedPreferencesUtil.getPerferences(
					HomePageActivity.this, "userInfo", key);
			try {
				String userName = map.get(key[0]);
				String md5Psw = map.get(key[1]);
				// 删除密码
				md5Psw = null;
				// 重新保存密码
				List<String> data = new ArrayList<String>();
				data.add(userName);
				data.add(md5Psw);
				SharedPreferencesUtil.saveShared(HomePageActivity.this,
						"userInfo", key, data);
				// 重置全局userEntity为空
				ETGApplication.userEntity = null;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// 循环遍历销毁所有的Acyivity
				// ??????????????
				HomePageActivity.this.finish();
				System.exit(0);
			}

		}
	};

	private android.content.DialogInterface.OnClickListener negativeListener = new android.content.DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
		}
	};

	public static void t(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		// 为设置gps界面返回时
		if (arg0 == 101 && location != null) {
			requestWeatherData(location.getLongitude(), location.getLatitude());
		} else {
			Toast.makeText(this, "定位中，请稍后", Toast.LENGTH_SHORT).show();
			new RequestGPSBiz(HomePageActivity.this).requestGPS();
		}
	}

	@Override
	protected void onDestroy() {
		// 解除广播接收器
		unregisterReceiver(receiver);
		receiver = null;
		super.onDestroy();
	}

	/** 设置监听 */
	private void setListener() {

		// 设置点击不同选择按钮时的监听
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.homeP_rB:
					// 添加首页fragment
					selectedFragmentIndex = 0;
					break;
				case R.id.plan_rB:
					// 添加计划fragment
					selectedFragmentIndex = 1;
					break;
				case R.id.myP_rB:
					// 添加我的fragment
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
						transaction.add(R.id.linlaot_homepage_content,
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

		// 设置定位按钮的点击监听
		searchCity_Bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 隐藏城市显示view
				searchCity_Bt.setVisibility(View.INVISIBLE);
				// 显示进度条
				progressBar.setVisibility(View.VISIBLE);
				if (location != null) {
					requestWeatherData(location.getLongitude(),
							location.getLatitude());
				} else {
					Toast.makeText(HomePageActivity.this, "定位中，请稍后",
							Toast.LENGTH_SHORT).show();
					location = new RequestGPSBiz(HomePageActivity.this)
							.requestGPS();
				}
			}
		});
	}

	public void requestWeatherData(double lon, double lat) {

		searchCity_Bt.setVisibility(View.INVISIBLE);
		// 显示进度条
		progressBar.setVisibility(View.VISIBLE);

		RequestParams params = setParams(lon, lat);
		// 通过广播接收返回的数据
		RequestWeatherFromBdiBiz.getWeatherData(HomePageActivity.this, 39,
				"http://apis.baidu.com/showapi_open_bus/weather_showapi/point",
				params);
	}

	/**
	 * 设置向服务器端获取天气信息的请求参数
	 * 
	 * @param location
	 */
	private RequestParams setParams(double lon, double lat) {
		/**
		 * 请不要添加key参数.
		 */
		RequestParams params = new RequestParams();
		params.addHeader("apikey", "" + "6a64de48c734680156fbd141f95425ac");
		params.addQueryStringParameter("lng", "" + lon);
		params.addQueryStringParameter("lat", "" + lat);
		params.addQueryStringParameter("from", "5");
		params.addQueryStringParameter("needMoreDay", "0");
		params.addQueryStringParameter("needIndex", "0");
		return params;
	}

	/** 封装主页三个页面的fragment集合 */
	// private List<Fragment> fragments = new ArrayList<Fragment>();

	// private TextView today_Tv;

	/** 初始化控件 */
	private void setViews() {
		top_radioB = (RadioButton) findViewById(R.id.homeP_rB);
		plan_radioB = (RadioButton) findViewById(R.id.plan_rB);
		mine_radioB = (RadioButton) findViewById(R.id.myP_rB);
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
		searchCity_Bt = (Button) findViewById(R.id.searchCity_Bt);
		// 天气设置控件
		weatherToday = (TextView) findViewById(R.id.weatherDay1_Tv);
		weatherTomorrow = (TextView) findViewById(R.id.weatherDay2_Tv);
		weatherDayAfterTomorrow = (TextView) findViewById(R.id.weatherDay3_Tv);
		tomorrow_Tv = (TextView) findViewById(R.id.Day2_Tv);
		dayAfterTomorrow_Tv = (TextView) findViewById(R.id.Day3_Tv);
		updateTimeTv = (TextView) findViewById(R.id.updateTime_TV);
		weatherImage1 = (ImageView) findViewById(R.id.home_weather_imageView1);
		weatherImage2 = (ImageView) findViewById(R.id.home_weather_imageView2);
		weatherImage3 = (ImageView) findViewById(R.id.home_weather_imageView3);
		// today_Tv = (TextView) findViewById(R.id.Day1_Tv);
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		tv_homepage_nickname = (TextView) findViewById(R.id.tv_homepage_nickname);
		iv_homepage_header = (ImageView) findViewById(R.id.iv_homepage_header);

		homePageFragment = new HomePageFragment();
		planFragment = new TravelPlanFragment();
		personalFragment = new PersonalFragment();

		fragmentArray[0] = homePageFragment;
		fragmentArray[1] = planFragment;
		fragmentArray[2] = personalFragment;

		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		// 把fragment添加到transaction
		transaction.add(R.id.linlaot_homepage_content, homePageFragment);
		// 显示
		transaction.show(homePageFragment);
		// 提交
		transaction.commit();
		btnArray[0] = (RadioButton) findViewById(R.id.homeP_rB);
		btnArray[1] = (RadioButton) findViewById(R.id.plan_rB);
		btnArray[2] = (RadioButton) findViewById(R.id.myP_rB);
		// 设置当前选中butten
		btnArray[currentFragmentIndex].setChecked(true);

	}

	/** 设置标题栏天气状况的显示 */
	public void setWeatherInfo(WeatherEntity weatherEntity) {
		BitmapUtils bitmapUtils = new BitmapUtils(HomePageActivity.this);
		// 更改当前城市
		searchCity_Bt.setText(weatherEntity.getCity());
		// 设置今天天气信息
		CurrentWeather currentWeather = weatherEntity.getCurrentWeather();
		String infoToday = "温度 : " + currentWeather.getTemp() + "℃\n" + "湿度 : "
				+ currentWeather.getHumidity();
		weatherToday.setText(infoToday);
		// 设置图片
		bitmapUtils.display(weatherImage1, currentWeather.getWindStrength());
		// 设置更新时间
		updateTimeTv.setText(currentWeather.getUpdateTime() + "更新");
		// 设置明天天气信息
		FurtherWeather tomorrowWeather = weatherEntity.getTomorrowWeather();
		String infoTomorrowWeather = "温度 : " + tomorrowWeather.getTemp()
				+ "℃\n" + tomorrowWeather.getWeather();
		bitmapUtils.display(weatherImage2, tomorrowWeather.getWind());
		weatherTomorrow.setText(infoTomorrowWeather);
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

	/** 广播接收类,接收发送请求之后返回的信息 */
	class MyWeatherDataReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (Const.ACTION_LOCATION_CHANGED.equals(action)) {
				if (ETGApplication.location != null) {
					requestWeatherData(ETGApplication.location.getLongitude(),
							ETGApplication.location.getLatitude());
				} else {
					Toast.makeText(HomePageActivity.this, "定位中，请稍后",
							Toast.LENGTH_SHORT).show();
					location = new RequestGPSBiz(HomePageActivity.this)
							.requestGPS();

				}
			}
			if (Const.RECEIVE_WEATHER_DATA.equals(action)) {
				if (Const.WEATHER_DATA_REQUEST_SUCCESSED == intent.getIntExtra(
						Const.WEATHER_DATA_STATUSCODE, -1)) {// ��ȡ��ݳɹ�
					// 解析获取到的数据
					String respString = intent
							.getStringExtra(Const.WEATHER_DATA_RESPONSESTRING);
					try {
						WeatherEntity weatherEntity = RequestWeatherFromBdiBiz
								.parseWeatherData(respString);

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

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		// 设置退出动画
		ActivityChangeAnim.changeAnim(this, 0,
				ActivityChangeAnim.MOVE_FROM_TOP_TO_BOTTOM);
	}
}