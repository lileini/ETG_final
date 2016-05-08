package com.lxl.travel.biz;


import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;

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



public class RequestWeatherFromBdiBiz {
	public static Context context;


	public static void getWeatherData(Context mContext, int port, String url,RequestParams params){
			context = mContext;
		
		HttpUtils httpUtils = new HttpUtils(10000);
		httpUtils.configCurrentHttpCacheExpiry(1000);
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

				context.sendBroadcast(intent);
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				// TODO Auto-generated method stub
				sendBroadcast(responseInfo.statusCode,responseInfo.result,null);
			}
		});

	}

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
		context.sendBroadcast(intent);
	}

	public static WeatherEntity parseWeatherData(String respString) throws JSONException {
		WeatherEntity weatherEntity = new WeatherEntity();
		//���ذ���"resultcode","reason","result"��JSONObject
		JSONObject jsonObject = new JSONObject(respString);
		//���ذ���"sk","today","future"�ֶε�JSONObject
		JSONObject jsonObject2 = jsonObject.getJSONObject("showapi_res_body");
		//��ȡ��ǰ��ѯ�ĳ��н��з�װ
		weatherEntity.setCity(jsonObject2.getJSONObject( "cityInfo").getString("c3"));
		//����"sk"JSONObject
		JSONObject jsonObject3 = jsonObject2.getJSONObject("now");		
		//��װ��������
		CurrentWeather currentWeather = new CurrentWeather();
		currentWeather.setHumidity(jsonObject3.getString("sd"));
		currentWeather.setTemp(jsonObject3.getString("temperature"));
		currentWeather.setUpdateTime(jsonObject3.getString("temperature_time"));
		currentWeather.setWindDirection(jsonObject3.getString("wind_direction"));
		currentWeather.setWindStrength(jsonObject3.getString("weather_pic"));
		weatherEntity.setCurrentWeather(currentWeather);
		JSONObject jsonObject4 = jsonObject2.getJSONObject("f2");

		FurtherWeather tomorrowWeather = new FurtherWeather();

		tomorrowWeather.setDate(jsonObject4.getString("day"));
		tomorrowWeather.setTemp(jsonObject4.getString("day_air_temperature"));
		tomorrowWeather.setWeather(jsonObject4.getString("day_weather"));
		tomorrowWeather.setWeek(jsonObject4.getString("weekday"));
		tomorrowWeather.setWind(jsonObject4.getString("day_weather_pic"));
		weatherEntity.setTomorrowWeather(tomorrowWeather);
		JSONObject jsonObject5 = jsonObject2.getJSONObject("f3");
		FurtherWeather dayAftertomorrowWeather = new FurtherWeather();
		dayAftertomorrowWeather.setDate(jsonObject5.getString("day"));
		dayAftertomorrowWeather.setTemp(jsonObject5.getString("day_air_temperature"));
		dayAftertomorrowWeather.setWeather(jsonObject5.getString("day_weather"));
		dayAftertomorrowWeather.setWeek(jsonObject5.getString("weekday"));
		dayAftertomorrowWeather.setWind(jsonObject5.getString("night_weather_pic"));
		weatherEntity.setDayAftertomorrowWeather(dayAftertomorrowWeather);
		ETGApplication.weatherEntity = weatherEntity;
		return weatherEntity;

	}
}
