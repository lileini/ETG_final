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

/**ͨ�������ȡ��������,�����������ݽ��з�װ*/
public class RequestWeatherDataBiz {
	public static Context context;

	/**���������ȡ��������*/
	public static void getWeatherData(Context mContext, int port, String url,Parameters params){
		context = mContext;
		/**
		 * �벻Ҫ���key����.
		 *//*
		Parameters params = new Parameters();
		params.add("ip", "www.juhe.cn");
		params.add("dtype", "xml");*/
		/**
		 * ����ķ��� ����: ��һ������ ��ǰ�����context �ڶ������� �ӿ�id ������������ �ӿ������url ���ĸ����� �ӿ�����ķ�ʽ
		 * ��������� �ӿ�����Ĳ���,��ֵ��com.thinkland.sdk.android.Parameters����; ����������
		 * ����Ļص�����,com.thinkland.sdk.android.DataCallBack;
		 * 
		 */
		JuheData.executeWithAPI(mContext, port,url,JuheData.GET, params, new DataCallBack() {
			/**
			 * ����ɹ�ʱ���õķ��� statusCodeΪhttp״̬��,responseString    *Ϊ���󷵻�����.
			 */
			@Override
			public void onSuccess(int statusCode, String responseString) {
				//������Թ㲥��ʽ����
				sendBroadcast(statusCode,responseString,null);
			}

			/**
			 * �������ʱ���õķ���,���۳ɹ�����ʧ�ܶ������.
			 */
			@Override
			public void onFinish() {
				//sendBroadcast(Const.WEATHER_DATA_REQUEST_FINISH,null,null);
			}
			/**
			 * ����ʧ��ʱ���õķ���,statusCodeΪhttp״̬��,throwableΪ���񵽵��쳣
			 * statusCode:30002 û�м�⵽��ǰ����. 30003 û�н��г�ʼ��. 0
			 * δ���쳣,����鿴Throwable��Ϣ. �����쳣�����http״̬��.
			 */
			@Override
			public void onFailure(int statusCode,String responseString, Throwable throwable) {
				sendBroadcast(statusCode,responseString,throwable);
			}
		});
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
		JSONObject jsonObject2 = jsonObject.getJSONObject("result");
		//��ȡ��ǰ��ѯ�ĳ��н��з�װ
		weatherEntity.setCity(jsonObject2.getJSONObject( "today").getString("city"));
		//����"sk"JSONObject
		JSONObject jsonObject3 = jsonObject2.getJSONObject("sk");		
		//��װ��������
		CurrentWeather currentWeather = new CurrentWeather();
		currentWeather.setHumidity(jsonObject3.getString("humidity"));
		currentWeather.setTemp(jsonObject3.getString("temp"));
		currentWeather.setUpdateTime(jsonObject3.getString("time"));
		currentWeather.setWindDirection(jsonObject3.getString("wind_direction"));
		currentWeather.setWindStrength(jsonObject3.getString("wind_strength"));
		weatherEntity.setCurrentWeather(currentWeather);
		//��װδ������,jsonObject4��һ������δ������������jsonObject
		JSONObject jsonObject4 = jsonObject2.getJSONObject("future");
		//��ȡ���������,ƴ��Ϊfuture�Ĳ�ѯ�ֶ�
		String dayOfTomorrow = "day_"+getDateOfTomorrow(1L);
		//��װ���������״����Ϣ
		FurtherWeather tomorrowWeather = new FurtherWeather();
		tomorrowWeather.setDate(jsonObject4.getJSONObject(dayOfTomorrow).getString("date"));
		tomorrowWeather.setTemp(jsonObject4.getJSONObject(dayOfTomorrow).getString("temperature"));
		tomorrowWeather.setWeather(jsonObject4.getJSONObject(dayOfTomorrow).getString("weather"));
		tomorrowWeather.setWeek(jsonObject4.getJSONObject(dayOfTomorrow).getString("week"));
		tomorrowWeather.setWind(jsonObject4.getJSONObject(dayOfTomorrow).getString("wind"));
		weatherEntity.setTomorrowWeather(tomorrowWeather);
		//��ȡ���������,ƴ��Ϊfuture�Ĳ�ѯ�ֶ�
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

	/**���ݵ�ǰϵͳ����,��ȡ�����ʱ������*/
	private static String getDateOfTomorrow(long dayCount) {
		//ָ�����ڸ�ʽ
		SimpleDateFormat sdf = new SimpleDateFormat("yyyMMdd");
		//��ǰϵͳʱ��(����)����һ���ʱ��(����ֵ86400000),�ٽ��и�ʽת��
		//���ⳬ��ֱ��+1��ʱ,�ᷢ��������������������Χ
		String date = sdf.format(new Date(System.currentTimeMillis()+86400000*dayCount));
		return date;
	}
}
