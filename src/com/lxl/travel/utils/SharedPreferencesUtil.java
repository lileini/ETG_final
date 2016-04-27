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
	 * @param name 文件的名字
	 * @param key 要储存的key
	 * @param data 要储存的key的value,每次存取都需要与key一一对应
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
	 * @param name 要读取的文件名
	 * @param key 储存时的key值
	 * @return map集合，若未去到则返回 "";
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
