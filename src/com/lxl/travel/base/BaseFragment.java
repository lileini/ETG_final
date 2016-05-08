package com.lxl.travel.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

//总Fragment
public abstract class BaseFragment extends Fragment {
    public  String TAG;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
        Log.i(TAG,"~~~~~ oncreate");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG,"~~~~~ onPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"~~~~~ onDestroy");
    }
}
