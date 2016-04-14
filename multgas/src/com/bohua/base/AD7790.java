package com.bohua.base;

public class AD7790 {
	
	public static final String ReadAd7790Path = "/sys/class/mult_gas/mult_gas/get_data";
	public static final String WriteAd7790Path = "/sys/class/mult_gas/mult_gas/get_data";

	/**
	 * ��AD7790������ֵת��Ϊ��ѹֵ = (AI+)-(AI-)
	 * */
	public  float ConvertNumberToVoltage(int number){
		int volteage_val=0;
		float c=0; 
		volteage_val=(int) ((number-32768)*2.5);
		c=(float)volteage_val/32768;
		return c;
	} 
	/**
	 * ���AD7790 read class �ľ��Ե�ַ
	 * */
	public String GetReadAd7790Path()
	{
		return ReadAd7790Path;
	}
	/**
	 * ���AD7790 write class �ľ��Ե�ַ
	 **/
	public String GetWriteAd7790Path()
	{
		
		return WriteAd7790Path;
	}
}
