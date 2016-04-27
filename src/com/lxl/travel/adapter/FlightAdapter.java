package com.lxl.travel.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lxl.travel.entity.FlightEntity;
import com.lxl.trivel.R;

public class FlightAdapter extends BaseAdapter{
	private Context context;
	private List<FlightEntity> flights;
	private LayoutInflater inflater;
	public FlightAdapter(Context context, List<FlightEntity> flights){
		this.context = context;
		this.flights = flights;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return flights.size();
	}

	@Override
	public Object getItem(int position) {
		return flights.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHoler holder;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.flight_item_lv, null);
			holder = new ViewHoler();
			holder.depTime = (TextView) convertView.findViewById(R.id.depTimetv);
			holder.arrTime = (TextView) convertView.findViewById(R.id.arrTimetv);
			holder.depTerminal = (TextView) convertView.findViewById(R.id.depTerminaltv);
			holder.arrTerminal = (TextView) convertView.findViewById(R.id.arrTerminaltv);
			holder.airline = (TextView) convertView.findViewById(R.id.airlinetv);
			holder.flightNum = (TextView) convertView.findViewById(R.id.flightNumtv);
			convertView.setTag(holder);
		}
		holder = (ViewHoler) convertView.getTag();
		holder.depTime.setText(flights.get(position).getDepTime());
		holder.arrTime.setText(flights.get(position).getArrTime());
		holder.depTerminal.setText(flights.get(position).getDepTerminal());
		holder.arrTerminal.setText(flights.get(position).getArrTerminal());
		holder.airline.setText(flights.get(position).getAirline());
		holder.flightNum.setText(flights.get(position).getFlightNum());
		return convertView;
	}
	class ViewHoler{
		TextView depTime, arrTime, depTerminal, arrTerminal, airline, flightNum;
	}
}
