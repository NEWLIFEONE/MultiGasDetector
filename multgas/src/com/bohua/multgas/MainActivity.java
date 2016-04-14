package com.bohua.multgas;

import java.util.Timer;
import java.util.TimerTask;

import com.bohua.base.FileHandler;
import com.bohua.base.FileProperties;
import com.bohua.base.GlobalData;
import com.bohua.base.LogFile;
import com.bohua.graphics.ScreenHandler;
import com.qinxiaoyu.multgas.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class MainActivity extends Activity {

	private static final int TIME = 100;
	FileHandler file = new FileHandler();
	FileProperties file_cfg = new FileProperties();
	Context appContext;
	Timer timer = new Timer();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȫ����ʾ
		ScreenHandler.setFullScreenAndPortrait(this);
		setContentView(R.layout.activity_main);

		appInit();
		/*������ʱ����1�������¸�����*/
		timer.schedule(task, TIME,TIME);

	} 
	private void appInit(){
		appContext = getApplicationContext();
		
		/**
		 * ���������ļ��У����ļ��д������ٴδ���
		 * */
		file.creatAllAppFiles(getApplicationContext());
		/**
		 * ���������������Ϊ���򴴽����Ҹ�ֵΪ0
		 * */
		file_cfg.checkPropertiesKey(appContext);
		/**
		 * ���ı��л�ȡȫ�ֱ���
		 * */
		GlobalData.readGlobalDataFromFileProperties(appContext);
		
		Log.i("������","��ʼ�����");
		
		LogFile log =  new LogFile();
		log.writeLogToFile("MainActivity","onCreate","�������");
	}
	Handler main_wnd_handler = new Handler() 
	{  
	  	public void handleMessage(Message msg) 
        {  
            if (msg.what == 1) 
            { 
            	Log.i("������","��ʱ1����");
            	timer.cancel();
            	Intent intent = new Intent(MainActivity.this,GeneralSatuation.class);
            	startActivity(intent);
            	finish();
            }
            super.handleMessage(msg);  
        }
	};
	
	TimerTask task = new TimerTask() 
	{   
	    @Override  
	    public void run() 
	    {  
	        // ��Ҫ������:������Ϣ  
	        Message message = new Message();  
	        message.what = 1;  
	        main_wnd_handler.sendMessage(message);  
	    }  
	}; 
}
