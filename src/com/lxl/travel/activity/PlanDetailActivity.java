package com.lxl.travel.activity;

import java.text.SimpleDateFormat;
import java.util.List;

import com.lxl.travel.entity.PlanEntity;
import com.lxl.trivel.R;
import com.lxl.trivel.R.layout;
import com.lxl.trivel.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class PlanDetailActivity extends Activity {
	
	private TextView dateFrom_Tv;
	private TextView dateTo_Tv;
	private TextView address_detail_Tv;
	private TextView price_detail_Tv;
	private TextView remark_detail_Tv;
	private PlanEntity planEntity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_plan_detail);
		getActionBar().hide();
		//获取传递过来的计划实体数据
		Intent intent = getIntent();
		planEntity = (PlanEntity) intent.getSerializableExtra("item");
		setViews();
	}

	/**初始化控件,赋值*/
	private void setViews() {
		dateFrom_Tv = (TextView) findViewById(R.id.planDetail_fromDate);
		dateTo_Tv = (TextView) findViewById(R.id.planDetail_toDate);
		address_detail_Tv = (TextView) findViewById(R.id.planDetail_addressDetail);
		price_detail_Tv = (TextView) findViewById(R.id.planDetail_priceDetail);
		remark_detail_Tv = (TextView) findViewById(R.id.planDetail_remarkDetail);
		//给每个控件赋值
		if(planEntity!=null){
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			dateFrom_Tv.setText(dateFormat.format(planEntity.getFromDate()));
			dateTo_Tv.setText(dateFormat.format(planEntity.getToDate()));
			address_detail_Tv.setText(planEntity.getAltradionsAddress());
			price_detail_Tv.setText(String.valueOf(planEntity.getAltradionsPrice()));
			remark_detail_Tv.setText(planEntity.getRemark());
		}
	}

}
