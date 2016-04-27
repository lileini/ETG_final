package com.lxl.travel.adapter;

import java.util.ArrayList;

import com.lxl.travel.entity.WIFIEntity;
import com.lxl.trivel.R;
import com.thinkland.sdk.android.loopj.h;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WifiAdapter extends BaseAdapter{
	
	private Context context;
	private ArrayList<WIFIEntity> data;
	private LayoutInflater inflater;
	public WifiAdapter(Context context, ArrayList<WIFIEntity> data) {
		super();
		this.context = context;
		this.data = data;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		WIFIEntity entity = data.get(position);
		ViewHolder holder ;
		if (convertView == null){
			convertView = inflater.inflate(R.layout.nearby_wifi_item, null);
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.tv_item_wifi_name);
			holder.address = (TextView) convertView.findViewById(R.id.tv_item_wifi_address);
			holder.distance = (TextView) convertView.findViewById(R.id.tv_item_wifi_distance);
			holder.intro = (TextView) convertView.findViewById(R.id.tv_item_wifi_intro);
			
			convertView.setTag(holder);
		}
		holder = (ViewHolder) convertView.getTag();
		holder.address.setText("Œª÷√:" + entity.getAddress());
		holder.distance.setText("æ‡Œ“:" + entity.getDistance() + "m");
		holder.intro.setText("√Ë ˆ:" + entity.getIntro());
		holder.name.setText("√˚≥∆:" + entity.getName());
		return convertView;
	}
	class ViewHolder{
		private TextView name,intro,address,distance; 
	}
}
