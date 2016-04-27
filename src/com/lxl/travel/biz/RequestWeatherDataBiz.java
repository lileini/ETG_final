package com.lxl.travel.biz;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.lxl.travel.entity.CurrentWeather;
import com.lxl.travel.entity.FurtherWeather;
import com.lxl.travel.entity.WeatherEntity;
import com.lxl.travel.utils.Const;
import com.lxl.travel.utils.LogUtil;
import com.thinkland.sdk.android.DataCallBack;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

/**通过此类获取天气数据,解析天气数据进行封装*/
public class RequestWeatherDataBiz {
	public static Context context;

	/**发送请求获取天气数据*/
	public static void getWeatherData(Context mContext, int port, String url,Parameters params){
		context = mContext;
		/**
		 * 请不要添加key参数.
		 *//*
		Parameters params = new Parameters();
		params.add("ip", "www.juhe.cn");
		params.add("dtype", "xml");*/
		/**
		 * 请求的方法 参数: 第一个参数 当前请求的context 第二个参数 接口id 第三二个参数 接口请求的url 第四个参数 接口请求的方式
		 * 第五个参数 接口请求的参数,键值对com.thinkland.sdk.android.Parameters类型; 第六个参数
		 * 请求的回调方法,com.thinkland.sdk.android.DataCallBack;
		 * 
		 */
		JuheData.executeWithAPI(mContext, port,url,JuheData.GET, params, new DataCallBack() {
			/**
			 * 请求成功时调用的方法 statusCode为http状态码,responseString    *为请求返回数据.
			 */
			@Override
			public void onSuccess(int statusCode, String responseString) {
				//将结果以广播形式发送
				sendBroadcast(statusCode,responseString,null);
			}

			/**
			 * 请求完成时调用的方法,无论成功或者失败都会调用.
			 */
			@Override
			public void onFinish() {
				//sendBroadcast(Const.WEATHER_DATA_REQUEST_FINISH,null,null);
			}
			/**
			 * 请求失败时调用的方法,statusCode为http状态码,throwable为捕获到的异常
			 * statusCode:30002 没有检测到当前网络. 30003 没有进行初始化. 0
			 * 未明异常,具体查看Throwable信息. 其他异常请参照http状态码.
			 */
			@Override
			public void onFailure(int statusCode,String responseString, Throwable throwable) {
				sendBroadcast(statusCode,responseString,throwable);
			}
		});
	}

	/**借助此方法进行广播发送*/
	protected static void sendBroadcast(int statusCode, 
			String responseString,Throwable throwable) {
		LogUtil.i("weather", ""+statusCode + "," + responseString);
		Intent intent = new Intent();
		intent.putExtra(Const.WEATHER_DATA_STATUSCODE, statusCode);
		intent.putExtra(Const.WEATHER_DATA_RESPONSESTRING, responseString);
		intent.setAction(Const.RECEIVE_WEATHER_DATA);
		if(throwable != null){			
			intent.putExtra(Const.WEATHER_DATA_THROWABLE, throwable);
		}
		//发送广播
		context.sendBroadcast(intent);
	}

	/**借助此方法,进行数据的解析
	 * @throws JSONException */
	public static WeatherEntity parseWeatherData(String respString) throws JSONException {
		WeatherEntity weatherEntity = new WeatherEntity();
		//返回包含"resultcode","reason","result"的JSONObject
		JSONObject jsonObject = new JSONObject(respString);
		//返回包含"sk","today","future"字段的JSONObject
		JSONObject jsonObject2 = jsonObject.getJSONObject("result");
		//获取当前查询的城市进行封装
		weatherEntity.setCity(jsonObject2.getJSONObject( "today").getString("city"));
		//返回"sk"JSONObject
		JSONObject jsonObject3 = jsonObject2.getJSONObject("sk");		
		//封装当天天气
		CurrentWeather currentWeather = new CurrentWeather();
		currentWeather.setHumidity(jsonObject3.getString("humidity"));
		currentWeather.setTemp(jsonObject3.getString("temp"));
		currentWeather.setUpdateTime(jsonObject3.getString("time"));
		currentWeather.setWindDirection(jsonObject3.getString("wind_direction"));
		currentWeather.setWindStrength(jsonObject3.getString("wind_strength"));
		weatherEntity.setCurrentWeather(currentWeather);
		//封装未来天气,jsonObject4是一个包含未来几天天气的jsonObject
		JSONObject jsonObject4 = jsonObject2.getJSONObject("future");
		//获取明天的日期,拼接为future的查询字段
		String dayOfTomorrow = "day_"+getDateOfTomorrow(1L);
		//封装明天的天气状况信息
		FurtherWeather tomorrowWeather = new FurtherWeather();
		tomorrowWeather.setDate(jsonObject4.getJSONObject(dayOfTomorrow).getString("date"));
		tomorrowWeather.setTemp(jsonObject4.getJSONObject(dayOfTomorrow).getString("temperature"));
		tomorrowWeather.setWeather(jsonObject4.getJSONObject(dayOfTomorrow).getString("weather"));
		tomorrowWeather.setWeek(jsonObject4.getJSONObject(dayOfTomorrow).getString("week"));
		tomorrowWeather.setWind(jsonObject4.getJSONObject(dayOfTomorrow).getString("wind"));
		weatherEntity.setTomorrowWeather(tomorrowWeather);
		//获取后天的日期,拼接为future的查询字段
		String dayOfAfterTomorrow = "day_"+getDateOfTomorrow(2L);
		FurtherWeather dayAftertomorrowWeather = new FurtherWeather();
		dayAftertomorrowWeather.setDate(jsonObject4.getJSONObject(dayOfAfterTomorrow).getString("date"));
		dayAftertomorrowWeather.setTemp(jsonObject4.getJSONObject(dayOfAfterTomorrow).getString("temperature"));
		dayAftertomorrowWeather.setWeather(jsonObject4.getJSONObject(dayOfAfterTomorrow).getString("weather"));
		dayAftertomorrowWeather.setWeek(jsonObject4.getJSONObject(dayOfAfterTomorrow).getString("week"));
		dayAftertomorrowWeather.setWind(jsonObject4.getJSONObject(dayOfAfterTomorrow).getString("wind"));
		weatherEntity.setDayAftertomorrowWeather(dayAftertomorrowWeather);
		return weatherEntity;

	}

	/**根据当前系统日期,获取明天的时间日期*/
	private static String getDateOfTomorrow(long dayCount) {
		//指定日期格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyMMdd");
		//当前系统时间(毫秒)加上一天的时间(毫秒值86400000),再进行格式转化
		//避免超出直接+1天时,会发生的月天数超出正常范围
		String date = sdf.format(new Date(System.currentTimeMillis()+86400000*dayCount));
		return date;
	}
}
