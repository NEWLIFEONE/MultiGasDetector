package com.bohua.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import com.qinxiaoyu.multgas.R;

import android.content.Context;

public class FileProperties {

	/**�����ļ���·��**/
	public String cfgfilepath = "/sdcard/MultGasDetecter/parameter/gasparam.dat";
	
	public String readProperties(String key)
	{
		String prop_string;
		
		Properties prop = new Properties();
		prop = loadConfig(cfgfilepath);
		prop_string = (String) prop.get(key);
		return prop_string;
	}
	
	public Properties loadConfig(String file) {  
		Properties properties = new Properties();  
		try {  
			FileInputStream s = new FileInputStream(file);  
			properties.load(s);  
		} catch (Exception e) {  
			e.printStackTrace();  
		}  
		return properties;  
	}  

    /** 
     *д��properties��Ϣ 
     * @param parameterName �����ļ������� 
     * @param parameterValue ��Ҫд��������ļ�����Ϣ 
     */  
    
    public void writeProperties(String parameterName, String parameterValue){
        
    	File file = new File(cfgfilepath);
    	if(!file.exists())
		{ 
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    	Properties prop = new Properties();  
        try {  
            InputStream fis = new FileInputStream(cfgfilepath);  
            //���������ж�ȡ�����б�����Ԫ�ضԣ�  
            prop.load(fis);  
            //���� Hashtable �ķ��� put��ʹ�� getProperty �����ṩ�����ԡ�  
            //ǿ��Ҫ��Ϊ���Եļ���ֵʹ���ַ���������ֵ�� Hashtable ���� put �Ľ����  
            OutputStream fos = new FileOutputStream(cfgfilepath);  
            prop.put(parameterName, parameterValue);  
            //���ʺ�ʹ�� load �������ص� Properties ���еĸ�ʽ��  
            //���� Properties ���е������б�����Ԫ�ضԣ�д�������  
            prop.store(fos, " Update '" + parameterName + "' value");  
           
        }  
        catch (IOException e) {  
//	        	Print.print("ConfigInfoError","Visit "+filePath+" for updating "+parameterName+" value error");  
            System.err.println("**********************");  
            System.err.println("\r\n write BalanceStat configuration failed,please check "+cfgfilepath+" is writer . thank you \n\n");  
            System.err.println("**********************");  
//	            throw e;  
        }  
    }  
    
    public void checkPropertiesKey(Context context)
    {
    	int i;
    	File file = new File(cfgfilepath);
    	if(!file.exists())
		{ 
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    	
    	String gas_param_list[] = context.getResources().getStringArray(R.array.gas_param);
    	for(i = 0;i<gas_param_list.length;i++ )
    	{
    		if(readProperties(gas_param_list[i])==null)/*���ÿһ��config�Ƿ���ֵ*/
    		{
    			if(i == 17)
    				writeProperties(gas_param_list[i],EnumDataSaveFreq.DATA_SAVE_FREQ_5S+"");/*���û��ֵ�����0*/
    			else
    				writeProperties(gas_param_list[i],"0");/*���û��ֵ�����0*/
    		}
    	}
    	 
    }
	 
	
	
	
}
