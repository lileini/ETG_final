package com.lxl.travel.adapter;

import java.util.List;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;
import com.lxl.travel.entity.CulinaryEntity;
import com.lxl.travel.utils.LogUtil;
import com.lxl.trivel.R;
import com.thinkland.sdk.android.loopj.h;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CulinaryAdapter extends BaseAdapter {
	Context context;
	LayoutInflater inflater;
	List<CulinaryEntity> data;
	BitmapUtils bitmapUtils;

	public CulinaryAdapter(Context context, List<CulinaryEntity> data) {
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
		CulinaryEntity entity = data.get(position);
		LogUtil.i("getView", "entity" + entity);
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.nearby_culinary_item, null);
			holder = new ViewHolder();
			holder.address = (TextView) convertView
					.findViewById(R.id.tv_item_curlinary_address);
			holder.all_remarks = (TextView) convertView
					.findViewById(R.id.tv_item_curlinary_all_remarks);
			holder.avg_price = (TextView) convertView
					.findViewById(R.id.tv_item_curlinary_avg_price);
			holder.name = (TextView) convertView
					.findViewById(R.id.tv_item_culinary_name);
			holder.good_remarks = (TextView) convertView
					.findViewById(R.id.tv_item_curlinary_good_remarks);
			holder.photos = (ImageView) convertView
					.findViewById(R.id.iv_item_culinary_photos);
			convertView.setTag(holder);
		}
		holder = (ViewHolder) convertView.getTag();
		holder.address.setText("地址：" + entity.getAddress());
		holder.name.setText(entity.getName());
		holder.all_remarks.setText("总评：" + entity.getAll_remarks() + "人");
		holder.good_remarks.setText("好评："
				+ (entity.getGood_remarks() + entity.getVery_good_remarks())
				+ "人");
		holder.avg_price.setText("人均：" + entity.getAvg_price());
		LogUtil.i("entity.getPhotos()", entity.getPhotos());
		if (entity.getPhotos() == null){
			holder.photos.setImageResource(R.drawable.ic_launcher);
		} else {
//			String url = ImageURLFormatUtil.format(entity.getPhotos());
			bitmapUtils.display(holder.photos, entity.getPhotos());
		}
		return convertView;
	}

	class ViewHolder {
		private ImageView photos;
		private TextView name, address, avg_price;
		private TextView all_remarks, good_remarks;
	}

}
