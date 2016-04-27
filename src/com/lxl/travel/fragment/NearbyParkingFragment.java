package com.lxl.travel.fragment;

import java.util.ArrayList;

import com.lxl.travel.ETGApplication;
import com.lxl.travel.adapter.ParkingAdapter;
import com.lxl.travel.biz.NearbyBiz;
import com.lxl.travel.entity.ParkingEntity;
import com.lxl.travel.utils.Const;
import com.lxl.trivel.R;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class NearbyParkingFragment extends Fragment {
	ListView listView;
	private ArrayList<ParkingEntity> data = new ArrayList<ParkingEntity>();
	private ParkingBroadcastReceiver receiver;
	private ParkingAdapter adapter;

	public NearbyParkingFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_nearby_parking,
				container, false);
		listView = (ListView) view.findViewById(R.id.lv_nearby_parking);
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (null != ETGApplication.location) {
			new NearbyBiz(getActivity()).nearbyParking(
					ETGApplication.location.getLongitude(),
					ETGApplication.location.getLatitude());
		}

		receiver = new ParkingBroadcastReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Const.ACTION_RESULT_NEARBY_PARKING);
		filter.addAction(Const.ACTION_LOCATION_CHANGED);
		getActivity().registerReceiver(receiver, filter);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		getActivity().unregisterReceiver(receiver);
		super.onDestroy();
	}

	class ParkingBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (Const.ACTION_LOCATION_CHANGED.equals(intent.getAction())) {
				new NearbyBiz(getActivity()).nearbyParking(
						ETGApplication.location.getLongitude(),
						ETGApplication.location.getLatitude());
			}

			if (Const.ACTION_RESULT_NEARBY_PARKING.equals(intent.getAction())) {

				int status = intent.getIntExtra("status", -1);
				if (status == Const.STATUS_OK) {

					ArrayList<ParkingEntity> list = (ArrayList<ParkingEntity>) intent
							.getSerializableExtra(Const.KEY_DATA);
					data.removeAll(data);
					for (ParkingEntity entity : list) {
						data.add(entity);
					}
					if (adapter == null) {
						adapter = new ParkingAdapter(getActivity(), data);
						listView.setAdapter(adapter);
					}
					adapter.notifyDataSetChanged();
				}

			}
		}

	}
}
