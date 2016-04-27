package com.lxl.travel.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.lxl.travel.ETGApplication;

/**
 * 异常统一处理
 * @author tarena
 *
 */
public class ExceptionUtil {
	public static void handleException(Exception e)
	{
		if (ETGApplication.isRelease)
		{
		//把异常信息变成字符串
			StringWriter stringWriter=new StringWriter();
			PrintWriter printWriter=new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			String string=stringWriter.toString();
			
		
		//发邮件，发到服务器
			
		}else
		{
			e.printStackTrace();
		}
	}

}
