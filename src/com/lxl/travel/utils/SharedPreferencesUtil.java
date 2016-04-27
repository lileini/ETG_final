package com.lxl.travel.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesUtil {
	/**
	 * 
	 * @param context
	 * @param name �ļ�������
	 * @param key Ҫ�����key
	 * @param data Ҫ�����key��value,ÿ�δ�ȡ����Ҫ��keyһһ��Ӧ
	 */
	public static void saveShared(Context context, String name, String[] keys, List<String> data){
		SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		for (int i=0; i<keys.length; i++){
			editor.putString(keys[i], data.get(i));
		}
		editor.commit();  
	}
	
	/**
	 * 
	 * @param context
	 * @param name Ҫ��ȡ���ļ���
	 * @param key ����ʱ��keyֵ
	 * @return map���ϣ���δȥ���򷵻� "";
	 */
	 public static Map<String, String> getPerferences(Context context,  String name, String[] keys) {  
	        Map<String, String> params = new HashMap<String, String>();  
	        SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
	        for (int i=0; i<keys.length; i++){
	        	String data = preferences.getString(keys[i], "");
	        	params.put(keys[i], data);
	        }
	        return params;  
	    }  
}
