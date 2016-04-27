package com.lxl.travel.activity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import com.lxl.travel.activity.DragLayout.Status;
import com.lxl.travel.utils.LogUtil;

public class MyRelativeLayout extends RelativeLayout {
    private DragLayout dl;

    public MyRelativeLayout(Context context) {
        super(context);
    }

    public MyRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setDragLayout(DragLayout dl) {
        this.dl = dl;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
    	boolean b = (dl.getStatus() != Status.Close);
    	LogUtil.i("dl.getStatus() != Status.Close", b);
  //      if (dl.getStatus() != Status.Close) {
    	if(b){
            return true;
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	LogUtil.i("onTouchEvent", dl.getStatus());
        if (dl.getStatus() != Status.Close) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                dl.close();
            }
            return true;
        }
        return super.onTouchEvent(event);
    }

}
