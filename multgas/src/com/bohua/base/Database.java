/**
 * ��ʱ���ѯ
 * select * from gas_data where system_date>='2014-07-21 06:01:34' and system_date<='2014-07-21 06:01:37';
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * **/

package com.bohua.base;

import android.database.CharArrayBuffer;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class Database {
	
	
	private static final String tag = "Database";
	private static void debug(String string)	{if(Debug.DatabaseDebug()== 1)Debug.debugi(tag,string);}

	/**��һ�����в������ݣ�����ñ����ڣ��򴴽���*/
	public void insertData(String Database_name ,String key1 , String key2 ,String key3)
	{
		SQLiteDatabase db;
		db = SQLiteDatabase.openOrCreateDatabase("/sdcard/MultGasDetecter/database/"+Database_name,null);

		try
		{
			db.execSQL("insert into news_inf values(null, ? , ? , ?)",new String[]{key1 , key2 , key3});
		}
		catch(SQLiteException e2)
		{	
			LogFile log =  new LogFile();
			log.writeLogToFile("Database","insertData","���ݿ�������ݴ���һ���ǲ��������ݿ⣬�����Ҳ�����");
			
			db.execSQL("create table news_inf(_id integer primary key autoincrement,"
			  +" news_key1 varchar(30),"
			  +" news_key2 varchar(20),"
			  +" news_key3 varchar(20))"); 
			db.execSQL("insert into news_inf values(null, ? , ? , ?)",new String[]{key1 , key2 , key3});
		}

		debug("insertData");		
		if((db != null)&&db.isOpen())
		{
			db.close();
		}
	}
	public void queryData(String Database_name)
	{
		SQLiteDatabase db;
		db = SQLiteDatabase.openOrCreateDatabase("/sdcard/MultGasDetecter/database/"+Database_name,null);
		Cursor result = db.rawQuery("select * from gas_data;", null);
		result.moveToFirst();
		while(!result.isAfterLast())
		{
			int id = result.getInt(0);
			String gas_type =  result.getString(1);
			int gas_original_value = result.getInt(2); 
			debug("id = "+id);
			debug("gas_type="+gas_type);
			debug("gas_original_value="+gas_original_value);
			result.moveToNext();
		}
		result.close();
	}
	/**��һ�����в�ѯ���ݿ�
	 * ��ѯĳһ���е�֪
	 * **/
	public void queryData(String dataBaseName , String table , String column)
	{
		SQLiteDatabase db;
		db = SQLiteDatabase.openOrCreateDatabase("/sdcard/MultGasDetecter/database/"+dataBaseName,null);
		Cursor result = db.rawQuery("select "+column+" from "+table+";", null);
		result.moveToFirst();
		
		try{
			while(!result.isAfterLast())
			{
				int id = result.getInt(0);
//				String gas_type =  result.getString(1);
//				int gas_original_value = result.getInt(2); 
//				double gas_valtage_value = result.getDouble(3);
//				double gas_actual_value = result.getDouble(4);
//				int templeture = result.getInt(5);
				debug("id = "+id);
//				debug("gas_type="+gas_type);
//				debug("gas_original_value="+gas_original_value);
//				debug("gas_valtage_value="+gas_valtage_value);
//				debug("gas_actual_value="+gas_actual_value);
//				debug("templeture="+templeture);
				result.moveToNext();
			}
			result.close();
		}
		catch(SQLiteException e2)
		{
			
			e2.printStackTrace();  	
		}

	}
	
	/**��һ�����в������ݣ�����ñ����ڣ��򴴽���*/  
	public void insertData(String databaseFile ,String table,String gas_type , int gas_original_value  
	,double gas_valtage_value,double gas_actual_value,int templeture ,String system_date)  
	{  
		SQLiteDatabase db;  
		db = SQLiteDatabase.openOrCreateDatabase("/sdcard/MultGasDetecter/database/"+databaseFile,null);  
		  
		  
		try  
		{  
			db.execSQL("insert into "+table+" values(null, ? , ? , ? , ? , ? , ?)"  
			,new String[]{gas_type , gas_original_value+"",gas_valtage_value+"",gas_actual_value+""  
			,templeture+"",system_date+""});  
		}  
		catch(SQLiteException e2)  
		{   
			LogFile log =  new LogFile();
			log.writeLogToFile("Database","insertData","���ݿ�������ݴ���һ���ǲ��������ݿ⣬�����Ҳ�����"
					+ "���õĵط���Database��111������");
			
			db.execSQL("create table "+table+"(id integer primary key autoincrement,"  
			 +" gas_type varchar(30),"  
			 +" gas_original_value integer,"  
			 +" gas_valtage_value double precision,"  
			 +" gas_actual_value double precision,"  
			 +" templeture integer,"  
			 +" system_date varchar(30))");   
			db.execSQL("insert into "+table+" values(null, ? , ? , ? , ? , ? , ?)"  
			,new String[]{gas_type , gas_original_value+"",gas_valtage_value+"",gas_actual_value+""  
			,templeture+"",system_date+""});  
			e2.printStackTrace();  
		}  
		debug("insertData");  
		if((db != null)&&db.isOpen())  
		{  
			db.close();  
		}  
	}  
	
	/**
	 * ��ѯ���ݿ��е�ĳ�ű��е�ĳ������
	 * File:
	 * 		���ݿ��ļ����ƣ�·��ͳһ���浽/sdcard/MultGasDetecter/database/��
	 * table:
	 * 		Ҫ��ѯ�����ݿ����
	 * columnName:
	 * 		Ҫ��ѯ�����ݿ������
	 * 
	 * ������Ϊ��
	 * 		id
	 * 		gas_type
	 * 		gas_original_value
	 * 		gas_valtage_value
	 * 		gas_actual_value
	 * 		templeture
	 * 		system_dat
	 * 
	 * eg::
	 * 		Database database = new Database();
	 * 		int len = database.queryDataByColumnName("test.db3","gas_data","gas_type").length;
	 *		debug("len = "+len);
	 *		String[] string = new String[len];
	 *		string = database.queryDataByColumnName("test.db3","gas_data","gas_type");
	 *	
	 *		for(int i = 0 ; i< len-1 ; i++)
	 *		{
	 *			debug(string[i]);
	 *		}
	 * 
	 * */
	public String[] queryData(String file,String table,String columnName,String begin_time ,String end_time)
	{
		String sqlQuery ;
		int m = 0;
		CharArrayBuffer array = new CharArrayBuffer(128);
		SQLiteDatabase db; 
		db = SQLiteDatabase.openOrCreateDatabase("/sdcard/MultGasDetecter/database/"+file,null);
		if((begin_time!=null)&&(end_time!=null))
		{
			sqlQuery = "select * from "+table+" where system_date>='"+begin_time+"' and system_date<='"+end_time+"';";
		}
		else
			sqlQuery = "select * from "+table+";";
		
		Cursor result = db.rawQuery(sqlQuery, null);
		debug("getColumnCount = "+result.getCount());
		String[] string = new String[result.getCount()];
		result.moveToFirst();
		
		if(columnName != null)
		{
			while(!result.isAfterLast())
			{ 
				try
				{
	//				debug("copyStringToBuffer");
					
					string[m]="";
					if(columnName.indexOf("id")!= -1)
					{
						result.copyStringToBuffer(0, array);
						string[m]+=getStringFromCharArrayBuffer(array);
					}
	
					if(columnName.indexOf("gas_type")!= -1)
					{
						result.copyStringToBuffer(1, array); 
						string[m]+=getStringFromCharArrayBuffer(array);
					}
	
					if(columnName.indexOf("gas_original_value")!= -1)
					{
						result.copyStringToBuffer(2, array);
						string[m]+=getStringFromCharArrayBuffer(array);
					}
	
					if(columnName.indexOf("gas_valtage_value")!= -1)
					{
						result.copyStringToBuffer(3, array);
						string[m]+=getStringFromCharArrayBuffer(array);
					}
	
					if(columnName.indexOf("gas_actual_value")!= -1)
					{
						result.copyStringToBuffer(4, array);
						string[m]+=getStringFromCharArrayBuffer(array);
					}
	
					if(columnName.indexOf("templeture")!= -1)
					{
						result.copyStringToBuffer(5, array);
						string[m]+=getStringFromCharArrayBuffer(array);
					}
	
					if(columnName.indexOf("system_dat")!= -1)
					{
						result.copyStringToBuffer(6, array);
						string[m]+=getStringFromCharArrayBuffer(array);
					}
					
//					debug(string[m]);
					m++;
					result.moveToNext();
				}
				catch(SQLiteException e)
				{
					LogFile log =  new LogFile();
					log.writeLogToFile("Database","queryData","��ѯ���ݿ⣬���ҽ����������һ���ַ���������"
							+"\n\t���ݿ��ļ�Ϊ��"+sqlQuery
							+"\n\t\t����Ϊ��"+table
							+"\n\t\t����Ϊ��"+columnName
							+"\n\t\t��ʼʱ�䣺"+begin_time
							+"\n\t\t����ʱ��"+end_time);
					
					debug("copy string error");
					e.printStackTrace(); 
				}	
			}
		}
		else
			debug("û������columnName");
		
		result.close();
		db.close();
		return string; 
	}
	/**
	 * ����ʱ���ѯ���ݿ��е��������е�����
	 * 
	 * 
	 * */
	public String[] queryDataByDate(String file,String table,String begin_time ,String end_time)
	{
		return queryData( file, table, "id gas_type gas_original_value gas_valtage_value gas_actual_value templeture system_dat", begin_time , end_time);
	}
	public String[] queryDataByColumnName(String file,String table,String columnName)
	{
		return queryData( file, table, "columnName", null , null);
	}
	
	private String getStringFromCharArrayBuffer(CharArrayBuffer array)
	{
		int i;
		String string = null;
//		debug("array.sizeCopied = "+array.sizeCopied);		
		string = "";
		for(i=0;i<array.sizeCopied;i++)
		{
//			debug(""+(char)array.data[i]);
			string += array.data[i]+"";
		}
		string += "  ";
		return string;
	}
	
	/**
	 * ɾ��ʱ���������ݿ��е�����
	 * ����ֵ��
	 * 		-1	��������
	 * 		0	ɾ���ɹ�
	 * 		-2	ɾ��ʧ�ܣ��׳��쳣
	 * 
	 * eg:	database.deleteDataByDate("xxx.db3","gas_data","2014-07-21   11:36:57","2014-07-21   11:39:07");
	 * **/
	public int deleteDataByDate(String file,String table ,String begin_time ,String end_time)
	{
		if((begin_time!=null)&&(end_time!=null))
		{
			SQLiteDatabase db;
			db = SQLiteDatabase.openOrCreateDatabase("/sdcard/MultGasDetecter/database/"+file,null);
			
			try
			{
				db.execSQL("delete from "+table+" where system_date>='"+begin_time+"' and system_date<='"+end_time+"';");
			}
			catch(SQLiteException e)
			{	
				LogFile log =  new LogFile();
				log.writeLogToFile("Database","deleteDataByDate","ɾ�����ݿ�������"
						+"\n\t���ݿ��ļ�Ϊ��"+file
						+"\n\t\t����Ϊ��"+table
						+"\n\t\t��ʼʱ�䣺"+begin_time
						+"\n\t\t����ʱ�䣺"+end_time);
				
				
				debug("delete database error");
				e.printStackTrace();	
				return -2;
			}
			db.close();
			return 0;
		}
		else
			return -1;
	}
	/**
	 * ɾ��ĳʱ���Ժ����ݿ��е�����
	 * ����ֵ��
	 * 		-1	��������
	 * 		0	ɾ���ɹ�
	 * 		-2	ɾ��ʧ�ܣ��׳��쳣
	 * 
	 * eg:	database.deleteDataAfterDate("xxx.db3","gas_data","2014-07-21   11:39:38");
	 * **/
	public int deleteDataAfterDate(String file,String table ,String begin_time)
	{
		if(begin_time == null)
			return -1;
		else
		{
			SQLiteDatabase db;
			db = SQLiteDatabase.openOrCreateDatabase("/sdcard/MultGasDetecter/database/"+file,null);
			
			try
			{
				db.execSQL("delete from "+table+" where system_date>'"+begin_time+"';");
			}
			catch(SQLiteException e)
			{	
				LogFile log =  new LogFile();
				log.writeLogToFile("Database","deleteDataAfterDate","ɾ�����ݿ�������"
						+"\n\t���ݿ��ļ�Ϊ��"+file
						+"\n\t\t����Ϊ��"+table
						+"\n\t\t��ʼʱ�䣺"+begin_time);
				
				debug("delete database error");
				e.printStackTrace();	
				return -2;
			}
			db.close();
			return 0;
		}
	}
	/**
	 * ɾ��ĳʱ����ǰ���ݿ��е�����
	 * ����ֵ��
	 * 		-1	��������
	 * 		0	ɾ���ɹ�
	 * 		-2	ɾ��ʧ�ܣ��׳��쳣
	 * 
	 * eg:	database.deleteDataBeforeDate("xxx.db3","gas_data","2014-07-21   11:39:10");
	 * **/
	public int deleteDataBeforeDate(String file,String table ,String begin_time)
	{
		if(begin_time == null)
			return -1;
		else
		{
			SQLiteDatabase db;
			db = SQLiteDatabase.openOrCreateDatabase("/sdcard/MultGasDetecter/database/"+file,null);
			
			try
			{
				db.execSQL("delete from "+table+" where system_date<'"+begin_time+"';");
			}
			catch(SQLiteException e)
			{	
				LogFile log =  new LogFile();
				log.writeLogToFile("Database","deleteDataBeforeDate","\nɾ�����ݿ�������"
						+"\n\t���ݿ��ļ�Ϊ��"+file
						+"\n\t\t����Ϊ��"+table
						+"\n\t\t��ʼʱ�䣺"+begin_time);
				
				debug("delete database error");
				e.printStackTrace();	
				return -2;
			}
			db.close();
			return 0;
		}
	}
	
	
	
	
	
	
	
	
}
