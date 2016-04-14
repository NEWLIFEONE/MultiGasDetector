package com.bohua.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.TextView;


public class Battery {

	
	static String tag = "Battery";
	private static void debug(String string){if(Debug.BatteryDebug() == 1)Debug.debugi(tag,string);}
	
	int intLevel;
	int intScale;
	TextView text_View;
	String battery_level = null;
	/*false : ��ʾû��ע���
	  true  : ��ʾע���*/
	boolean register_state = false;
	BroadcastReceiver mBatInfoReceive = new BroadcastReceiver()
	{
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();

			if(Intent.ACTION_BATTERY_CHANGED.equals(action))
			{
				intLevel = intent.getIntExtra("level", 0);
				intScale = intent.getIntExtra("scale", 10);
				text_View.setText("����"+String.valueOf(intLevel*100/intScale)+"%");
				battery_level = String.valueOf(intLevel*100/intScale);
			}
		}
	};
	
	/**
	 * ע���ص����仯�㲥����
	 * ���ҽ�һ��TextView��������,����ص������ͱ仯��ʱ��TextView���Զ�ˢ��
	 * **/
	public void reciveBatteryLevel(Context context , TextView textView) 
	{
		debug("registerReceiver ACTION_BATTERY_CHANGED");
		context.registerReceiver(mBatInfoReceive,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
		text_View = textView;
		register_state = true;
	}
	/**
	 * ע����ص����仯�㲥����
	 * ����ע���˵�ص����仯�����ע��
	 * **/
	public void unregisterBatteryReciver(Context context)
	{
		debug("unregisterBatteryReciver ACTION_BATTERY_CHANGED");
		if(register_state == true)
		{
			debug("ע����ص����仯�㲥����");
			context.unregisterReceiver(mBatInfoReceive);
		}
		else
		{
			debug("ע����ص����仯�㲥����ʧ�ܣ�û��ע����ù㲥");
		}
		
	}
	public String getBatteryLevel()
	{
		return battery_level;
	}
	
	
}
