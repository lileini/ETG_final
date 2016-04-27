package com.lxl.travel.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class HpViewPagerAdapter extends FragmentPagerAdapter{
	private List<Fragment> fragments ;
	
	public void setFragments(List<Fragment> fragments) {
		this.fragments = fragments;
	}

	public HpViewPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int postion) {
		return fragments.get(postion);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

}
