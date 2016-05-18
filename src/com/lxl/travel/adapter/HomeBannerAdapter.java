package com.lxl.travel.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class HomeBannerAdapter extends PagerAdapter {
	private Fragment fragment;
	private int[] bImages;

	public HomeBannerAdapter(Fragment fragment, int[] bImages) {
		this.fragment = fragment;
		this.bImages = bImages;
	}

	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View)object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		ImageView imageView = new ImageView(fragment.getActivity());

		imageView.setImageResource(bImages[position%bImages.length]);
		imageView.setScaleType(ScaleType.FIT_XY);

		container.addView(imageView);

//		imageView.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Toast.makeText(fragment.getActivity(), "pos = "+(position%bImages.length), Toast.LENGTH_SHORT).show();
//			}
//		});
		
		return imageView;
	}
}
