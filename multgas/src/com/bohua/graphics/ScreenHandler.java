package com.bohua.graphics;

import android.content.pm.ActivityInfo;
import android.view.Window;
import android.view.WindowManager;



import android.app.Activity;;



public class ScreenHandler {

	public static void setFullScreenAndPortrait(Object object)
	{
		/**
		 * �����豸ȫ��
		 * */
		((Activity) object).getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);  
		((Activity) object).requestWindowFeature(Window.FEATURE_NO_TITLE);
		/**
		 * �����豸����
		 * */
		((Activity) object).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); 
		/**
		 * �����豸��Ļһֱ��
		 * */
		((Activity) object).getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}
}
