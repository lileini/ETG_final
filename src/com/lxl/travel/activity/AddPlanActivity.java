//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : EasyToTravel
//  @ File Name : AddPlanActivity.java
//  @ Date : 2015/10/12
//  @ Author : 
//
//
/**
 * @author 米钧
 * 
 * */


package com.lxl.travel.activity;

import java.sql.Date;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.http.RequestParams;
import com.lxl.travel.ETGApplication;
import com.lxl.travel.base.BaseActivity;
import com.lxl.travel.biz.RequestPlanListBiz;
import com.lxl.travel.entity.AltradionsEntity;
import com.lxl.travel.entity.PlanEntity;
import com.lxl.travel.utils.Const;
import com.lxl.trivel.R;


@SuppressLint("NewApi")
public class AddPlanActivity extends BaseActivity {

	private TextView fromDate_Tv;
	private TextView toDate_Tv;
	private Button search_Btn;
	private EditText price_Tv;
	private EditText remark_Et;
	private Button submit_Btn;
	private AddPlanResponseReceiver receiver;
	private EditText altradions_Et;
	private boolean isFromAltradions = false;
	private AltradionsEntity altradionsEntity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addplan);
		setViews();
		setListener();
		getActionBar().hide();
		//判断是否有景点信息
		Intent intent = getIntent();
		altradionsEntity = (AltradionsEntity) intent.getSerializableExtra("AltradionsEntity");
		if(altradionsEntity != null){
			altradions_Et.setText(altradionsEntity.getTravelAddress());
			price_Tv.setText(altradionsEntity.getTravelPrice());
			fromDate_Tv.setText(intent.getStringExtra("fromDate"));
			toDate_Tv.setText(intent.getStringExtra("toDate"));

			isFromAltradions = true;
			setPlanEntity();
		}
		//注册广播接收器
		receiver = new AddPlanResponseReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Const.RECEIVE_ADD_PLAN_RESPONSE);
		registerReceiver(receiver, filter);
	}

	@Override
	protected void onDestroy() {
		//解除广播接收器
		unregisterReceiver(receiver);
		super.onDestroy();
	}

	/**初始化各控件*/
	private void setViews() {
		fromDate_Tv = (TextView) findViewById(R.id.addPlan_fromdate_Tv);
		toDate_Tv = (TextView) findViewById(R.id.addPlan_todate_Tv);
		search_Btn = (Button) findViewById(R.id.addPlan_altradions_search_btn);
		altradions_Et = (EditText) findViewById(R.id.addPlan_altradions_Et);
		price_Tv = (EditText) findViewById(R.id.addPlan_price_Tv);
		remark_Et = (EditText) findViewById(R.id.addPlan_remark_edit);
		submit_Btn = (Button) findViewById(R.id.addPlan_submit_btn);
	}

	private PlanEntity planEntity;
	private String fromDate;
	private String toDate;
	/**添加控件监听*/
	private void setListener() {
		//添加日期选择空间监听
		fromDate_Tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setDate(v);
			}
		});

		toDate_Tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setDate(v);
			}
		});
		//点击查询景点按钮,跳转到查询页面,需要返回一个查询实体对象
		search_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AddPlanActivity.this, TravelAltradionsActivity.class);
				fromDate = fromDate_Tv.getText().toString();
				toDate = toDate_Tv.getText().toString();
				intent.putExtra("fromDate", fromDate);
				intent.putExtra("toDate", toDate);
				startActivity(intent);
				Toast.makeText(AddPlanActivity.this, "加载查询页面", Toast.LENGTH_SHORT).show();
			}
		});

		//点击添加按钮,提交计划数据到服务器
		submit_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if(planEntity == null){
					setPlanEntity();
				}else{
					sendAddQuest(planEntity);
				}
			}
		});
	}

	private void setPlanEntity() {
		//判断日期输入
		if(TextUtils.isEmpty(fromDate_Tv.getText()) || TextUtils.isEmpty(toDate_Tv.getText())){
			Toast.makeText(AddPlanActivity.this, "亲,还没有选择行程日期哦", Toast.LENGTH_LONG).show();	
			return;
		}
		//判断是否选择景点
		if(TextUtils.isEmpty(altradions_Et.getText())){
			Toast.makeText(AddPlanActivity.this, "亲,还缺少一个旅行目的地哦", Toast.LENGTH_LONG).show();	
			return;
		}
		//如果价格为空的话,给一个初始值
		if(TextUtils.isEmpty(price_Tv.getText())){
			price_Tv.setText("¥ 0.0");
		}

		//还需要判断出发日期是否大于返程日期

		//将消息封装到计划实体类
		planEntity = new PlanEntity();
		planEntity.setAltradionsAddress(altradions_Et.getText().toString());
		planEntity.setAltradionsPrice(Double.parseDouble(price_Tv.getText().toString().replace("¥ ", "")));
		planEntity.setFromDate(Date.valueOf(fromDate_Tv.getText().toString()));
		planEntity.setToDate(Date.valueOf(toDate_Tv.getText().toString()));
		planEntity.setRemark(remark_Et.getText().toString());
		planEntity.setUsername(ETGApplication.userEntity.getUsername());
		if(isFromAltradions){			
			isFromAltradions = false;
		}else{
			sendAddQuest(planEntity);
		}
	}

	/**向服务器发送添加请求*/
	protected void sendAddQuest(PlanEntity planEntity) {	
		RequestParams params = new RequestParams();
		params.addBodyParameter("username", planEntity.getUsername());
		params.addBodyParameter("fromDate",String.valueOf(planEntity.getFromDate()));
		params.addBodyParameter("toDate",String.valueOf(planEntity.getToDate()));
		params.addBodyParameter("AltradionsPrice",String.valueOf(planEntity.getAltradionsPrice()));
		params.addBodyParameter("AltradionsAddress",planEntity.getAltradionsAddress());
		params.addBodyParameter("remark",planEntity.getRemark());
		new RequestPlanListBiz().addPlan(params, this);
		//Toast.makeText(AddPlanActivity.this, "...", Toast.LENGTH_LONG).show();
	}



	/**日期选择器
	 * @param v */	
	private void setDate(final View v) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int monthOfYear = calendar.get(Calendar.MONTH);
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		Dialog dialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT, new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				String date = year + "-" + $(monthOfYear+1) + "-" + $(dayOfMonth);
				TextView tv = (TextView)v;
				tv.setText(date);
			}
		}, year, monthOfYear, dayOfMonth);
		dialog.show();
	}

	/**转化日期格式*/
	public String $(int n){
		return n < 10 ? "0" + n : String.valueOf(n);
	}

	/**接收添加计划请求的返回信息广播*/
	class AddPlanResponseReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			if(Const.RECEIVE_ADD_PLAN_RESPONSE.equals(intent.getAction())){
				if("ok".equals(intent.getStringExtra("result"))){
					Toast.makeText(AddPlanActivity.this, "添加行程成功", Toast.LENGTH_SHORT).show();
			/*		Intent intent1 = new Intent(AddPlanActivity.this, HomePageActivity.class);
					startActivity(intent1);*/
					finish();
				}else{
					Toast.makeText(AddPlanActivity.this, "添加行程失败,请稍后再试", Toast.LENGTH_SHORT).show();
				}
			}

		}

	}



}
