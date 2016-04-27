package com.lxl.travel.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
import android.widget.Toast;

import com.lxl.travel.ETGApplication;
import com.lxl.travel.adapter.WifiAdapter;
import com.lxl.travel.biz.NearbyBiz;
import com.lxl.travel.entity.WIFIEntity;
import com.lxl.travel.utils.Const;
import com.lxl.trivel.R;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class NearbyWIFIFragment extends Fragment {
	private ArrayList<WIFIEntity> data = new ArrayList<WIFIEntity>();
	private ListView listView;
	private WifiAdapter adapter;
	private WifiBroadcastReceiver receiver;

	public NearbyWIFIFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.fragment_nearby_wifi, container,
				false);
		listView = (ListView) v.findViewById(R.id.lv_nearby_wifi);
		return v;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		if(ETGApplication.location != null){
			new NearbyBiz(getActivity()).nearbyWIFI(ETGApplication.location.getLongitude()
					,ETGApplication.location.getLatitude());
		}else{
			Toast.makeText(getActivity(), "定位中，请稍后", Toast.LENGTH_SHORT).show();
		}
		receiver = new WifiBroadcastReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Const.ACTION_RESULT_NEARBY_WIFI);
		filter.addAction(Const.ACTION_LOCATION_CHANGED);
		getActivity().registerReceiver(receiver, filter);
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		getActivity().unregisterReceiver(receiver);
		super.onDestroy();
	}

	class WifiBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (Const.ACTION_LOCATION_CHANGED.equals(intent.getAction())){
				new NearbyBiz(getActivity()).nearbyWIFI(ETGApplication.location.getLongitude()
						,ETGApplication.location.getLatitude());
			}
			if (Const.ACTION_RESULT_NEARBY_WIFI.equals(intent.getAction())) {

				int status = intent.getIntExtra("status", -1);
				switch (status) {
				case Const.STATUS_OK:
					ArrayList<WIFIEntity> list = (ArrayList<WIFIEntity>) intent
							.getSerializableExtra(Const.KEY_DATA);
					Collections.sort(list, new Comparator<WIFIEntity>() {
						@Override
						public int compare(WIFIEntity entity1,
								WIFIEntity entity2) {
							return Integer.parseInt(entity1.getDistance()) < Integer
									.parseInt(entity2.getDistance()) ? -1 : 1;
						}
					});
					
					if (adapter == null) {
						adapter = new WifiAdapter(getActivity(), data);
						listView.setAdapter(adapter);
					}
					data.retainAll(data);
					for (WIFIEntity entity : list) {
						data.add(entity);
					}
					break;

				}

			}
		}

	}

}
