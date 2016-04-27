package com.lxl.travel.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lxl.travel.entity.BusEntity;
import com.lxl.trivel.R;

public class BusAdapter extends BaseAdapter{
	private Context context;
	private List<BusEntity> buses;
	private LayoutInflater inflater;
	public BusAdapter(Context context, List<BusEntity> bus){
		this.context = context;
		this.buses = bus;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return buses.size();
	}

	@Override
	public Object getItem(int position) {
		return buses.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHoler holder;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.bus_item_lv, null);
			holder = new ViewHoler();
			holder.depTime = (TextView) convertView.findViewById(R.id.busDepTimeTv);
			holder.fromCity = (TextView) convertView.findViewById(R.id.busFromCityTv);
			holder.toCity = (TextView) convertView.findViewById(R.id.busToCityTv);
			holder.price = (TextView) convertView.findViewById(R.id.priceTV);
			convertView.setTag(holder);
		}
		holder = (ViewHoler) convertView.getTag();
		holder.depTime.setText(buses.get(position).getDate());
		holder.fromCity.setText(buses.get(position).getStart());
		holder.toCity.setText(buses.get(position).getArrive());
		holder.price.setText(buses.get(position).getPrice());
		return convertView;
	}
	class ViewHoler{
		TextView depTime, price, fromCity, toCity;
	}
}

