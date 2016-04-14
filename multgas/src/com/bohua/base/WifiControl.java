package com.bohua.base;

import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;

public class WifiControl{
	
	private WifiManager myWifiManager;
	
	public static final int WIFICIPHER_NOPASS = 0;  //�����������Ϊ������
	
	public static final int WIFICIPHER_WEP = 1; //�����������ΪWEP
	
	public static final int WIFICIPHER_WPA = 2; //�����������ΪWPA
	
	/*����Ĺ��캯��*/
	
	public WifiControl(WifiManager wifiManager) {
		// TODO Auto-generated constructor stub
		this.myWifiManager=wifiManager;
	}
	/*���ڴ�WIFI*/
	public void openWifi(){
		if(!myWifiManager.isWifiEnabled() && 
				myWifiManager.getWifiState() != WifiManager.WIFI_STATE_ENABLING)
		{
			myWifiManager.setWifiEnabled(true);
		}
	}
	
	public void closeWifi(){
		if(myWifiManager.isWifiEnabled() &&
				myWifiManager.getWifiState() != WifiManager.WIFI_STATE_DISABLING)
		{
			myWifiManager.setWifiEnabled(false);
		}
	}
	/*�������������ÿ������ӵ�����
	 * SSID ΪWIFI �ȵ��SSID
	 * password Ϊ���ȵ������
	 * type ��ʾ���ܵ�����
	 * return ���úõ�wifi�ȵ�
	 * ע�� ������ֻ�����������ͺ�WPA����ͨ����֤*/
	public WifiConfiguration CreateWifiInfo(String SSID, String Password,
			int Type) {
		WifiConfiguration config = new WifiConfiguration();
		config.allowedAuthAlgorithms.clear();
		config.allowedGroupCiphers.clear();
		config.allowedKeyManagement.clear();
		config.allowedPairwiseCiphers.clear();
		config.allowedProtocols.clear();
		config.allowedGroupCiphers.clear();
		config.allowedPairwiseCiphers.clear();
		config.SSID = "\"" + SSID + "\"";
		if (Type == WIFICIPHER_NOPASS) {
			config.wepKeys[0] = "";
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
			config.wepTxKeyIndex = 0;
		}
		if (Type == WIFICIPHER_WEP) {
			config.hiddenSSID = true;
			config.wepKeys[0] = "\"" + Password + "\"";
			config.allowedAuthAlgorithms
					.set(WifiConfiguration.AuthAlgorithm.SHARED);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
			config.allowedGroupCiphers
					.set(WifiConfiguration.GroupCipher.WEP104);
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
			config.wepTxKeyIndex = 0;
		}
		if (Type == WIFICIPHER_WPA) {
			Log.v("my_tag", "��ʼ����WPA�ȵ�");
			config.preSharedKey = "\"" + Password + "\"";
			config.hiddenSSID = true;
			config.status = WifiConfiguration.Status.ENABLED;
			config.allowedAuthAlgorithms
					.set(WifiConfiguration.AuthAlgorithm.OPEN);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
			config.allowedPairwiseCiphers
					.set(WifiConfiguration.PairwiseCipher.TKIP);

			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
			config.allowedPairwiseCiphers
					.set(WifiConfiguration.PairwiseCipher.CCMP);
			config.allowedPairwiseCiphers
					.set(WifiConfiguration.PairwiseCipher.TKIP);
			config.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
			config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);

		}
		return config;
	}
	
	/*�÷������������Ѿ����úõ�WIFI�ȵ�
	 * wifiConfiguration �����úõ�WIFI�ȵ�
	 * return �Ƿ����ӳɹ�*/

	public boolean connectToConfiguredWifi(WifiConfiguration wifiConfiguration) {
		int wcgID = myWifiManager.addNetwork(wifiConfiguration);
		boolean netFlag = myWifiManager.enableNetwork(wcgID, false);
		return netFlag;
	}
}