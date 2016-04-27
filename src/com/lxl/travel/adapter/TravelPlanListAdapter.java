package com.lxl.travel.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.lxl.travel.entity.PlanEntity;
import com.lxl.trivel.R;

public class TravelPlanListAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private ArrayList<PlanEntity> planData;
	private Context context;

	public TravelPlanListAdapter(Context context) {
		inflater = LayoutInflater.from(context);
		this.context = context;
	}

	public void setData(ArrayList<PlanEntity> currentPlanData) {
		this.planData = currentPlanData;
	}

	@Override
	public int getCount() {
		return planData.size();
	}

	@Override
	public Object getItem(int position) {
		return planData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		/*View v = null;
		if (planData.get(position).getAltradionsAddress().equals("no data")) {
			v = inflater.inflate(R.layout.empty_plan_data, null);
			return v;
		} else {
			if (v != null) {
				v.setVisibility(View.GONE);
			}*/
			if (convertView == null) {
				convertView = inflater.inflate(
						R.layout.item_travelplan_xlistview_1, null);
				holder = new ViewHolder();
				holder.fromDate_Tv = (TextView) convertView
						.findViewById(R.id.plan_fromDate_Tv);
				holder.toDate_Tv = (TextView) convertView
						.findViewById(R.id.plan_toDate_Tv);
			
				holder.address_Tv = (TextView) convertView
						.findViewById(R.id.plan_address_Tv);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			// 设置日期格式
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			// 解析Date数据获得日期字符串
			String fromDate = format.format(planData.get(position)
					.getFromDate());
			holder.fromDate_Tv.setText(fromDate);
			String toDate = format.format(planData.get(position).getToDate());
			holder.toDate_Tv.setText(toDate);
			holder.address_Tv.setText(planData.get(position)
					.getAltradionsAddress());
			return convertView;
	//	}
	}

	class ViewHolder {
		private TextView fromDate_Tv;
		private TextView toDate_Tv;
		// private TextView tv_functions;
		// private TextView tv_coating;
		private TextView address_Tv;
		// private FrameLayout xlistview_delete_flayout;
		// private TextView plan_center_Tv;
	}

}
