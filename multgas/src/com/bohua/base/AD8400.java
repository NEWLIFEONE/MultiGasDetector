package com.bohua.base;

public class AD8400 {
	
	public final String WriteAd8400Path = "/sys/class/mult_gas/mult_gas/dp_cmd";
	
	/**����AD8400��ֵ�µĵ���*/
	public  float ConvertNumberToOhm(int number)	{
		float ret = 0;
		ret=(float) (47+(number*4.3816));
		return ret;
	}
	
	
}
