package com.lxl.travel.activityanim;

import android.app.Activity;

import com.lxl.trivel.R;

public class ActivityChangeAnim {
	//backModel: R.anim.slide_up_in, R.anim.slide_down_out
	//inModel: R.anim.zoom_enter,R.anim.zoom_exit
	/**由下往上*/
	public static final int MOVE_FROM_TOP_TO_BOTTOM = R.anim.slide_from_top_to_bottom;
	/**由上往下*/
	public static final int MOVE_FROM_BOTTOM_TO_TOP = R.anim.slide_from_bottom_to_top;
	/**不动*/
	public static final int NO_MOVE = R.anim.hold;
	
	
	/**
	 * 此方法只能在startActivity和finish方法之后调用。
	 * @param activity
	 * @param enterAnim for the incoming activity
	 * @param exitAnim for the outgoing activity
	 */
	public static void changeAnim(Activity activity, int enterAnim, int exitAnim){
		activity.overridePendingTransition(enterAnim, exitAnim);
	}

}
