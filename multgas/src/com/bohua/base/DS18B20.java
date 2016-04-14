package com.bohua.base;

import java.io.File;

import android.os.MultGas;

public class DS18B20 {
	private static final String ReadDS18B20Path = "/sys/class/mult_gas/mult_gas/set_cmd";

	/**
	 * ���DS18B20��class �ľ��Ե�ַ
	 **/
	public String getDS18B20Path()
	{
		return ReadDS18B20Path;
	}
	/**
	 * ��õ�ǰDS18B20���¶�
	 **/
	public static int getTemperature()
	{
		int ret;
		if(detectFirmwareClassFileReady() == -1)
			return -255;
		ret = MultGas.readMultGasData(ReadDS18B20Path, 1);
		return ret;
	}
	public static int detectFirmwareClassFileReady()
	{
		File classFile = new File(ReadDS18B20Path);
		if(classFile.exists())
		{
			return 0;
		}
		else
		{
//			Log.w("GasAlgorithm","δ��firmwork��ӿ��ļ�");
			return -1;
		}
	}
	 
}
