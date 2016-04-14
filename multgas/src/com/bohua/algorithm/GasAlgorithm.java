package com.bohua.algorithm;

import java.io.File;

import android.content.Context;
import android.os.MultGas;

import com.bohua.base.AD7790;
import com.bohua.base.Debug;
import com.bohua.base.FileProperties;
import com.bohua.base.GlobalData;
import com.qinxiaoyu.multgas.R;

public class GasAlgorithm {
	static FileProperties file_cfg = new FileProperties();
	protected static final int _ret = -1;
	
	
	static String tag = "GasAlgorithm";
	private static void debug(String string)	{if(Debug.GasAlgorithmDebug() == 1)Debug.debugi(tag,string);}
	
	
	/*��ȡ���������Ũ��ֵ*/
	public static double getO2precent(Context context)
	{
		double O2_precent;
		int O2_zero_point=0;
		double O2_ratio = 0;
		
		if(detectFirmwareClassFileReady(context)==-1)
			return _ret;
		
		O2_zero_point = GlobalData.getO2ZeroPoint();
		O2_ratio = GlobalData.getO2Ratio();

		int ret=MultGas.readMultGasData(context.getResources().getString(R.string.ReadAd7790Path), 1);
		
		/*���������ٷְ�*/
		O2_precent = (1-(1/Math.pow(Math.E, ((ret - O2_zero_point)/(O2_ratio)))))*100;
//		debug("��������Ũ��ֵ = "+O2_precent);
		
//		/*�����¶Ȳ���*/
//		O2_precent = O2_precent/temperCompen.calculationCoefficient();
//		debug("main","�¶Ȳ������Kֵ= "+O2_precent);

		
		return O2_precent;
	}
	public static double getCOppm(Context context)
	{
		int co_zero_point=0;
		double co_ratio = 0;
		double co_ppm;
		
		if(detectFirmwareClassFileReady(context)==-1)
			return _ret;
		
		co_zero_point = GlobalData.getCOZeroPoint();
		co_ratio = GlobalData.getCORatio();
		
		AD7790 Ad7790Handler= new AD7790();
		int ret=MultGas.readMultGasData(Ad7790Handler.GetReadAd7790Path(), 1);
		
		/*һ����̼����PPM*/
		co_ppm = (double)(ret - co_zero_point)*co_ratio;
		
		/*һ����̼�¶Ȳ�����ʽ*/
		
		return co_ppm;
	}
	public static double getNO2ppm(Context context)
	{
		 double no2_ppm;
		 int no2_zero_point=0;
		 double no2_ratio = 0;
		
		 if(detectFirmwareClassFileReady(context)==-1)
			 return _ret;
	 
		 no2_zero_point = GlobalData.getNO2ZeroPoint();
		 no2_ratio = GlobalData.getNO2Ratio();
		 
		 debug("no2_zero_point = "+no2_zero_point);
		 debug("no2_ratio = "+no2_ratio);
		 
		 int ret=MultGas.readMultGasData(context.getResources().getString(R.string.ReadAd7790Path), 1);
		 
		 debug("ԭʼ����Ϊ��"+ret);
		 
		 no2_ppm = (double)(ret - no2_zero_point)*no2_ratio;
		 
		 debug("no2_ppm = "+no2_ppm);
		
		 return no2_ppm;
	 }
	public static double getCH4precent(Context context)
	{
		 double ch4_precent = 0;
		 int CH4_zero_point=0;
		 double CH4_ratio = 0;
		
		 if(detectFirmwareClassFileReady(context)==-1)
			return _ret;
		 
		 CH4_zero_point = GlobalData.getCH4ZeroPoint();
		 CH4_ratio = GlobalData.getCH4Ratio();
			
		 int ret=MultGas.readMultGasData(context.getResources().getString(R.string.ReadAd7790Path), 1);
		 ch4_precent = (double)(CH4_zero_point-ret)/CH4_ratio;
		 return ch4_precent;
	}
	
	
	
