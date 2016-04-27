package com.lxl.travel.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lxl.travel.ETGApplication;
import com.lxl.travel.activity.PlanDetailActivity;
import com.lxl.travel.adapter.TravelPlanListAdapter;
import com.lxl.travel.biz.RequestPlanListBiz;
import com.lxl.travel.entity.PlanEntity;
import com.lxl.travel.utils.Const;
import com.lxl.trivel.R;

public class PastTravelPlanFragment extends Fragment {

	private DataPreparedReceiver receiver;
	private TravelPlanListAdapter adapter;
	private PullToRefreshListView mListView;
	RequestPlanListBiz planListBiz;
	private TextView empty_notifaction;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_currenttravelplan, null);
		empty_notifaction = (TextView)view.findViewById(R.id.empty_plan_data);
		empty_notifaction.setVisibility(View.GONE);
		mListView = (PullToRefreshListView) view.findViewById(R.id.xListView);
		planListBiz = new RequestPlanListBiz();
		pastPlan = new ArrayList<PlanEntity>();
		setListener();
		//注册广播接收器
		receiver = new DataPreparedReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Const.RECEIVE_PAST_PLAN);
		ETGApplication.instance.registerReceiver(receiver, filter);

		return view;
	}

	String[] items ;
	/**设置点击监听*/
	private void setListener() {
		items = new String[]{"查看","删除"};
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setSingleChoiceItems(items, -1, new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							Intent intent = new Intent(getActivity(),PlanDetailActivity.class);
							intent.putExtra("item", pastPlan.get(position-1));
							getActivity().startActivity(intent);
							dialog.dismiss();
							break;
						case 1:
							if(planListBiz!=null){
								planListBiz.deletePlan(pastPlan.get(position-1).getId(), ETGApplication.userEntity.getUsername());
								pastPlan.remove(position-1);
								adapter.notifyDataSetChanged();
								dialog.dismiss();
							}
							break;
						}
					}
				});
				builder.setCancelable(true);
				builder.show();
			}
		});
	}



	@Override
	public void onDestroy() {
		//注销广播接收器
		ETGApplication.instance.unregisterReceiver(receiver);
		super.onDestroy();
	}


	private ArrayList<PlanEntity> pastPlan ;
	class DataPreparedReceiver extends BroadcastReceiver{

		@SuppressWarnings("unchecked")
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if(Const.RECEIVE_PAST_PLAN.equals(action)){
				List<PlanEntity> temp = (ArrayList<PlanEntity>) intent.getSerializableExtra(Const.PAST_PLAN_DATA);
				if(temp != null){
					//先清空上次的数据,避免重复显示
					pastPlan.removeAll(pastPlan);
					for(PlanEntity entity : temp){
						pastPlan.add(entity);
					}
					setListView();
				}
			}
		}

	}
	private void setListView() {
		if(adapter == null){			
			//初始化适配器
			adapter=new TravelPlanListAdapter(getActivity());
			//设置子控件数据
			adapter.setData(pastPlan);
			//设置适配器
			mListView.setAdapter(adapter);
		}else{
			adapter.notifyDataSetChanged();
		}
	}
}
