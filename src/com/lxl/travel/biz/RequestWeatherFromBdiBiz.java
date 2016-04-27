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


/**ͨ�������ȡ��������,�����������ݽ��з�װ*/
public class RequestWeatherFromBdiBiz {
	public static Context context;

	/**���������ȡ��������*/
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
				//���͹㲥
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
//				//������Թ㲥��ʽ����
//				sendBroadcast(statusCode,responseString,null);
//			}
//
//			/**
//			 * �������ʱ���õķ���,���۳ɹ�����ʧ�ܶ������.
//			 */
//			@Override
//			public void onFinish() {
//				//sendBroadcast(Const.WEATHER_DATA_REQUEST_FINISH,null,null);
//			}
//			/**
//			 * ����ʧ��ʱ���õķ���,statusCodeΪhttp״̬��,throwableΪ���񵽵��쳣
//			 * statusCode:30002 û�м�⵽��ǰ����. 30003 û�н��г�ʼ��. 0
//			 * δ���쳣,����鿴Throwable��Ϣ. �����쳣�����http״̬��.
//			 */
//			@Override
//			public void onFailure(int statusCode,String responseString, Throwable throwable) {
//				sendBroadcast(statusCode,responseString,throwable);
//			}
//		});
	}

	/**�����˷������й㲥����*/
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
		//���͹㲥
		context.sendBroadcast(intent);
	}

	/**�����˷���,�������ݵĽ���
	 * @throws JSONException */
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
		//��װδ������,jsonObject4��һ������δ������������jsonObject
		JSONObject jsonObject4 = jsonObject2.getJSONObject("f2");
		//��ȡ���������,ƴ��Ϊfuture�Ĳ�ѯ�ֶ�
//		String dayOfTomorrow = "day_"+getDateOfTomorrow(1L);
		//��װ���������״����Ϣ
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
		//��ȡ���������,ƴ��Ϊfuture�Ĳ�ѯ�ֶ�
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
		//����ǰ��ѯ����������Ϣ����,��Ϊappȫ��
		ETGApplication.weatherEntity = weatherEntity;
		return weatherEntity;

	}
}
