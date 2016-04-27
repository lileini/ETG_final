package com.lxl.travel.biz;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;

import com.lxl.travel.ETGApplication;
import com.lxl.travel.entity.CulinaryEntity;
import com.lxl.travel.entity.ParkingEntity;
import com.lxl.travel.entity.WIFIEntity;
import com.lxl.travel.utils.Const;
import com.lxl.travel.utils.LogUtil;
import com.thinkland.sdk.android.DataCallBack;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;
import com.thinkland.sdk.android.loopj.e;

public class NearbyBiz {
	private Activity activity;

	public NearbyBiz(Activity activity) {
		this.activity = activity;
	}

	public void nearbyParking(double lon, double lat) {
		Parameters params = new Parameters();
//		params.add("lon", 104.06);
//		params.add("lat", 30.67);
		params.add("lon", lon);
		params.add("lat", lat);
		params.add("distance", 2000);
		JuheData.executeWithAPI(activity, 133,
				"http://api2.juheapi.com/park/query", JuheData.GET, params,
				new DataCallBack() {
				int status = -1;
					@Override
					public void onFailure(int arg0, String arg1, Throwable arg2) {
						// TODO Auto-generated method stub
						LogUtil.i("nearbyParking", "onFailure" + arg0 + ";"
								+ arg1 + arg2);
					}

					@Override
					public void onFinish() {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(int arg0, String result) {
						LogUtil.i("nearbyParking", "onSuccess" + arg0 + ";"
								+ result);
						if (arg0 == 200) {
							try {
								JSONObject object = new JSONObject(result);
								if ("ok".equals(object.getString("reason"))) {
									String json = object.getString("result");
									JSONArray array = new JSONArray(json);
									ArrayList<ParkingEntity> list = parseParkingJsonArray(array);
									Intent intent = new Intent(Const.ACTION_RESULT_NEARBY_PARKING);
									intent.putExtra("status", Const.STATUS_OK);
									intent.putExtra(Const.KEY_DATA, list);
									ETGApplication.instance.sendBroadcast(intent);
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				});
	}

	private ArrayList<ParkingEntity> parseParkingJsonArray(JSONArray array) {
		ArrayList<ParkingEntity> list = new ArrayList<ParkingEntity>();
		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject object = array.getJSONObject(i);
				ParkingEntity entity = new ParkingEntity();
				entity.setBTTCJG(object.getString("BTTCJG"));
				entity.setCCDZ(object.getString("CCDZ"));
				entity.setCCMC(object.getString("CCMC"));
				entity.setCCTP(object.getString("CCTP"));
				entity.setKCW(object.getString("KCW"));
				entity.setSFKF(object.getString("SFKF"));
				list.add(entity);
			}
			return list;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void nearbyWIFI(double lon, double lat) {
		Parameters params = new Parameters();
		// params.add("lon", location.getLongitude());
		// params.add("lat", location.getLatitude());
//		params.add("lon", 104.06);
//		params.add("lat", 30.67);
		params.add("lon", lon);
		params.add("lat", lat);
		params.add("gtype", 3);
		JuheData.executeWithAPI(activity, 18, "http://apis.juhe.cn/wifi/local",
				JuheData.GET, params, new DataCallBack() {
					int status = -1;

					@Override
					public void onFailure(int arg0, String arg1, Throwable arg2) {
						// TODO Auto-generated method stub
						LogUtil.i("onFailure", "onFailure" + arg0 + ",----"
								+ arg1 + ";----" + arg2);
						status = Const.STATUS_ERR;
						Intent intent = new Intent(
								Const.ACTION_RESULT_NEARBY_WIFI);
						intent.putExtra("status", Const.STATUS_OK);
					}

					@Override
					public void onFinish() {

					}

					@Override
					public void onSuccess(int resultStatus, String resultInfo) {
						LogUtil.i("onSuccess", "" + resultInfo);
						if (resultStatus == 200) {
							try {
								JSONObject object = new JSONObject(resultInfo);
								if ("Successed!".equals(object
										.getString("reason"))) {
									String result = object.getString("result");
									LogUtil.i("result", result);
									JSONObject object_result = new JSONObject(
											result);
									JSONArray array = new JSONArray(
											object_result.getString("data"));
									LogUtil.i("onSuccess", array);
									status = Const.STATUS_OK;
									ArrayList<WIFIEntity> data = parseWIFIJsonArray(array);
									Intent intent = new Intent(
											Const.ACTION_RESULT_NEARBY_WIFI);
									intent.putExtra("status", Const.STATUS_OK);
									intent.putExtra(Const.KEY_DATA, data);
									ETGApplication.instance
											.sendBroadcast(intent);
								}
							} catch (JSONException e) {
								e.printStackTrace();

							}
						}
					}

				});
	}

	protected ArrayList<WIFIEntity> parseWIFIJsonArray(JSONArray array) {
		ArrayList<WIFIEntity> data = new ArrayList<WIFIEntity>();
		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject object = array.getJSONObject(i);
				WIFIEntity entity = new WIFIEntity();
				entity.setAddress(object.getString("address"));
				entity.setIntro(object.getString("intro"));
				entity.setName(object.getString("name"));
				entity.setDistance(object.getString("distance"));

				data.add(entity);
			}
			return data;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void nearbyCulinary(double lon, double lat, final int page) {

		Parameters params = new Parameters();
		// params.add("lng", location.getLongitude());
		// params.add("lat", location.getLatitude());
//		params.add("lng", 104.06);
//		params.add("lat", 30.67);
		params.add("lng", lon);
		params.add("lat", lat);
		params.add("page", page);
		params.add("dtype", "json");
		JuheData.executeWithAPI(activity, 45,
				"http://apis.juhe.cn/catering/query", JuheData.GET, params,
				new DataCallBack() {
					@Override
					public void onFailure(int arg0, String arg1, Throwable arg2) {
						LogUtil.i("TAG", "" + arg1);
						Intent intent = new Intent(
								Const.ACTION_RESULT_NEARBY_CULINARY);
						intent.putExtra("status", Const.STATUS_ERR);
						// ֪ͨ���߳��޸�listView
						ETGApplication.instance.sendBroadcast(intent);
					}

					@Override
					public void onFinish() {
						LogUtil.i("TAG", "" + "onFinishs");
					}

					@Override
					public void onSuccess(int resultcode, String responseString) {
						LogUtil.i("nearbyCulinary", "onSuccess" + resultcode);
						if (resultcode == 200) {
							try {
								JSONObject object = new JSONObject(
										responseString);
								if ("查询成功".equals(object.getString("reason"))) {
									JSONArray array = new JSONArray(object
											.getString("result"));
									LogUtil.i("result", "result:" + array);
									ArrayList<CulinaryEntity> data = parseCulinaryJSONArray(array);
									Intent intent = new Intent(
											Const.ACTION_RESULT_NEARBY_CULINARY);
									intent.putExtra(Const.KEY_DATA, data);
									intent.putExtra("status", Const.STATUS_OK);
									// ֪ͨ���߳��޸�listView
									ETGApplication.instance
											.sendBroadcast(intent);
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					}
				});

	}

	private ArrayList<CulinaryEntity> parseCulinaryJSONArray(JSONArray array) {
		ArrayList<CulinaryEntity> data = new ArrayList<CulinaryEntity>();
		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject object = array.getJSONObject(i);
				CulinaryEntity entity = new CulinaryEntity();
				entity.setPhotos(object.getString("photos"));
				entity.setAddress(object.getString("address"));
				String all_remarks = object.getString("all_remarks");
				if (!"".equals(all_remarks)) {
					entity.setAll_remarks(Integer.parseInt(all_remarks));
				} else {
					entity.setAll_remarks(0);
				}
				entity.setAvg_price(object.getString("avg_price"));
				String bad_remarks = object.getString("bad_remarks");
				if (!"".equals(bad_remarks)) {
					entity.setBad_remarks(Integer.parseInt(bad_remarks));
				} else {
					entity.setBad_remarks(0);
				}
				String common_remarks = object.getString("common_remarks");
				if (!"".equals(common_remarks)) {
					entity.setCommon_remarks(Integer.parseInt(common_remarks));
				} else {
					entity.setCommon_remarks(0);
				}
				entity.setEnvironment_rating(object
						.getString("environment_rating"));
				String good_remarks = object.getString("good_remarks");
				if (!"".equals(good_remarks)) {
					entity.setGood_remarks(Integer.parseInt(good_remarks));
				} else {
					entity.setGood_remarks(0);
				}
				entity.setName(object.getString("name"));
				entity.setPhone(object.getString("phone"));
				entity.setProduct_rating(object.getString("product_rating"));
				entity.setRecommended_products(object
						.getString("recommended_products"));
				entity.setService_rating(object.getString("service_rating"));
				String very_bad_remarks = object.getString("very_bad_remarks");
				if (!"".equals(good_remarks)) {
					entity.setVery_bad_remarks(Integer
							.parseInt(very_bad_remarks));
				} else {
					entity.setVery_bad_remarks(0);
				}
				String very_good_remarks = object
						.getString("very_good_remarks");
				if (!"".equals(good_remarks)) {
					entity.setVery_good_remarks(Integer
							.parseInt(very_good_remarks));
				} else {
					entity.setCommon_remarks(0);
				}
				data.add(entity);
			}
			return data;
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}
}
