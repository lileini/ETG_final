package com.lxl.travel.biz;

import java.sql.Date;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.ParseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lxl.travel.ETGApplication;
import com.lxl.travel.activity.AddPlanActivity;
import com.lxl.travel.entity.PlanEntity;
import com.lxl.travel.utils.Const;
import com.thinkland.sdk.android.a.a;

public class RequestPlanListBiz {
	private ExecutorService threadPool;

	public RequestPlanListBiz(){
		//需要在构造方法中进行有没有网络的判断
		//??????????????
		threadPool = Executors.newSingleThreadExecutor();
	}

	public void allPlan(final String username, final Context context) {
		threadPool.execute(new Runnable() {

			@Override
			public void run() {
				// 6秒超时
				HttpUtils httpUtils = new HttpUtils(6000);
				//上次更新内容只缓存500毫秒(默认60s) 缓存时间过长的话,如果连续下拉刷新,是不会发送请求的
				httpUtils.configCurrentHttpCacheExpiry(500);
				String url = Const.GLOB_URL + "findAll" + "?username=" + username;
				RequestParams params = new RequestParams();
				//params.addBodyParameter("username", username);
				httpUtils.send(HttpMethod.GET, url, params,
						new RequestCallBack<String>() {
					int status = -1;

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						
						// 请求成功
						if (responseInfo.statusCode == 200) {
							String info = responseInfo.result;
							Log.i("info", "info: "+info);
							try {
								JSONObject object = new JSONObject(info);
								String result = object.getString("result");
								if ("ok".equals(result)) {
									// 查询成功 获取json数据
									String json = object.getString("data");
									// 解析json
									// status = 成功状态
									ArrayList<PlanEntity> list = parseJSONArray(json);
									Log.i("info", list+"");
									//广播发送解析json得到的palnentity集合
									Intent intent = new Intent(Const.ACTION_RESPONE_PLAN);
									//给状态赋值,接收数据成功
									status = Const.STATUS_RECEIVE_OK;
									intent.putExtra("status", status);
									intent.putExtra("data", list);
									context.sendBroadcast(intent);									
								} else if ("err".equals(result)) {
									//发送广播,status = 没有获取到数据(暂无计划)
									Intent intent = new Intent(Const.ACTION_RESPONE_PLAN);
									status = Const.STATUS_RECEIVE_FAILED;
									intent.putExtra("status", status);
									context.sendBroadcast(intent);
								}
							} catch (JSONException e) {
								//发送广播,解析json错误
								Intent intent = new Intent(Const.ACTION_RESPONE_PLAN);
								status = Const.STATUS_RECEIVED_DATA_PARSE_FAILED;
								intent.putExtra("status", status);
								context.sendBroadcast(intent);
								e.printStackTrace();
							} 
						}

					}

					@Override
					public void onFailure(HttpException error, String msg) {
						// status = 连接失败
						// 发广播
						Intent intent = new Intent(Const.ACTION_RESPONE_PLAN);
						status = Const.STATUS_SERVER_CONNECT_FAILED;
						intent.putExtra("status", status);
						context.sendBroadcast(intent);
						Log.i("info", "onFailure: "+msg);
					}
				});

			}
		});

	}

	public ArrayList<PlanEntity> parseJSONArray(String json) throws JSONException {
		ArrayList<PlanEntity> list = new ArrayList<PlanEntity>();
		JSONArray array = new JSONArray(json);
		for(int i=0; i<array.length(); i++){
			PlanEntity entity = new PlanEntity();
			JSONObject obj = array.getJSONObject(i);
			entity.setAltradionsAddress(obj.getString("AltradionsAddress"));
			entity.setAltradionsPrice(obj.getDouble("price"));
			entity.setFromDate(Date.valueOf(obj.getString("fromDate")));
			entity.setId(obj.getInt("id"));
			entity.setRemark(obj.getString("remark"));
			entity.setToDate(Date.valueOf(obj.getString("toDate")));
			entity.setUsername(obj.getString("username"));
			list.add(entity);
		}
		return list;
	}

	/**向服务器发送请求添加计划
	 * @param planEntity 
	 * @param addPlanActivity */
	public void addPlan(final RequestParams params, Context context) {
		threadPool.execute(new Runnable() {

			@Override
			public void run() {
				HttpUtils httpUtils = new HttpUtils(6000);
				String url = Const.GLOB_URL+"addPlan";
				httpUtils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						Log.i("info", "add resp: "+responseInfo.result);
						sendBroadcat("ok");
					};
					
					@Override
					public void onFailure(HttpException error, String msg) {
						Log.i("info", "add msg: "+msg);
						Log.i("info", "add error: "+error.getMessage());
						Log.i("info", "add Code: "+error.getExceptionCode());
						sendBroadcat(msg);
					}
				});

			}
		});

	}
	
	/**发送广播*/
	private void sendBroadcat(String result) {
		Intent intent = new Intent(Const.RECEIVE_ADD_PLAN_RESPONSE);
		intent.putExtra("result", result);
		ETGApplication.instance.sendBroadcast(intent);
	}

	/**
	 * 向服务器发送删除计划的请求
	 * @param id
	 * @param username
	 */
	public void deletePlan(final int id, final String username) {
		threadPool.execute(new Runnable() {

			@Override
			public void run() {
				HttpUtils httpUtils = new HttpUtils(6000);
				String url = Const.GLOB_URL+"delete?username="+username+"&id="+id;
				httpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> respResult) {
						if(200 == respResult.statusCode){
							String jsonStr = respResult.result;
							try {
								JSONObject object = new JSONObject(jsonStr);
								String result = object.getString("result");
								if("ok".equals(result)){
									Intent intent = new Intent(Const.ACTION_RESPONE_PLAN);
									intent.putExtra("status", Const.STATUS_OK);
									ETGApplication.instance.sendBroadcast(intent);
								}	
							} catch (JSONException e) {
								e.printStackTrace();
							}
							
						}
					}

					@Override
					public void onFailure(HttpException exception, String msg) {
						// TODO Auto-generated method stub

					}
				});

			}
		});

	}
}
