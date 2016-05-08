package com.lxl.travel.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class BaseActivity extends FragmentActivity {
    public static String TAG;
    public static ArrayList<Activity> mActivityList = new ArrayList<Activity>();

    protected HashMap<String, Fragment> mFragments;
    protected String mCurFragment;
    protected boolean isFirst;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityList.add(this);
        TAG = getLocalClassName();
        Log.i(TAG,"~~~~~~~~~~~~~Oncreate");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"~~~~~~~~~~~~~onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivityList.remove(this);

        Log.i(TAG,"~~~~~~~~~~~~~onDestroy");
    }

    public static void closeApp() {
        for (Activity activity : mActivityList) {
            if (activity != null && !activity.isFinishing()) {
                activity.finish();
            }
        }
        System.exit(0);
    }


    public void closeActivity() {
        finish();
//        overridePendingTransition(R.anim.scale_in, R.anim.slide_bottom_out);
    }

    @Override
    public void onBackPressed() {
        closeActivity();
    }
}
