package com.lxl.travel.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.lxl.travel.ETGApplication;

/**
 * �쳣ͳһ����
 * @author tarena
 *
 */
public class ExceptionUtil {
	public static void handleException(Exception e)
	{
		if (ETGApplication.isRelease)
		{
		//���쳣��Ϣ����ַ���
			StringWriter stringWriter=new StringWriter();
			PrintWriter printWriter=new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			String string=stringWriter.toString();
			
		
		//���ʼ�������������
			
		}else
		{
			e.printStackTrace();
		}
	}

}
