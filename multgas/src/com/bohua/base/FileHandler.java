package com.bohua.base;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

import com.qinxiaoyu.multgas.R;

import android.content.Context;
import android.os.Environment;

public class FileHandler {
	
	static String tag = "FileHandler";
	private static void debug(String string){if(Debug.FileHandlerDebug() == 1) Debug.debugi(tag,string);}
	
	
	
	/**
	 * ��鿨״̬ 
	 * ����SD�����Ҿ��з���Ȩ�򷵻�0
	 * δ����SD�����߷���Ȩ�޲����򷵻�-1
	 **/
	public int checkSDcardStatus()
	{
		try {
			if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
			{
				debug("SD�����벢�Ҿ��з���Ȩ��");
				return 0;
			}
		}
		catch(Exception e){
			debug("SD��δ������߷���Ȩ�޲���");
			e.printStackTrace();
			return -1;
		}
		return 0;
	}
	
	/**
	 * ��ȡSD��·��
	 * ����SD����·��
	 **/
	public String getSdcardPath()
	{
		File sd = Environment.getExternalStorageDirectory();
		String file_path= sd.getPath();
		return file_path;
	}
	
	/**
	 * ��SD���д���һ���ļ��У�������ļ��Ѿ������򷵻�-1
	 * path ��ʾ��/sdcard/...... 
	 * name ��ʾ�ļ�����
	 **/
	public int creatFile(String name,String path){
		File sd = Environment.getExternalStorageDirectory();
		String file_path= sd.getPath()+path+"/"+name;
		File file = new File(file_path);
		if(!file.exists()){
			file.mkdir();
			debug("��SD���д����ļ��ɹ�");
			debug("�ļ�·��Ϊ"+file_path);
			return 0;
		}
		else
		{
			debug("��SD���������ļ���"+file_path);
			return -1;
		}
	}
	
	/**
	 * ����app����Ҫ�������ļ���
	 **/
	public int creatAllAppFiles(Context context)
	{
		int flag;

		String fileList[] = context.getResources().getStringArray(R.array.folder_list);
		debug("�ļ�����Ϊ"+fileList.length);
		if(checkSDcardStatus()==0)
		{
			for(int i=0;i<fileList.length;i++)
			{
				flag=creatFile(fileList[i],"");
				if(flag==0)
				{
					/**�����ļ��ɹ�*/
					debug("�����ļ�"+fileList[i]+"�ɹ�");
				}
				else if(flag == -1)
				{
					//debug("�����ļ�"+fileList[i]+"ʧ�ܣ���");
					//return -1;
				}
				else
				{
					debug("�����ļ�"+fileList[i]+"ʧ�ܣ���");
				}
			}
			return 0;
		}
		else
		{
			return -1;
		}
	}
	/**
	 * ����app����Ҫ�������ļ�
	 **/
	public int creatAppFiles()
	{
		File sd = Environment.getExternalStorageDirectory();
		String file_path= sd.getPath();

		String fileList[]={
				"/MultGasDetecter/config.cfg",
				"/MultGasDetecter/config1.cfg",
				"/MultGasDetecter/ratio.cfg"
//				"/MultGasDetecter/strength.cfg"
			   };
		if(checkSDcardStatus()==0)
		{ 
			try
			{
				for(int i=0;i<fileList.length;i++)
				{
					File file = new File(file_path+fileList[i]);
					debug("�����ļ�"+file_path+fileList[i]);
					if(!file.exists())
					{
						if(file.createNewFile())
						{
							debug("�����ļ�"+fileList[i]+"�ɹ�����");
							return 0;
						}
						else
						{
							debug("�����ļ�"+fileList[i]+"ʧ�ܣ���");
							return -1;
						}
					}
					else
					{
						debug("�Ѵ����ļ�"+file_path+fileList[i]);
					}
				}
			}
			catch(IOException  e)
			{
				e.printStackTrace();  
				return -1;
			}
		}
		return 0;
	}
	/**
	 * д�����ݵ��ļ���
	 * **/
	public int writeToFile(String path, String string, boolean type)
	{
		/**��ȡSD�����ļ�·��*/
		String filePath=getSdcardPath()+path;
		debug(filePath);
		File file = new File(filePath);
    	if(!file.exists())
		{ 
			try 
			{
				file.createNewFile();
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		FileWriter fw = null;
		BufferedWriter bw = null;
		try
		{
			fw = new FileWriter(filePath,type);	// ����FileWriter��������д���ַ���  
			bw = new BufferedWriter(fw); // ��������ļ������  
			bw.write(string + "\n"); // д���ļ�  
			bw.newLine();  
            bw.flush(); // ˢ�¸����Ļ���  
            bw.close();  
            fw.close();  
            debug("д�ļ��ɹ�");
			
		} catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			try
			{  
				bw.close();  
	            fw.close();  
	        } 
			catch (IOException e1) 
	        {  
                // TODO Auto-generated catch block  
	        }  
		}
		return 0;
	}
	/**
	 * ���ļ��ж�������  
	 * **/
	public String readFromFile(String path)
	{
		String ret= "";
		/**��ȡSD�����ļ�·��*/
		String filePath=getSdcardPath()+path;
		
		File file = new File(filePath);
    	if(!file.exists())
		{ 
			try 
			{
				file.createNewFile();
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    	
		FileReader fr = null;
		BufferedReader br = null;
		try 
		{
			fr = new FileReader(filePath);
			br = new BufferedReader(fr);
			String s = null;
			try 
			{

				while((s = br.readLine()) != null)
				{
					s = s+"\n";
					ret += s;
				}
				
				
		        br.close();  
		        fr.close();
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			debug("���ļ�"+ret);
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	
	public String getFileNameByPath(String filePath)
	{
		String fileName = filePath.substring(filePath.lastIndexOf("/")+1);
		return fileName;
	}
	public String getFileSize(File file)
	{
		   long fileSize = file.length();
           if(fileSize > 1024*1024) 
           {
		       float size = fileSize /(1024f*1024f);
		       return new DecimalFormat("#.00").format(size) + "MB";
           } 
           else if(fileSize >= 1024) 
           {
              float size = fileSize/1024;
              return new DecimalFormat("#.00").format(size) + "KB";
           } 
           else 
           {
              return fileSize + "B";
           }
	}
	
	public String getFileSuffix(File file) 
	{
		String type = "*/*";
		String fileName = file.getName();
		int dotIndex = fileName.indexOf('.');
		debug("indexOf "+dotIndex);
		if((dotIndex >-1) && (dotIndex < (fileName.length() - 1))) 
		{    
            return fileName.substring(dotIndex + 1);    
        }    
		return type;
	}
	
	
	
}
