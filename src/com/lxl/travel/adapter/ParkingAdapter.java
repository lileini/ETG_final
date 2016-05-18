package com.lxl.travel.adapter;

import java.util.ArrayList;

import com.lidroid.xutils.BitmapUtils;
import com.lxl.travel.entity.ParkingEntity;
import com.lxl.trivel.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ParkingAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<ParkingEntity> data;
	private LayoutInflater inflater;
	private BitmapUtils bitmapUtils;
	public ParkingAdapter(Context context, ArrayList<ParkingEntity> data) {
		super();
		this.context = context;
		this.data = data;
		this.inflater = LayoutInflater.from(context);
		this.bitmapUtils = new BitmapUtils(context);
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
		ParkingEntity entity = data.get(position);
		ViewHolder holder;
		if (convertView == null){
			convertView = inflater.inflate(R.layout.nearby_parking_item, null);
			holder = new ViewHolder();
			holder.BTTCJG = (TextView) convertView.findViewById(R.id.tv_item_parking_BTTCJG);
			holder.CCDZ = (TextView) convertView.findViewById(R.id.tv_item_parking_address);
			holder.CCMC = (TextView) convertView.findViewById(R.id.tv_item_parking_CCMC);
			holder.CCTP = (ImageView) convertView.findViewById(R.id.iv_item_parking_CCTP);
			holder.KCW = (TextView) convertView.findViewById(R.id.tv_item_parking_KCW);
			holder.SFKF = (TextView) convertView.findViewById(R.id.tv_item_parking_SFKF);
			
			convertView.setTag(holder);
		}
		holder = (ViewHolder) convertView.getTag();

		holder.BTTCJG.setText("价格:" + entity.getBTTCJG());
		holder.CCDZ.setText("位置:" + entity.getCCDZ());
		holder.CCMC.setText("名称:" + entity.getCCMC());
		holder.KCW.setText("剩余车位:" + entity.getKCW());
		switch (Integer.parseInt(entity.getSFKF())) {
			case 1:
				holder.SFKF.setText("是否开放：开放");
				break;

			case 0:
				holder.SFKF.setText("是否开放：不开放");
				break;
		}
		if (entity.getCCTP() != null){
			bitmapUtils.display(holder.CCTP, entity.getCCTP());
		}else {
			holder.CCTP.setImageResource(R.drawable.ic_launcher);
		}

		return convertView;
	}

	class ViewHolder{
		/** 白天停车价格*/
		private TextView BTTCJG;
		/**地址*/
		private TextView CCDZ;
		/** 空车位*/
		private TextView KCW;
		/** 名称*/
		private TextView CCMC;
		/** 是否开放(0：不开放；1：开放；)*/
		private TextView SFKF;
		/**停车场图片*/
		private ImageView CCTP;
	}
}
