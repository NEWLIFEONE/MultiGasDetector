package com.bohua.base;

import com.qinxiaoyu.multgas.R;

import android.content.Context;

public class GlobalData {
	
	static String tag = "GlobalData";
	private static void debug(String string){if(Debug.GlobalDataDebug() == 1) Debug.debugi(tag,string);}
	
	static int 		O2ZeroPoint = 0;
	static double 	O2Ratio = 0;
	static int 		COZeroPoint = 0;
	static double 	CORatio = 0;
	static int 		NO2ZeroPoint=0;
	static double 	NO2Ratio = 0;
	static int 		CH4ZeroPoint=0;
	static double 	CH4Ratio = 0;
	
	/**
	 * ����ȫ�ֲ��� �ķ���
	 * */
	public static int getO2ZeroPoint()				{return  O2ZeroPoint;}
	public static void setO2ZeroPoint(int zeroPoint){O2ZeroPoint = zeroPoint;}
	public static double getO2Ratio()				{return  O2Ratio;}
	public static void setO2Ratio(double ratio)		{O2Ratio = ratio;}
	

	/**
	 * һ����̼ȫ�ֲ��� �ķ���
	 * */
	public static int getCOZeroPoint()				{return  COZeroPoint;}
	public static void setCOZeroPoint(int zeroPoint){COZeroPoint = zeroPoint;}
	public static double getCORatio()				{return  CORatio;}
	public static void setCORatio(double ratio)		{CORatio = ratio;}

	/**
	 * ��������ȫ�ֲ��� �ķ���
	 * */
	public static int getNO2ZeroPoint()					{return  NO2ZeroPoint;}
	public static void setNO2ZeroPoint(int zeroPoint)	{NO2ZeroPoint = zeroPoint;}
	public static double getNO2Ratio()					{return  NO2Ratio;}
	public static void setNO2Ratio(double ratio)		{NO2Ratio = ratio;}
	
	/**
	 * ����ȫ�ֲ��� �ķ���
	 * */
	public static int getCH4ZeroPoint()					{return  CH4ZeroPoint;}
	public static void setCH4ZeroPoint(int zeroPoint)	{CH4ZeroPoint = zeroPoint;}
	public static double getCH4Ratio()					{return  CH4Ratio;}
	public static void setCH4Ratio(double ratio)		{CH4Ratio = ratio;}
	
	
	
	/****************************************************************************************************/
	/****************************************************************************************************/
	static double 	CH4AlarmPoint = 0;
	static double 	COAlarmPoint = 0;
	static double 	O2AlarmPoint = 0;
	static double 	NO2AlarmPoint = 0;
	
	/**
	 * ��ȡ/�������屨����ķ���
	 * */
	public static void setCH4AlarmPoint(double alarmPoint)	
	{
		CH4AlarmPoint = alarmPoint;
		FileProperties file_cfg = new FileProperties();
		file_cfg.writeProperties("CH4AlarmPoint", CH4AlarmPoint+"");
	}
	public static void setCOAlarmPoint(double alarmPoint)	
	{
		COAlarmPoint = alarmPoint;
		FileProperties file_cfg = new FileProperties();
		file_cfg.writeProperties("COAlarmPoint", COAlarmPoint+"");
	}
	public static void setO2AlarmPoint(double alarmPoint)	
	{
		O2AlarmPoint = alarmPoint;
		FileProperties file_cfg = new FileProperties();
		file_cfg.writeProperties("O2AlarmPoint", O2AlarmPoint+"");
	}
	public static void setNO2AlarmPoint(double alarmPoint)	
	{
		NO2AlarmPoint = alarmPoint;
		FileProperties file_cfg = new FileProperties();
		file_cfg.writeProperties("NO2AlarmPoint", NO2AlarmPoint+"");
	}
	
	public static double getCH4AlarmPoint()	{return CH4AlarmPoint;}
	public static double getCOAlarmPoint()	{return COAlarmPoint;}
	public static double getO2AlarmPoint()	{return O2AlarmPoint;}
	public static double getNO2AlarmPoint()	{return NO2AlarmPoint;}
	
	
	/****************************************************************************************************/
	/****************************************************************************************************/
	static EnumDataSaveFreq DataSaveFreqence ;
	
	public static void setDataSaveFreqence(EnumDataSaveFreq selectFreq)	
	{
		DataSaveFreqence = selectFreq;
		FileProperties file_cfg = new FileProperties();
		file_cfg.writeProperties("DataSaveFreqence", DataSaveFreqence+"");
	}
	public static EnumDataSaveFreq getDataSaveFreqence()	{return DataSaveFreqence;}
	
	
	/****************************************************************************************************/
	/****************************************************************************************************/
	/***
	 *ϵͳ״̬ȫ�ֱ��� 
	 ***/
	public static EnumState deviceState;
	
