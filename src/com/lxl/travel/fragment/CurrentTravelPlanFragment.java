package com.lxl.travel.fragment;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.annotation.SuppressLint;
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
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lxl.travel.ETGApplication;
import com.lxl.travel.activity.PlanDetailActivity;
import com.lxl.travel.adapter.TravelPlanListAdapter;
import com.lxl.travel.base.BaseFragment;
import com.lxl.travel.biz.RequestPlanListBiz;
import com.lxl.travel.entity.PlanEntity;
import com.lxl.travel.utils.Const;
import com.lxl.trivel.R;

public class CurrentTravelPlanFragment extends BaseFragment {
	private PullToRefreshListView mListView;
	private ArrayList<PlanEntity> currentPlanData;
	private ArrayList<PlanEntity> pastPlanData;
	private PlanBroadcastReceiver receiver;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_currenttravelplan, null);
		setListView(view);

		// 如果用户已登录,则用户名不为空
		if (ETGApplication.userEntity != null) {
			planListBiz.allPlan(ETGApplication.userEntity.getUsername(),
					ETGApplication.instance);
		} // else{//用户未登录
		// 设置显示无数据时的提示
		// currentPlanData.add(new PlanEntity(null, null, null, 0, "no data",
		// null));
		// setCunrrentItemDisplayAdapter();
		// }
		return view;
	}

	String[] items;
	private boolean isFirst = true;
	private TextView empty_notifaction;
	private int currentPos ;
	/**
	 * 初始化listview
	 *
	 * @param view
	 */
	private void setListView(View view) {
		mListView = (PullToRefreshListView) view.findViewById(R.id.xListView);
		empty_notifaction = (TextView) view.findViewById(R.id.empty_plan_data);
		mListView.setMode(Mode.PULL_FROM_START);
		mListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				if (ETGApplication.userEntity != null) {
					planListBiz.allPlan(
							ETGApplication.userEntity.getUsername(),
							ETGApplication.instance);
				} else {
					if (isFirst) {
						Toast.makeText(getActivity(), "请点击大白的肚子,添加计划...",
								Toast.LENGTH_SHORT).show();
						isFirst = false;
					}
					mListView.onRefreshComplete();
				}

			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub

			}
		});//setOnRefreshListener

		items = new String[] { "查看", "删除" };
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				builder.setSingleChoiceItems(items, -1, new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							Intent intent = new Intent(getActivity(),
									PlanDetailActivity.class);
							intent.putExtra("item",
									currentPlanData.get(position - 1));
							getActivity().startActivity(intent);
							dialog.dismiss();
							break;
						case 1:
							if (planListBiz != null) {
								planListBiz
								.deletePlan(
										currentPlanData.get(
												position - 1).getId(),
												ETGApplication.userEntity
												.getUsername());
								currentPos = position-1;
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


	private RequestPlanListBiz planListBiz;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		planListBiz = new RequestPlanListBiz();
		pastPlanData = new ArrayList<PlanEntity>();
		currentPlanData = new ArrayList<PlanEntity>();
		// 注册接收获取服务返回信息的广播接收器
		receiver = new PlanBroadcastReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Const.ACTION_RESPONE_PLAN);
		getActivity().registerReceiver(receiver, filter);

	}

	@Override
	public void onDestroy() {
		// 注销广播注册
		getActivity().unregisterReceiver(receiver);
		super.onDestroy();

	}

	class PlanBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// 收到广播就执行停止
			mListView.onRefreshComplete();
			int status = intent.getIntExtra("status", -1);
			switch (status) {
			case Const.STATUS_RECEIVE_OK:
				// 成功获取解析的数据
				@SuppressWarnings("unchecked")
				ArrayList<PlanEntity> list = (ArrayList<PlanEntity>) intent
				.getSerializableExtra("data");
				// 遍历list,把数据放入data,保证与adapter关联的集合对象,始终是同一个对象
				// 将实时行程与历史行程数据进行分流(5天前的数据全部划分为历史行程)
				try {
					splitPlan(list);
				} catch (ParseException e) {
					Toast.makeText(ETGApplication.instance, "加载数据失败",
							Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}
				break;
			case Const.STATUS_RECEIVE_FAILED:
				// 获取解析的数据失败
				// Toast.makeText(ETGApplication.instance,
				// "当前没有计划...点击大白添加吧...", Toast.LENGTH_LONG).show();
				break;

			case Const.STATUS_RECEIVED_DATA_PARSE_FAILED:
				// 解析数据失败
				Toast.makeText(ETGApplication.instance, "加载数据失败",
						Toast.LENGTH_LONG).show();
				break;

			case Const.STATUS_SERVER_CONNECT_FAILED:
				// 连接服务器失败
				Toast.makeText(ETGApplication.instance, "服务器忙,请稍后再试",
						Toast.LENGTH_LONG).show();
				break;
			case Const.STATUS_OK:
				// 删除计划成功
				try {
					currentPlanData.remove(currentPos);
					adapter.notifyDataSetChanged();
					Toast.makeText(ETGApplication.instance, "删除成功",
							Toast.LENGTH_LONG).show();
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
		}

	}

	/** 分流当前行程数据和历史行程数据,分别获取mListview显示需要的数据 */
	private void splitPlan(ArrayList<PlanEntity> list) throws ParseException {
		// 先将数据进行保存
		// ??????????????

		// 在写入数据之前,先清空上次的数据,防止数据重复显示
		currentPlanData.removeAll(currentPlanData);
		pastPlanData.removeAll(pastPlanData);
		for (int i = 0; i < list.size(); i++) {
			// 获取返程时间
			PlanEntity planEntity = list.get(i);
			long toDate = parseToDate(planEntity);
			// 获取当前时间用于比较
			long tempDate = System.currentTimeMillis();
			if (toDate < tempDate - 86400000) {
				pastPlanData.add(planEntity);
			} else {// 说明是当前行程,放到当前行程中
				currentPlanData.add(planEntity);
			}
		}
		setCunrrentItemDisplayAdapter();
		// 发送广播,通知历史行程页面进行历史行程形成数据加载
		Intent intent = new Intent(Const.RECEIVE_PAST_PLAN);
		intent.putExtra(Const.PAST_PLAN_DATA, pastPlanData);
		getActivity().sendBroadcast(intent);
	}

	private TravelPlanListAdapter adapter;

	/** 设置当前计划的界面 */
	private void setCunrrentItemDisplayAdapter() {
		if (adapter == null) {
			// 初始化适配器
			adapter = new TravelPlanListAdapter(getActivity());
			// 设置子控件数据
			adapter.setData(currentPlanData);
			// 设置适配器
			mListView.setAdapter(adapter);
		} else {
			adapter.notifyDataSetChanged();
		}
		empty_notifaction.setVisibility(View.GONE);
	}

	/**
	 * 解析查询到的计划中的返程时间为毫秒
	 * 
	 * @param planEntity
	 * @throws ParseException
	 */
	@SuppressLint("SimpleDateFormat")
	private long parseToDate(PlanEntity planEntity) throws ParseException {
		Date tempDate = planEntity.getToDate();
		String toDate = String.valueOf(tempDate).replace("-", "");
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();
		java.util.Date tempDate2 = format.parse(toDate);
		c.setTime(tempDate2);
		long result = c.getTimeInMillis();
		return result;
	}
}

