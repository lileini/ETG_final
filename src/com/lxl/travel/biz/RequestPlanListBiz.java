package com.lxl.travel.biz;

import java.sql.Date;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
		//��Ҫ�ڹ��췽���н�����û��������ж�
		//??????????????
		threadPool = Executors.newSingleThreadExecutor();
	}

	public void allPlan(final String username, final Context context) {
		threadPool.execute(new Runnable() {

			@Override
			public void run() {
				// 6�볬ʱ
				HttpUtils httpUtils = new HttpUtils(6000);
				//�ϴθ�������ֻ����500����(Ĭ��60s) ����ʱ������Ļ�,�����������ˢ��,�ǲ��ᷢ�������
				httpUtils.configCurrentHttpCacheExpiry(500);
				String url = Const.GLOB_URL + "findAll" + "?username=" + username;
				RequestParams params = new RequestParams();
				//params.addBodyParameter("username", username);
				httpUtils.send(HttpMethod.GET, url, params,
						new RequestCallBack<String>() {
					int status = -1;

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						
						// ����ɹ�
						if (responseInfo.statusCode == 200) {
							String info = responseInfo.result;
							Log.i("info", "info: "+info);
							try {
								JSONObject object = new JSONObject(info);
								String result = object.getString("result");
								if ("ok".equals(result)) {
									// ��ѯ�ɹ� ��ȡjson����
									String json = object.getString("data");
									// ����json
									// status = �ɹ�״̬
									ArrayList<PlanEntity> list = parseJSONArray(json);
									Log.i("info", list+"");
									//�㲥���ͽ���json�õ���palnentity����
									Intent intent = new Intent(Const.ACTION_RESPONE_PLAN);
									//��״̬��ֵ,�������ݳɹ�
									status = Const.STATUS_RECEIVE_OK;
									intent.putExtra("status", status);
									intent.putExtra("data", list);
									context.sendBroadcast(intent);									
								} else if ("err".equals(result)) {
									//���͹㲥,status = û�л�ȡ������(���޼ƻ�)
									Intent intent = new Intent(Const.ACTION_RESPONE_PLAN);
									status = Const.STATUS_RECEIVE_FAILED;
									intent.putExtra("status", status);
									context.sendBroadcast(intent);
								}
							} catch (JSONException e) {
								//���͹㲥,����json����
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
						// status = ����ʧ��
						// ���㲥
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
	
	/**���͹㲥*/
	private void sendBroadcat(String result) {
		Intent intent = new Intent(Const.RECEIVE_ADD_PLAN_RESPONSE);
		intent.putExtra("result", result);
		ETGApplication.instance.sendBroadcast(intent);
	}

	/**
	 * �����������ɾ���ƻ�������
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
