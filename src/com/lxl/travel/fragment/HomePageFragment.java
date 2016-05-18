package com.lxl.travel.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.lxl.travel.ETGApplication;
import com.lxl.travel.activity.NearbyPageActivity;
import com.lxl.travel.activity.TravelAltradionsActivity;
import com.lxl.travel.activity.TripPageActivity;
import com.lxl.travel.activityanim.ActivityChangeAnim;
import com.lxl.travel.adapter.HomeBannerAdapter;
import com.lxl.travel.base.BaseFragment;
import com.lxl.travel.utils.Const;
import com.lxl.trivel.R;

public class HomePageFragment extends BaseFragment {

	private Button travel_btn;
	private Button ticket_Tv;
	private Button trip_btn;
	private Button train_btn;
	private Button plane_btn;
	private Button bus_btn;
	private Button nearBy_btn;
	private Button food_btn;
	private Button wifi_btn;
	private Button park_btn;
	private ViewPager bPager;
	private int prePos = 0;
	private boolean isScroller = true;
	/**
	 * 广告图片
	 */
	private int[] bImages = {R.drawable.banner_1, R.drawable.banner_2, R.drawable.banner_3};


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_toppage, null);
		setViews(view);
		//初始化广告条
		setBannerPager();
		addListener();
		return view;
	}

	private void setBannerPager() {
		/*
        设置页面
         */
		bPager.setAdapter(new HomeBannerAdapter(this, bImages));

		/*bPager.addOnPageChangeListener(new BasePagerListener() {
			@Override
			public void onPageSelected(int position) {
				indicatorLayout.getChildAt(prePos % bImages.length).setEnabled(true);
				indicatorLayout.getChildAt(position % bImages.length).setEnabled(false);
				prePos = position;
			}
		});
*/
        /*
        设置2秒切换页面
         */
		final Handler h = new Handler();
		Runnable r = new Runnable() {
			@Override
			public void run() {
				if (isScroller) {
					bPager.setCurrentItem(bPager.getCurrentItem() + 1);
					h.postDelayed(this, 3000);
				}
			}
		};
		h.postDelayed(new Runnable() {
			@Override
			public void run() {
				if (isScroller) {
					bPager.setCurrentItem(bPager.getCurrentItem() + 1);
					h.postDelayed(this, 3000);
				}
			}
		}, 3000);
	}

	/**设置切换动画*/
	public void changeAnim(){
		ActivityChangeAnim.changeAnim(getActivity(), ActivityChangeAnim.MOVE_FROM_BOTTOM_TO_TOP, ActivityChangeAnim.NO_MOVE);
		
	}
	
	/** 设置跳转监听 */
	private void addListener() {
		travel_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						TravelAltradionsActivity.class);
				getActivity().startActivity(intent);
				changeAnim();
				//Toast.makeText(getActivity(), "跳转到景点查询", 1).show();
			}
		});

		ticket_Tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						TravelAltradionsActivity.class);
				getActivity().startActivity(intent);
				changeAnim();
				//Toast.makeText(getActivity(), "跳转到门票查询", 1).show();
			}
		});

		trip_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						TripPageActivity.class);
				getActivity().startActivity(intent);
				changeAnim();
				//Toast.makeText(getActivity(), "跳转到票务查询", 1).show();
			}
		});
		train_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						TripPageActivity.class);
				intent.putExtra(Const.TRAIN, Const.TRAIN);
				getActivity().startActivity(intent);
				changeAnim();
				//Toast.makeText(getActivity(), "跳转到火车票查询", 1).show();
			}
		});

		plane_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						TripPageActivity.class);
				intent.putExtra(Const.AIRPLANE, Const.AIRPLANE);
				getActivity().startActivity(intent);
				changeAnim();
				//Toast.makeText(getActivity(), "跳转到机票查询", 1).show();
			}
		});

		bus_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						TripPageActivity.class);
				intent.putExtra(Const.BUS,Const.BUS);
				getActivity().startActivity(intent);
				changeAnim();
				//Toast.makeText(getActivity(), "跳转到车票查询", Toast.LENGTH_SHORT).show();
			}
		});

		nearBy_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						NearbyPageActivity.class);
				getActivity().startActivity(intent);
				changeAnim();
				//Toast.makeText(getActivity(), "跳转到附近搜索", Toast.LENGTH_SHORT).show();
			}
		});

		food_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						NearbyPageActivity.class);
				getActivity().startActivity(intent);
				changeAnim();
				//Toast.makeText(getActivity(), "跳转到搜索美食", Toast.LENGTH_SHORT).show();
			}
		});

		wifi_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						NearbyPageActivity.class);
				getActivity().startActivity(intent);
				changeAnim();
				//Toast.makeText(getActivity(), "跳转到搜索wifi", Toast.LENGTH_SHORT).show();
			}
		});

		park_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						NearbyPageActivity.class);
				getActivity().startActivity(intent);
				changeAnim();
				//Toast.makeText(getActivity(), "跳转到搜索车位", Toast.LENGTH_SHORT).show();
			}
		});

	}

	/**
	 * 初始化控件
	 * 
	 * @param view
	 */
	private void setViews(View view) {
		travel_btn = (Button) view.findViewById(R.id.travel_Ib);
		ticket_Tv = (Button) view.findViewById(R.id.ticket_Tv);
		trip_btn = (Button) view.findViewById(R.id.trip_Iv);
		train_btn = (Button) view.findViewById(R.id.train_Tv);
		plane_btn = (Button) view.findViewById(R.id.plane_Tv);
		bus_btn = (Button) view.findViewById(R.id.bus_Tv);
		nearBy_btn = (Button) view.findViewById(R.id.nearBy_Iv);
		food_btn = (Button) view.findViewById(R.id.food_Tv);
		wifi_btn = (Button) view.findViewById(R.id.wifi_Tv);
		park_btn = (Button) view.findViewById(R.id.park_Tv);
		bPager = (ViewPager)view.findViewById(R.id.ad_banner_Vp);

	}

}
