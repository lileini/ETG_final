package com.lxl.travel.activity;

import com.lxl.travel.base.BaseActivity;
import com.lxl.trivel.R;
import com.lxl.trivel.R.layout;
import com.lxl.trivel.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class AboutUSActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_us);
		getActionBar().hide();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.about_u, menu);
		return true;
	}

}
