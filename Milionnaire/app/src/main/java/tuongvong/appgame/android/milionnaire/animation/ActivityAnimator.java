package tuongvong.appgame.android.milionnaire.animation;


import android.app.Activity;

import tuongvong.appgame.android.milionnaire.R;

public class ActivityAnimator
{
	
	public void fadeAnimation(Activity a)
	{
		a.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	}
	
	public void disappearTopLeftAnimation(Activity a)
	{
		a.overridePendingTransition(R.anim.disappear_top_left_in, R.anim.disappear_top_left_out);
	}
	
	public void appearTopLeftAnimation(Activity a)
	{
		a.overridePendingTransition(R.anim.appear_top_left_in, R.anim.appear_top_left_out);
	}
	
	public void disappearBottomRightAnimation(Activity a)
	{
		a.overridePendingTransition(R.anim.disappear_bottom_right_in, R.anim.disappear_bottom_right_out);
	}
	
	public void appearBottomRightAnimation(Activity a)
	{
		a.overridePendingTransition(R.anim.appear_bottom_right_in, R.anim.appear_bottom_right_out);
	}
	
	public void unzoomAnimation(Activity a)
	{
		a.overridePendingTransition(R.anim.unzoom_in, R.anim.unzoom_out);
	}
    

}
