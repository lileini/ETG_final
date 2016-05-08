//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : EasyToTravel
//  @ File Name : TravelPlayFragment.java
//  @ Date : 2015/10/12
//  @ Author : 
//
//



package com.lxl.travel.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.lxl.travel.ETGApplication;
import com.lxl.travel.activity.AddPlanActivity;
import com.lxl.travel.activity.LoginActivity;
import com.lxl.travel.adapter.TravelPlanViewPagerAdapter;
import com.lxl.travel.base.BaseFragment;
import com.lxl.trivel.R;

public class TravelPlanFragment extends BaseFragment {

	private ImageButton addplan_Ib;
	private View v;
	private ImageButton head_Ib;
	private RadioButton pastPlan_RadioB;
	private RadioButton currPlan_RadioB;
	private ViewPager vp;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.fragment_travelplan, null);
		setViews();
		setViewPager();
		setListener();
		setPlanChangeListener();
		return v;
	}

	/**设置viewpager的显示*/
	private void setViewPager() {
		List<Fragment> fragments = new ArrayList<Fragment>();
		TravelPlanViewPagerAdapter adapter = new TravelPlanViewPagerAdapter(getActivity().getSupportFragmentManager());
		//添加当前页面fragment
		CurrentTravelPlanFragment currentPlanFragment = new CurrentTravelPlanFragment();
		fragments.add(currentPlanFragment);
		//添加历史页面fragment
		PastTravelPlanFragment pastPlanFragment = new PastTravelPlanFragment();
		fragments.add(pastPlanFragment);

		adapter.setFragments(fragments);
		vp.setAdapter(adapter);
	}

	/**初始化控件*/
	private void setViews() {
		addplan_Ib = (ImageButton) v.findViewById(R.id.add_plan);
		//relativelayout = (RelativeLayout) v.findViewById(R.id.add_rlayout);	
		head_Ib = (ImageButton) v.findViewById(R.id.head);
		currPlan_RadioB = (RadioButton) v.findViewById(R.id.radio0);
		pastPlan_RadioB = (RadioButton) v.findViewById(R.id.radio1);
		vp = (ViewPager) v.findViewById(R.id.fragmrnt_container);
	}

	/**添加监听*/
	private void setListener() {
		//添加监听,点击时,弹出添加计划按钮
		head_Ib.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//隐藏头图像
				head_Ib.setVisibility(View.GONE);
				//显示add按钮
				addplan_Ib.setVisibility(View.VISIBLE);
				//以动画的方式 弹出
				Animation a=new TranslateAnimation(0, 0, addplan_Ib.getHeight(), 0);
				a.setDuration(500);
				a.setFillAfter(true);
				addplan_Ib.startAnimation(a);
				a.setFillAfter(false);
			}
		});

		//添加点击监听,跳转到添加计划页面
		addplan_Ib.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//隐藏add按钮
				addplan_Ib.setVisibility(View.INVISIBLE);
				//显示头图像
				head_Ib.setVisibility(View.VISIBLE);
				//跳转到添加计划页面
				if(ETGApplication.userEntity == null){
					Toast.makeText(ETGApplication.instance, "登录之后添加更多计划", Toast.LENGTH_SHORT).show();
					//延迟跳转到登录页面
					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {
							Intent intent = new Intent(getActivity(), LoginActivity.class);
							startActivity(intent);							
						}
					}, 1000);
					return ;
				}else{
					Intent intent = new Intent(getActivity(),AddPlanActivity.class);
					startActivity(intent);
				}
			}
		});
	}

	/**设置计划行程\历史行程切换,以及viewPager切换时,页面与选择按钮之间的关联监听*/
	private void setPlanChangeListener() {
		//当前行程监听
		currPlan_RadioB.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					vp.setCurrentItem(0);						
				}
			}
		});

		//历史行程监听
		pastPlan_RadioB.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					vp.setCurrentItem(1);
				}
			}
		});

		//ViewPager切换监听
		vp.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				if(arg0 == 0){
					currPlan_RadioB.setChecked(true);
					pastPlan_RadioB.setChecked(false);
				}else if(arg0 == 1){
					pastPlan_RadioB.setChecked(true);
					currPlan_RadioB.setChecked(false);
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {				
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {				
			}
		});

		/**触摸监听,用于隐藏添加按钮*/
		vp.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(addplan_Ib != null && addplan_Ib.getVisibility() == View.VISIBLE){
					//隐藏add按钮
					addplan_Ib.setVisibility(View.INVISIBLE);
					//以动画的方式 弹出
					Animation a=new TranslateAnimation(0, 0, 0, addplan_Ib.getHeight()-80);
					a.setDuration(300);
					a.setFillAfter(true);
					addplan_Ib.startAnimation(a);
					a.setFillAfter(false);
					//延迟设置head图像可见,动画更协调
					new Handler().postDelayed(new Runnable() {
						
						@Override
						public void run() {
							//显示头图像
							head_Ib.setVisibility(View.VISIBLE);		

						}
					}, 500);

				}

				return false;
			}
		});
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