	/**
	 * ����������
	 * */	
	public static double setO2calibration(int o2_zeropoint , int O2_sensitivity,int temperature ,float gas_strength)
	{
		String zero_point_str;
		int o2_zero_point = 0;
		
		/**��������������*/
		debug("��������������");
//		file_cfg.writeProperties("O2ZeroPoint",o2_zeropoint+"");
		file_cfg.writeProperties("O2Sensitivity",O2_sensitivity+"");
		file_cfg.writeProperties("O2Temperature",temperature+"");
		
		/**���ļ��л�ȡ---���*/
		zero_point_str=file_cfg.readProperties("O2ZeroPoint");
		o2_zero_point=Integer.parseInt(zero_point_str);
		
		double o2_ratio = Math.log((double) (1/(1-gas_strength/100)));
		o2_ratio =  ((O2_sensitivity-o2_zero_point))/o2_ratio;
		debug("����Kֵ = "+o2_ratio);
		
		/**�������*/
		file_cfg.writeProperties("O2Ratio", o2_ratio+"");
		/**Ӧ�ò���������Ӧ��*/
		GlobalData.setO2Ratio(o2_ratio);
//		GlobalData.setO2ZeroPoint(o2_zeropoint);
		debug("ratio = "+o2_ratio);
	
		return o2_ratio;
	}
	public static double setCOcalibration(int co_zeropoint,int co_sensitivity,int temperature ,float gas_strength)
	{
		double ratio;
		String zero_point_str;
		int co_zero_point;
		/**����һ����̼(CO)������*/
		debug("����һ����̼(CO)������");
//		file_cfg.writeProperties("COZeroPoint",co_zeropoint+"");
		file_cfg.writeProperties("COSensitivity",co_sensitivity+"");
		file_cfg.writeProperties("COTemperature",temperature+"");
		/**���ļ��л�ȡ---���*/
		zero_point_str=file_cfg.readProperties("COZeroPoint");;
		co_zero_point=Integer.parseInt(zero_point_str);
		
		/**�������*/
		if(((co_sensitivity-co_zero_point)<=0))
			ratio=0;
		else
			ratio=(double)gas_strength/(co_sensitivity-co_zero_point);
		/**�������*/
		file_cfg.writeProperties("CORatio", ratio+"");
		/**Ӧ�ò���������Ӧ��*/
//		setCOZeroPoint(co_zeropoint);
		GlobalData.setCORatio(ratio);
		debug("ratio = "+ratio);

		return ratio;
	}
	public static double setNO2calibration(int no2_zeropoint,int no2_sensitivity,int temperature ,float gas_strength)
	{
		double ratio;
		String zero_point_str;
		int no2_zero_point;
		
		/**�����������(NO2)������*/
		debug("�����������(NO2)������");
//		file_cfg.writeProperties("NO2ZeroPoint",no2_zeropoint+"");
		file_cfg.writeProperties("NO2Sensitivity",no2_sensitivity+"");
		file_cfg.writeProperties("NO2Temperature",temperature+"");
		
		
		/**���ļ��л�ȡ---���*/
		zero_point_str=file_cfg.readProperties("NO2ZeroPoint");;
		no2_zero_point=Integer.parseInt(zero_point_str);
		
		/**�������*/
		if(((no2_sensitivity-no2_zero_point)<=0))
			ratio=0;
		else
			ratio=(double)gas_strength/(no2_sensitivity-no2_zero_point);
		
		/**�������*/
		file_cfg.writeProperties("NO2Ratio", ratio+"");
		/**Ӧ�ò���������Ӧ��*/
		GlobalData.setNO2Ratio(ratio); 
//		setNO2ZeroPoint(no2_zeropoint);
		debug("ratio = "+ratio);

		return ratio;
	}
	public static double setCH4calibration(int ch4_zeropoint,int ch4_sensitivity,int temperature ,float gas_strength)
	{
		double ratio;
		String zero_point_str;
		int ch4_zero_point;
		
		/**�������(CH4)������*/
		debug("�������(CH4)������");
//		file_cfg.writeProperties("CH4ZeroPoint",ch4_zeropoint+"");
		file_cfg.writeProperties("CH4Sensitivity",ch4_sensitivity+"");
		file_cfg.writeProperties("CH4Temperature",temperature+"");
		
		/**���ļ��л�ȡ---���*/
		zero_point_str=file_cfg.readProperties("CH4ZeroPoint");;
		ch4_zero_point=Integer.parseInt(zero_point_str);
		
		/**�������*/
		if(gas_strength == 0)
			ratio = 0;
		else
			ratio = (double)(ch4_zero_point - ch4_sensitivity)/gas_strength;
		
		/**�������*/
		file_cfg.writeProperties("CH4Ratio", ratio+"");
		/**Ӧ�ò���������Ӧ��*/
		GlobalData.setCH4Ratio(ratio);
//		setCH4ZeroPoint(ch4_zeropoint);
		debug("ratio = "+ratio);
		
		return ratio;
	}
	/**
	 * 
	 * **/
	public static void setGasCalibration(String gasType , int gas_sensitivity ,float gas_strength,int temperature)
	{
		debug("setGasCalibration----"+gasType+" in put ----");
		
		if("O2".equals(gasType))
		{
			setO2calibration(0,gas_sensitivity,temperature,gas_strength);
			debug("��������������");
		}
		else if("CH4".equals(gasType))
		{
			debug("��������������");
			setCH4calibration(0,gas_sensitivity,temperature,gas_strength);
		}
		else if("NO2".equals(gasType))
		{
			debug("������������������");
			setNO2calibration(0,gas_sensitivity,temperature,gas_strength);
		}
		else if("CO".equals(gasType))
		{
			debug("����һ����̼������");
			setCOcalibration(0,gas_sensitivity,temperature,gas_strength);
		}
	}
	
	
	public static int  setGasZeroPoint(Context context , String gasType ,int zeroPoint)
	{
		
		debug("setGasZeroPoint----"+gasType+" in put ----");
		
		if("O2".equals(gasType))
		{
			GlobalData.setO2ZeroPoint(zeroPoint);
			file_cfg.writeProperties("O2ZeroPoint", zeroPoint+"");
			debug("�����������");
		}
		else if("CH4".equals(gasType))
		{
			GlobalData.setCH4ZeroPoint(zeroPoint);
			file_cfg.writeProperties("CH4ZeroPoint", zeroPoint+"");
			debug("�����������");
		}
		else if("NO2".equals(gasType))
		{
			GlobalData.setNO2ZeroPoint(zeroPoint);
			file_cfg.writeProperties("NO2ZeroPoint", zeroPoint+"");
			debug("���������������");
		}
		else if("CO".equals(gasType))
		{
			GlobalData.setCOZeroPoint(zeroPoint);
			file_cfg.writeProperties("COZeroPoint", zeroPoint+"");
			debug("����һ����̼���");
		}
		return 0;
	}
	
	public  float ConvertNumberToVoltage(int number)
	{
		int volteage_val=0;
		float c=0; 
		if(number<=0) return 0;
		volteage_val=(int) ((number-32768)*2.5);
		c=(float)volteage_val/32768;
		return c;
	} 
	
	public int getGasData(Context context)
	{ 
		if(detectFirmwareClassFileReady(context)==-1)
			return -1;
		
		AD7790 Ad7790Handler= new AD7790();
		int ret=MultGas.readMultGasData(Ad7790Handler.GetReadAd7790Path(), 1);
		
		return ret;
	}
	
	public static int detectFirmwareClassFileReady(Context context)
	{
		File classFile = new File(context.getResources().getString(R.string.ReadAd7790Path));
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
