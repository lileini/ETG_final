package com.lxl.travel.biz;

import java.sql.Date;
import java.text.SimpleDateFormat;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.OnFinished;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lxl.travel.ETGApplication;
import com.lxl.travel.entity.CurrentWeather;
import com.lxl.travel.entity.FurtherWeather;
import com.lxl.travel.entity.WeatherEntity;
import com.lxl.travel.utils.Const;
import com.lxl.travel.utils.LogUtil;


/**通过此类获取天气数据,解析天气数据进行封装*/
public class RequestWeatherFromBdiBiz {
	public static Context context;

	/**发送请求获取天气数据*/
	public static void getWeatherData(Context mContext, int port, String url,RequestParams params){
			context = mContext;
		
		HttpUtils httpUtils = new HttpUtils(10000);
		httpUtils.configCurrentHttpCacheExpiry(1000);
		LogUtil.i("getWeatherData", "params:" + params.getEntity() +";"+ params.getQueryStringParams());
		httpUtils.send(HttpMethod.GET, url, params, new RequestCallBack<String>() {
			
			@Override
			public void onFailure(HttpException throwable, String responseString) {
				LogUtil.i("getWeatherData", "onFailure");
				Intent intent = new Intent();
				intent.putExtra(Const.WEATHER_DATA_STATUSCODE, 404);
				intent.putExtra(Const.WEATHER_DATA_RESPONSESTRING, responseString);
				intent.setAction(Const.RECEIVE_WEATHER_DATA);
				if(throwable != null){			
					intent.putExtra(Const.WEATHER_DATA_THROWABLE, throwable);
				}
				//发送广播
				context.sendBroadcast(intent);
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				// TODO Auto-generated method stub
				sendBroadcast(responseInfo.statusCode,responseInfo.result,null);
			}
		});
//			@Override
//			public void onSuccess(int statusCode, String responseString) {
//				//将结果以广播形式发送
//				sendBroadcast(statusCode,responseString,null);
//			}
//
//			/**
//			 * 请求完成时调用的方法,无论成功或者失败都会调用.
//			 */
//			@Override
//			public void onFinish() {
//				//sendBroadcast(Const.WEATHER_DATA_REQUEST_FINISH,null,null);
//			}
//			/**
//			 * 请求失败时调用的方法,statusCode为http状态码,throwable为捕获到的异常
//			 * statusCode:30002 没有检测到当前网络. 30003 没有进行初始化. 0
//			 * 未明异常,具体查看Throwable信息. 其他异常请参照http状态码.
//			 */
//			@Override
//			public void onFailure(int statusCode,String responseString, Throwable throwable) {
//				sendBroadcast(statusCode,responseString,throwable);
//			}
//		});
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
		JSONObject jsonObject2 = jsonObject.getJSONObject("showapi_res_body");
		//获取当前查询的城市进行封装
		weatherEntity.setCity(jsonObject2.getJSONObject( "cityInfo").getString("c3"));
		//返回"sk"JSONObject
		JSONObject jsonObject3 = jsonObject2.getJSONObject("now");		
		//封装当天天气
		CurrentWeather currentWeather = new CurrentWeather();
		currentWeather.setHumidity(jsonObject3.getString("sd"));
		currentWeather.setTemp(jsonObject3.getString("temperature"));
		currentWeather.setUpdateTime(jsonObject3.getString("temperature_time"));
		currentWeather.setWindDirection(jsonObject3.getString("wind_direction"));
		currentWeather.setWindStrength(jsonObject3.getString("weather_pic"));
		weatherEntity.setCurrentWeather(currentWeather);
		//封装未来天气,jsonObject4是一个包含未来几天天气的jsonObject
		JSONObject jsonObject4 = jsonObject2.getJSONObject("f2");
		//获取明天的日期,拼接为future的查询字段
//		String dayOfTomorrow = "day_"+getDateOfTomorrow(1L);
		//封装明天的天气状况信息
		FurtherWeather tomorrowWeather = new FurtherWeather();
//		tomorrowWeather.setDate(jsonObject4.getJSONObject(dayOfTomorrow).getString("date"));
//		tomorrowWeather.setTemp(jsonObject4.getJSONObject(dayOfTomorrow).getString("temperature"));
//		tomorrowWeather.setWeather(jsonObject4.getJSONObject(dayOfTomorrow).getString("weather"));
//		tomorrowWeather.setWeek(jsonObject4.getJSONObject(dayOfTomorrow).getString("week"));
//		tomorrowWeather.setWind(jsonObject4.getJSONObject(dayOfTomorrow).getString("wind"));
		
		tomorrowWeather.setDate(jsonObject4.getString("day"));
		tomorrowWeather.setTemp(jsonObject4.getString("day_air_temperature"));
		tomorrowWeather.setWeather(jsonObject4.getString("day_weather"));
		tomorrowWeather.setWeek(jsonObject4.getString("weekday"));
		tomorrowWeather.setWind(jsonObject4.getString("day_weather_pic"));
		weatherEntity.setTomorrowWeather(tomorrowWeather);
		//获取后天的日期,拼接为future的查询字段
	//	String dayOfAfterTomorrow = "day_"+getDateOfTomorrow(2L);
		JSONObject jsonObject5 = jsonObject2.getJSONObject("f3");
		FurtherWeather dayAftertomorrowWeather = new FurtherWeather();
//		dayAftertomorrowWeather.setDate(jsonObject4.getJSONObject(dayOfAfterTomorrow).getString("date"));
//		dayAftertomorrowWeather.setTemp(jsonObject4.getJSONObject(dayOfAfterTomorrow).getString("temperature"));
//		dayAftertomorrowWeather.setWeather(jsonObject4.getJSONObject(dayOfAfterTomorrow).getString("weather"));
//		dayAftertomorrowWeather.setWeek(jsonObject4.getJSONObject(dayOfAfterTomorrow).getString("week"));
//		dayAftertomorrowWeather.setWind(jsonObject4.getJSONObject(dayOfAfterTomorrow).getString("wind"));
		dayAftertomorrowWeather.setDate(jsonObject5.getString("day"));
		dayAftertomorrowWeather.setTemp(jsonObject5.getString("day_air_temperature"));
		dayAftertomorrowWeather.setWeather(jsonObject5.getString("day_weather"));
		dayAftertomorrowWeather.setWeek(jsonObject5.getString("weekday"));
		dayAftertomorrowWeather.setWind(jsonObject5.getString("night_weather_pic"));
		weatherEntity.setDayAftertomorrowWeather(dayAftertomorrowWeather);
		//将当前查询到的天气信息对象,设为app全局
		ETGApplication.weatherEntity = weatherEntity;
		return weatherEntity;

	}
}
