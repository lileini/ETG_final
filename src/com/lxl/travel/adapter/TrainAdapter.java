package com.lxl.travel.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lxl.travel.entity.TrainEntity;
import com.lxl.trivel.R;

public class TrainAdapter extends BaseAdapter{
	private Context context;
	private List<TrainEntity> trains;
	private LayoutInflater inflater;
	public TrainAdapter(Context context, List<TrainEntity> trains){
		this.context = context;
		this.trains = trains;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return trains.size();
	}

	@Override
	public Object getItem(int position) {
		return trains.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.i("tag", "trains:"+trains);
		Log.i("tag01", "trains[0]:"+trains.get(0).toString());
		ViewHoler holder;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.train_item_lv, null);
			holder = new ViewHoler();
			holder.depTime = (TextView) convertView.findViewById(R.id.depTimeTv);
			holder.arrTime = (TextView) convertView.findViewById(R.id.arrTimeTv);
			holder.depStation = (TextView) convertView.findViewById(R.id.depStationTv);
			holder.arrStation = (TextView) convertView.findViewById(R.id.arrStation);
			holder.runTime = (TextView) convertView.findViewById(R.id.runTimeTv);
			holder.trainNum = (TextView) convertView.findViewById(R.id.trainNumTv);
			holder.price = (TextView) convertView.findViewById(R.id.priveTv);
			holder.priceType = (TextView) convertView.findViewById(R.id.priceTypeTv);
			convertView.setTag(holder);
		}
		holder = (ViewHoler) convertView.getTag();
		holder.depTime.setText(trains.get(position).getStart_time());
		Log.i("tag", "depTime:"+trains.get(position).getStart_time());
		holder.arrTime.setText(trains.get(position).getEnd_time());
		holder.depStation.setText(trains.get(position).getStart_station());
		holder.arrStation.setText(trains.get(position).getEnd_station());
		holder.runTime.setText(trains.get(position).getRun_time());
		holder.trainNum.setText(trains.get(position).getTrain_no());
		holder.price.setText(trains.get(position).getPrice_list().get(0).getPrice() + "¥  ");
		holder.priceType.setText(trains.get(position).getPrice_list().get(0).getPrice_type());
		return convertView;
	}
	class ViewHoler{
		TextView depTime, arrTime, depStation, arrStation, runTime, trainNum, price, priceType;
	}
}
