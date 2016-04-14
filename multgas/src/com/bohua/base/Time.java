package com.bohua.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.TextView;

public class Time {

	
	static String tag = "Time";
	private static void debug(String string){if(Debug.TimeDebug() == 1)Debug.debugi(tag,string);}
	
	
	TextView text_View;
	
	/*false : ��ʾû��ע���
	  true  : ��ʾע���*/
	boolean register_state = false;
	
	BroadcastReceiver timeTickReceive = new BroadcastReceiver()
	{
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();

			if(Intent.ACTION_TIME_TICK.equals(action))
			{
				Date data = new Date();
				text_View.setText(data.getSystemDateToStringWithoutS());
				debug(data.getSystemDateToStringWithoutS());
			}
		}
	};
	/**
	 * ע���ص����仯�㲥����
	 * ���ҽ�һ��TextView��������,����ص������ͱ仯��ʱ��TextView���Զ�ˢ��
	 * **/
	public void reciveTimeTick(Context context , TextView textView) 
	{
		debug("registerReceiver ACTION_TIME_TICK");
		context.registerReceiver(timeTickReceive,new IntentFilter(Intent.ACTION_TIME_TICK));
		text_View = textView;
		register_state = true;
	}
	public void unregisterTimeTick(Context context)
	{
		debug("unregisterBatteryReciver ACTION_BATTERY_CHANGED");
		if(register_state == true)
		{
			debug("ע��ʱ��仯�㲥����");
			context.unregisterReceiver(timeTickReceive);
		}
		else 
		{
			debug("ע��ʱ��仯�㲥����ʧ�ܣ�û��ע����ù㲥");
		}
		
	}
	
}
