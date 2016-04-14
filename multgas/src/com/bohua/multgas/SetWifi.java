package com.bohua.multgas;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.bohua.base.Battery;
import com.bohua.base.Date;
import com.bohua.base.Debug;
import com.bohua.base.Time;
import com.bohua.base.WifiControl;
import com.bohua.graphics.ButtonImageReplace;
import com.bohua.graphics.ScreenHandler;
import com.bohua.graphics.WifiListAdapter;
import com.qinxiaoyu.multgas.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class SetWifi extends Activity{
	
	private List<ScanResult> list = null; //�����������WIFI�ȵ�
	private String password; //���WIFI�ȵ������
	private String SSID; //���WIFI�ȵ��SSID
	private int wifiType;  //���wifi������
	
	private WifiConfiguration wifiConfiguration; //������úõ�WIFI�ȵ�
	private WifiManager wifiManager; //Wifi��������Ҫ��
	private WifiControl wifiControl; //�Լ����ƺõ�wifi����
	
	private ListView listViewWifiList ; //��Ҫ��ʱ���µ����listview
	private WifiListAdapter wifiListAdapter; //List��Ҫ��������
	private AlertDialog alertDialog; //listview�ĵ����˵�
	private View myLoginView;       //alertDialog���õ��Ľ���
	private EditText alertDialogEditTextPassword; //alertDialog��������������ı���
	private TextView alertDialogTextViewSSID;  //aiertDialog������ʾSSID���ı���
	
	private Handler handler;
	private Runnable updataWifiListViewRunnable; //���ڸ����б���߳�
	//private Runnable updataWifiListViewRunnableclock; //������ˢ��ListView
	
	private SharedPreferences sharedPreferences;//���ڴ�ŵ�¼�ȵ���Ϣ��ʵ���Զ���¼
	private SharedPreferences.Editor editor;
	private boolean ifWifiAutoConnect;  //���ڱ�ʾ�Ƿ����Զ�����
	
	private Timer timer;
	private Timer upDataWifiListTimer;
	
	static String tag = "SetWifi";
	private static void debug(String string){if(Debug.SetWifiDebug() == 1) Debug.debugi(tag,string);}
	
	Battery battery = new Battery();
	Context appContext;
	
	TextView SystemTime;
	Date date = new Date();
	Time time = new Time();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ScreenHandler.setFullScreenAndPortrait(this);
		setContentView(R.layout.set_wifi);
		appContext = getApplicationContext();
		
		
		setAllViews();
		init();
	}
	private void setAllViews()
	{
		TextView SystemBattery= (TextView)findViewById(R.id.set_wifi_testview_battery);
		battery.reciveBatteryLevel(appContext, SystemBattery);
		
		SystemTime = (TextView)findViewById(R.id.set_wifi_textView_time);
		SystemTime.setText(date.getSystemDateToStringWithoutS());
		time.reciveTimeTick(appContext , SystemTime);
	}
	private void init()
	{
		createHandler();
		wifiInit();
		handleReturnButton();
		handleOCWifiCheckBox();
		createAlertDialog();//����list��alertDialog�в���������Ӧ���ȳ�ʼ��
		createWifiList();
		handleOCWifiAutoConnectCheckbox();
		timerInit();
		//ClockUpdataWifiListView();
		
		
	}
	private void createHandler()
	{
		handler=new Handler()
		{
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch (msg.what) {
				case 0x123:
					
					
					break;

				default:
					break;
				}
			}
		};
	}
	private void handleReturnButton()
	{
		Button button = (Button) findViewById(R.id.set_wifi_button_return);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				battery.unregisterBatteryReciver(appContext);
				time.unregisterTimeTick(appContext);
				Intent intent = new Intent(SetWifi.this,GeneralSetting.class);
				startActivity(intent);
				finish();
			}
			
		});
		ButtonImageReplace.replaceReturnButtonImageOnTouch(button);
	}
	
	//��ʼ������WIFI�Ƿ�����Checkbox
	private void handleOCWifiCheckBox()
	{
		final CheckBox checkBox = (CheckBox) findViewById(R.id.set_wifi_checkbox_oc_wifi);
		if(wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED)
		{
			checkBox.setChecked(true);
			checkBox.setText("WIFI�ѿ���");
		}
		else
		{
			checkBox.setChecked(false);
			checkBox.setText("WIFI�Ѿ��ر�");
		}
		
		checkBox.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(checkBox.isChecked() == true)
				{
					try 
					{
						wifiControl.openWifi();
						checkBox.setText("WIFI�ѿ���");
					}
					catch (Exception e) 
					{
						e.printStackTrace();
					}
					updataWifiListViewRunnable=new Runnable() 
					{
						
						@Override
						public void run() 
						{
							debug("updataWifiListViewRunnable");
							while (wifiManager.getWifiState() != WifiManager.WIFI_STATE_ENABLED);
							debug("WIFI_STATE_ENABLED");
							try 
							{
								Thread.sleep(1000);
							} 
							catch (InterruptedException e) 
							{
								e.printStackTrace();
							}
							updateWifiList();
						}
					};
					handler.post(updataWifiListViewRunnable);
				}
				else if(checkBox.isChecked() == false)
				{
					try 
					{
						wifiControl.closeWifi();
						checkBox.setText("WIFI�ѹر�");
					} 
					catch (Exception e) 
					{
						// TODO: handle exception
						e.printStackTrace();
					}
					updateWifiList();	
				}
			}
		});
	}
	
	//��ʼ������WIFI�Ƿ��Զ����ӵ�Checkbox
	private void handleOCWifiAutoConnectCheckbox()
	{
		final CheckBox checkBox = (CheckBox) findViewById(R.id.set_wifi_checkbox_oc_auto_connect);
		sharedPreferences.getBoolean("ifWifiAutoConnect", false);
		if(ifWifiAutoConnect == true){
			checkBox.setChecked(true);
			checkBox.setText("�Զ����ӿ���");
		}else if(ifWifiAutoConnect == false){
			checkBox.setChecked(false);
			checkBox.setText("�Զ����ӹر�");
		}
		checkBox.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (checkBox.isChecked() == false)
				// �����Զ�����
				{
					editor.putBoolean("ifWifiAutoConnect", false);
					editor.commit();
					checkBox.setText("�Զ����ӹر�");

				} else if (checkBox.isChecked() == true) {
					editor.putBoolean("ifWifiAutoConnect", true);
					editor.commit();
					checkBox.setText("�Զ����ӿ���");
				}
			}
		});
	}
	private void autoConnectToWifi()
	{
		SSID=null;
		password = null;
		wifiType = 0;
		ifWifiAutoConnect=false;
		SSID = sharedPreferences.getString("SSID", null);
		password = sharedPreferences.getString("password", null);
		wifiType = sharedPreferences.getInt("wifiType", 0);
		ifWifiAutoConnect = sharedPreferences .getBoolean("ifWifiAutoConnect", false);
		debug("�����"+SSID);
		debug("�����"+ifWifiAutoConnect);
		if(ifWifiAutoConnect==false)
		{
			return;
		}
		else if(ifWifiAutoConnect == true)
		{
			wifiConfiguration=wifiControl.CreateWifiInfo(SSID, password, wifiType);
			wifiControl.connectToConfiguredWifi(wifiConfiguration);
			debug(SSID);
		}
	}
	private void wifiInit()
	{
		wifiManager =(WifiManager)SetWifi.this.getSystemService(Context.WIFI_SERVICE);
		wifiControl = new WifiControl(wifiManager);
		sharedPreferences = getSharedPreferences(SSID, MODE_PRIVATE);
		editor =sharedPreferences.edit();
		
		
	}
	private void createWifiList()
	{
		list = wifiManager.getScanResults();
		listViewWifiList = (ListView) findViewById(R.id.set_wifi_listview_wifi_list);
		wifiListAdapter = new WifiListAdapter(this, list);
		listViewWifiList.setAdapter(wifiListAdapter);
		listViewWifiList.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_DISABLED);
		createListOnClickListener();
	} 
	private void updateWifiList() 
	{
		debug("updateWifiList");
		wifiManager.startScan();
		list=wifiManager.getScanResults();
		for(int i=0;i<list.size();i++)
		{
			if(list.get(i).SSID.equals(""))
			{
				debug(" !!! remove the null SSID item");
				list.remove(i);
			}
		}
		wifiListAdapter.list=list;
		wifiListAdapter.notifyDataSetChanged();		
	}
	private void createListOnClickListener()
	{
		listViewWifiList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				ScanResult scanResult = list.get(position);
				SSID=scanResult.SSID;
				alertDialogTextViewSSID.setText(scanResult.SSID);
				if(scanResult.capabilities.contains("WPA")){
					wifiType = WifiControl.WIFICIPHER_WPA;
					alertDialog.show();
				}else if(scanResult.capabilities.contains("WEP")){
					wifiType = WifiControl.WIFICIPHER_WEP;
					alertDialog.show();
				}
			}
		});
	}
	private void createAlertDialog()
	{
		LayoutInflater layoutInflater = LayoutInflater.from(this);
		myLoginView = layoutInflater.inflate(R.layout.wifi_alert_dialog, null);
		alertDialogEditTextPassword=(EditText) myLoginView.findViewById(R.id.AlertDialogEditTextPassword);
		alertDialogTextViewSSID=(TextView) myLoginView.findViewById(R.id.AlertDialogTextViewSSID);
		alertDialog = new AlertDialog.Builder(SetWifi.this)
		.setTitle("�û���¼")
		.setIcon(R.drawable.ic_launcher)
		.setView(myLoginView)
		.setPositiveButton("��¼", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				password=alertDialogEditTextPassword.getText().toString();
				if(password!=null)
				{
					wifiConfiguration=wifiControl.CreateWifiInfo(SSID, password, wifiType);
					wifiControl.connectToConfiguredWifi(wifiConfiguration);
					
					editor.clear();
					editor.commit();
					editor.putString("SSID", SSID);
					editor.putString("password", password);
					editor.putInt("wifiType", wifiType);
					editor.commit();
				}
				
			}
		})
		.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		}).create();
	}
	/*private void ClockUpdataWifiListView()
	{
		updataWifiListViewRunnableclock = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(15000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					while (wifiManager.getWifiState() != WifiManager.WIFI_STATE_ENABLED)
						;
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e2) {
						// TODO �Զ����ɵ� catch ��
						e.printStackTrace();
					}
					updateWifiList();
				}
			}
		};
		handler.post(updataWifiListViewRunnableclock);
	}*/
	
	private void timerInit()
	{
		TimerTask task = new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				autoConnectToWifi();
			}
		};
		timer = new Timer();
		timer.schedule(task, 5000,20000);
		
		TimerTask upDataWifiListTask = new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message message = new Message();
				message.what = 1;
				upDataWifiListHandler.sendMessage(message);
			}
		};
		upDataWifiListTimer = new Timer();
		upDataWifiListTimer.schedule(upDataWifiListTask, 0,2000);
	}
	
	Handler upDataWifiListHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) 
			{
				updateWifiList();
			}
			super.handleMessage(msg);
		}
	};
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		handler.removeCallbacks(updataWifiListViewRunnable);
		//handler.removeCallbacks(updataWifiListViewRunnableclock);
		super.onDestroy();
		
	}

}