	public static void setDeviceState(EnumState state)	
	{
		deviceState = state;
	}
	public static EnumState getDeviceState()	{return deviceState ;}
	
	

	/****************************************************************************************************/
	/****************************************************************************************************/
	/**
	 * ֻ��ϵͳ������ʱ�̵��øú���
	 * �������������仯��ʱ�̣���ʱ��ֵ��ȫ�ֵı����������øú���
	 * **/
	public static void readGlobalDataFromFileProperties(Context context)  
	{
		FileProperties file_cfg = new FileProperties();
		
		/*���ļ��л�ȡ---�������*/
		debug("���ļ��л�ȡ---�������");
		O2ZeroPoint=Integer.parseInt(file_cfg.readProperties(context.getResources().getString(R.string.SenserParamO2ZeroPiont)));
		debug("���Ϊ"+O2ZeroPoint);
		
		/*���ļ��л�ȡ---����б��*/
		debug("���ļ��л�ȡ---����б��");
		O2Ratio = Double.parseDouble(file_cfg.readProperties(context.getResources().getString(R.string.SenserParamO2Ratio)));
		debug("б��Ϊ"+O2Ratio);
		
		/*���ļ��л�ȡ---һ����̼���*/
		debug("���ļ��л�ȡ---һ����̼���");
		COZeroPoint=Integer.parseInt(file_cfg.readProperties("COZeroPoint"));
		debug("���Ϊ"+COZeroPoint);
		
		/*���ļ��л�ȡ---һ����̼б��*/
		debug("���ļ��л�ȡ---һ����̼б��");
		CORatio =  Double.parseDouble(file_cfg.readProperties("CORatio"));
		debug("б��Ϊ"+CORatio);
		
		/*���ļ��л�ȡ---�����������*/
		debug("���ļ��л�ȡ---�����������");
		NO2ZeroPoint=Integer.parseInt(file_cfg.readProperties("NO2ZeroPoint"));
		debug("���Ϊ"+NO2ZeroPoint);
		
		/*���ļ��л�ȡ---��������б��*/
		debug("���ļ��л�ȡ---��������б��");
		NO2Ratio =  Double.parseDouble(file_cfg.readProperties("NO2Ratio"));
		debug("б��Ϊ"+NO2Ratio);
		
		/*���ļ��л�ȡ---�������*/
		debug("���ļ��л�ȡ---�������");
		CH4ZeroPoint=Integer.parseInt(file_cfg.readProperties("CH4ZeroPoint"));
		debug("���Ϊ"+CH4ZeroPoint);
		
		/*���ļ��л�ȡ---����б��*/
		debug("���ļ��л�ȡ---����б��");
		CH4Ratio = Double.parseDouble(file_cfg.readProperties("CH4Ratio"));
		debug("б��Ϊ"+CH4Ratio);
		 
		/**
		 * ��ȡ������
		 * */
		/**���ļ��л�ȡ---���鱨����*/
		debug("���ļ��л�ȡ---���鱨����");
		CH4AlarmPoint = Double.parseDouble(file_cfg.readProperties("CH4AlarmPoint"));
		debug("������"+CH4AlarmPoint);
		
		/**���ļ��л�ȡ---һ����̼������*/
		debug("���ļ��л�ȡ---һ����̼������");
		COAlarmPoint = Double.parseDouble(file_cfg.readProperties("COAlarmPoint"));
		debug("������"+COAlarmPoint);
		
		/**���ļ��л�ȡ---��������������*/
		debug("���ļ��л�ȡ---��������������");
		NO2AlarmPoint = Double.parseDouble(file_cfg.readProperties("NO2AlarmPoint"));
		debug("������"+NO2AlarmPoint);
		
		/**���ļ��л�ȡ---����������*/
		debug("���ļ��л�ȡ---����������");
		O2AlarmPoint = Double.parseDouble(file_cfg.readProperties("O2AlarmPoint"));
		debug("������"+O2AlarmPoint);
		
		/**���ļ��л�ȡ---���ݱ���Ƶ��*/
		debug("�ļ��л�ȡ---���ݱ���Ƶ��");
		DataSaveFreqence = EnumDataSaveFreq.valueOf(file_cfg.readProperties("DataSaveFreqence"));
		
		debug("���ݱ���Ƶ��"+DataSaveFreqence);
		

	}
	
	
}
