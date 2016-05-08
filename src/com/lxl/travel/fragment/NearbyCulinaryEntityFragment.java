package com.lxl.travel.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lxl.travel.ETGApplication;
import com.lxl.travel.adapter.CulinaryAdapter;
import com.lxl.travel.biz.NearbyBiz;
import com.lxl.travel.entity.CulinaryEntity;
import com.lxl.travel.utils.Const;
import com.lxl.travel.utils.LogUtil;
import com.lxl.trivel.R;

/**
 * A simple {@link Fragment} subclass.
 * 
 */
public class NearbyCulinaryEntityFragment extends Fragment {

	private PullToRefreshListView listView;
	private List<CulinaryEntity> data = new ArrayList<CulinaryEntity>();
	private static int currentPage = 1;
	CulinaryAdapter adapter;
	CulinaryBroadcastReceiver receiver;

	public NearbyCulinaryEntityFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_nearby_culinary_entity,
				container, false);
		listView = (PullToRefreshListView) v
				.findViewById(R.id.lv_fragment_culinary);

		setListener();
		return v;
	}

	private void setListener() {

		listView.setMode(Mode.BOTH);
		listView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				if (refreshView.isHovered()) {

					String label = DateUtils.formatDateTime(getActivity(),
							System.currentTimeMillis(),
							DateUtils.FORMAT_SHOW_TIME
									| DateUtils.FORMAT_SHOW_DATE
									| DateUtils.FORMAT_ABBREV_ALL);

					// Update the LastUpdatedLabel
					refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(
							label);

					// Do work to refresh the list here.
					currentPage = 1;
					if (ETGApplication.location != null) {
						new NearbyBiz(getActivity()).nearbyCulinary(
								ETGApplication.location.getLongitude(),
								ETGApplication.location.getLatitude(),
								currentPage++);
					}
				} else {
					if (ETGApplication.location != null) {
						new NearbyBiz(getActivity()).nearbyCulinary(
								ETGApplication.location.getLongitude(),
								ETGApplication.location.getLatitude(),
								currentPage++);
					}
				}
			}
		});

		listView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				Toast.makeText(getActivity(), "上滑加载更多数据", Toast.LENGTH_SHORT)
						.show();
			}
		});

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		receiver = new CulinaryBroadcastReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Const.ACTION_RESULT_NEARBY_CULINARY);
		filter.addAction(Const.ACTION_LOCATION_CHANGED);
		getActivity().registerReceiver(receiver, filter);
		if (ETGApplication.location != null) {
			new NearbyBiz(getActivity()).nearbyCulinary(
					ETGApplication.location.getLongitude(),
					ETGApplication.location.getLatitude(), 1);
		}else {
			Toast.makeText(getActivity(), "定位中，请稍后", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		getActivity().unregisterReceiver(receiver);
		super.onDestroy();

	}

	public void updateListView() {
		if (adapter == null) {
			adapter = new CulinaryAdapter(getActivity(), data);
			listView.setAdapter(adapter);
		}
		adapter.notifyDataSetChanged();
	}

	class CulinaryBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (Const.ACTION_LOCATION_CHANGED.equals(intent.getAction())){
				new NearbyBiz(getActivity()).nearbyCulinary(
						ETGApplication.location.getLongitude(),
						ETGApplication.location.getLatitude(), 1);
			}
			if (Const.ACTION_RESULT_NEARBY_CULINARY.equals(intent.getAction())){

				int status = intent.getIntExtra("status", -1);
				LogUtil.i("CulinaryBroadcastReceiver", "onReceive");
				switch (status) {
				case Const.STATUS_OK:
					// ArrayList<CulinaryEntity> data
					ArrayList<CulinaryEntity> list = (ArrayList<CulinaryEntity>) intent
							.getSerializableExtra(Const.KEY_DATA);
					for (int i = 0; i < list.size(); i++) {
						data.add(list.get(i));
						LogUtil.i("data", data.get(i));
					}
					updateListView();
					break;
				case Const.STATUS_ERR:
					Toast.makeText(getActivity(), "数据获取失败", Toast.LENGTH_SHORT)
							.show();
					break;
				}
			}
		}
	}
}
