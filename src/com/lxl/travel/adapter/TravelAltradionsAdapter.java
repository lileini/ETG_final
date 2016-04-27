package com.lxl.travel.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.lxl.travel.entity.AltradionsEntity;
import com.lxl.trivel.R;

public class TravelAltradionsAdapter extends BaseAdapter{
	private ArrayList<AltradionsEntity> travelData;
	private Context context;
	private BitmapUtils bitmapUtils;
	public TravelAltradionsAdapter(Context context,ArrayList<AltradionsEntity> travelData,BitmapUtils bitmapUtils) {
		this.travelData = travelData;
		this.context = context;
		this.bitmapUtils = bitmapUtils;
	}

	@Override
	public int getCount() {
		return travelData.size();
	}

	@Override
	public Object getItem(int position) {
		return travelData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewholder = null;
		if(convertView ==null){
			convertView = View.inflate(context, R.layout.travel_item, null);
			viewholder = new ViewHolder();
			viewholder.imageView = (ImageView)convertView.findViewById(R.id.imageView1);
			viewholder.textView1 = (TextView)convertView.findViewById(R.id.textView1);
			viewholder.textView2 = (TextView)convertView.findViewById(R.id.textView2);
			viewholder.textView3 = (TextView)convertView.findViewById(R.id.textView3);
			viewholder.textView4 = (TextView)convertView.findViewById(R.id.textView4);
			viewholder.textView5 = (TextView)convertView.findViewById(R.id.textView5);
			convertView.setTag(viewholder);
		}
		viewholder = (ViewHolder)convertView.getTag();
		viewholder.textView1.setText(travelData.get(position).getTravelName()) ;
		viewholder.textView2.setText(travelData.get(position).getCommentGrade()) ;
		viewholder.textView3.setText(travelData.get(position).getTravelStar()) ;
		viewholder.textView4.setText(travelData.get(position).getTravelPrice()) ;
		viewholder.textView5.setText(travelData.get(position).getTravelAddress()) ;
		try {
			bitmapUtils.display(viewholder.imageView,travelData.get(position).getImage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return convertView;
	}
	
	class ViewHolder{
		ImageView imageView;
		TextView textView1;
		TextView textView2;
		TextView textView3;
		TextView textView4;
		TextView textView5;
	}
	
}
