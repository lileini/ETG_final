//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : EasyToTravel
//  @ File Name : JsonParser.java
//  @ Date : 2015/10/12
//  @ Author : 
//
//



package com.lxl.travel.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import android.util.Log;

import com.lxl.travel.entity.AltradionsEntity;
import com.lxl.travel.entity.BusEntity;
import com.lxl.travel.entity.FlightEntity;
import com.lxl.travel.entity.TrainEntity;
import com.lxl.travel.entity.TravelDetailEntity;
import com.lxl.travel.entity.TrainEntity.Price_list;

public class JsonParser {
	

	static TrainEntity entity = new TrainEntity();
	public static List<FlightEntity> parseFlight(JSONArray ary) throws JSONException {
		
		List<FlightEntity> flights = new ArrayList<FlightEntity>();
		
			for (int i=0; i<ary.length(); i++){
				JSONObject obj = ary.getJSONObject(i);
				flights.add(new FlightEntity(obj.getString("DepCity"),obj.getString("ArrCity"),
					obj.getString("Airline"),obj.getString("FlightNum"),obj.getString("OnTimeRate")
					,obj.getString("FlightDate"),obj.getString("DepTime"),obj.getString("ArrTime")
					,obj.getString("DepTerminal"),obj.getString("ArrTerminal")));
					Log.i("info", "parse:"+flights);
			}
			Log.i("info", "return:"+flights);
			return flights;
	}
	public static List<TrainEntity> parseTrainList(JSONArray ary) throws JSONException{
		List<TrainEntity> trains = new ArrayList<TrainEntity>();
		for(int i=0; i<ary.length(); i++){
			JSONObject obj = ary.getJSONObject(i);
			trains.add(new TrainEntity(obj.getString("end_time"),obj.getString("start_station")
					,obj.getString("start_station_type"),obj.getString("start_time"),
					obj.getString("run_distance"),obj.getString("end_station_type"),
					obj.getString("train_type"),obj.getString("train_no"),obj.getString("run_time"),
					obj.getString("end_station"),parsePrice_list(obj.getJSONArray("price_list"))));
		}
		return trains;
	}
	public static List<BusEntity> parseBusList(JSONArray ary) throws JSONException{
		List<BusEntity> buses = new ArrayList<BusEntity>();
		for(int i=0; i<ary.length(); i++){
			JSONObject obj = ary.getJSONObject(i);
			buses.add(new BusEntity(obj.getString("arrive"),obj.getString("price")
					,obj.getString("start"),obj.getString("date")));
		}
		return buses;
	}
	public static List<Price_list> parsePrice_list(JSONArray ary) throws JSONException{
		List<Price_list> price_lists = new ArrayList<TrainEntity.Price_list>();
		for(int i=0; i<ary.length(); i++){
			JSONObject obj = ary.getJSONObject(i);
			price_lists.add(entity.new Price_list(obj.getString("price_type"),obj.getString("price")));
		}
		return price_lists;
	}
	public static ArrayList<String> parseCity(JSONArray ary) throws JSONException{
	/*	String[] citys = null;
		for(int i=0; i<ary.length(); i++){
			JSONObject obj = ary.getJSONObject(i);
			citys = new String[ary.length()];
			citys[i] = obj.getString("city");
		}
		Log.i("info", "parse:"+citys);
		return citys;*/
		
		
		ArrayList<String> list = new ArrayList<String>();
		for(int i=0; i<ary.length(); i++){
			JSONObject obj = ary.getJSONObject(i);
			list.add(obj.getString("city"));
		}
		/*String[] citys = new String[list.size()];
		for (int i=0; i<list.size(); i++){
			citys[i] = list.get(i);
		}*/
		//Log.i("info", "parse list:"+list);
		return list;
		
	} 	
	public static ArrayList<String> parseTrainCitys(JSONArray ary) throws JSONException{
		ArrayList<String> list = new ArrayList<String>();
		for(int i=0; i<ary.length(); i++){
			JSONObject obj = ary.getJSONObject(i);
			list.add(obj.getString("sta_name"));
		}	
		return list;
	}

	public static ArrayList<AltradionsEntity> listcm = new ArrayList<AltradionsEntity>();
	public static ArrayList<AltradionsEntity> parser(String json,int number) {   //解析景点信息。
		listcm.clear();
		try {
			JSONObject jsonObject = new JSONObject(json);
			//Log.i("JSONObject = ", jsonObject.toString());
			String k = jsonObject.getString("reason");
			//Log.i("reason = ", k);
			JSONObject jsonobj = jsonObject.getJSONObject("result");
			JSONArray jsonArray = jsonobj.getJSONArray("ScenicSpotListItemList");
			//Log.i("jsonArray = ", jsonArray.toString());
			//Log.i("jsonArray01 = ", jsonArray.getString(1).toString());
			for (int i = 0; i < number; i++) {
				try {
					JSONObject obj = jsonArray.getJSONObject(i);
					String Address = "",CommentGrade="",ID = "",Image = "",Name = "",Price="",
							ProductManagerRecommand = "",Star = "";
					Address = obj.getString("Address");
					CommentGrade = obj.getString("CommentGrade"); //评分
					ID = obj.getString("ID");
					Image = obj.getString("Image");
					Name = obj.getString("Name");
					Price = obj.getString("Price");
					ProductManagerRecommand = obj.getString("ProductManagerRecommand"); //推荐
					Star = obj.getString("Star");
					
					
					

					AltradionsEntity AltradionsEntity = new AltradionsEntity();
					AltradionsEntity.setTravelAddress(Address);
					AltradionsEntity.setTravelID(ID);
					AltradionsEntity.setTravelName(Name);
					AltradionsEntity.setTravelPrice(Price);
					AltradionsEntity.setTravelStar(Star);
					AltradionsEntity.setCommentGrade(CommentGrade);
					AltradionsEntity.setImage(Image);
					AltradionsEntity.setProductManagerRecommand(ProductManagerRecommand);
					listcm.add(AltradionsEntity);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listcm;
	}
	public static TravelDetailEntity parserForDetail(String json){   //解析景点的详细细节。
		//Log.i("json = ", json);
		TravelDetailEntity entity = new TravelDetailEntity();
		try {
			JSONObject data = new JSONObject(json);
			JSONArray ary = data.getJSONObject("result")
					.getJSONArray("ScenicSpotList");
			String OpenTimeDesc = ary.getJSONObject(0).getString("OpenTimeDesc");
			//Log.i("OpenTimeDesc = ", OpenTimeDesc);
			String Introduction = ary.getJSONObject(0).getJSONArray("ProductList")
			.getJSONObject(0).getJSONObject("ProductDescriptionInfo")
			.getString("Introduction");
			//Log.i("Introduction = ", Introduction);
			entity.setOpenTimeDesc(OpenTimeDesc);
			entity.setIntroduction(Introduction);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return entity;
	}
}



