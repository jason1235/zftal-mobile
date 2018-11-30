package com.zfsoft.hrm.core.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;


/** 
 * 下载文件名生成工具，用于在多种浏览器中产生正确的中文文件名，CHORME暂不支持
 * @author jinjj
 * @date 2012-9-14 下午05:28:32 
 *  
 */
public class DownloadFilenameUtil {

	/**
	 * 生成正确的disposition信息,固定为UTF-8,待扩展
	 * @param userAgent
	 * @param filename
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String fileDisposition(String useragent,String filename) throws UnsupportedEncodingException{
		String charset = "UTF-8";
		String userAgent = StringUtils.lowerCase(useragent);
		String new_filename = URLEncoder.encode(filename, charset);
		String str = "attachment; filename="+URLEncoder.encode(filename, charset);
		if(StringUtils.isEmpty(userAgent)){
			return str;
		}
		if (userAgent.indexOf("msie") != -1)  
	    {  
			//str = "filename=" + filename;
	    }  
	    // Opera浏览器只能采用filename*  
	    else if (userAgent.indexOf("opera") != -1)  
	    {  
	    	str = "attachment; filename*=UTF-8''" + new_filename;  
	    }  
	    // Safari浏览器，只能采用ISO编码的中文输出  
	    else if (userAgent.indexOf("safari") != -1 )  
	   {  
	    	str = "attachment; filename=" + new String(filename.getBytes("UTF-8"),"ISO8859-1");  
	    }  
	    // Chrome浏览器，只能采用MimeUtility编码或ISO编码的中文输出  
	    else if (userAgent.indexOf("applewebkit") != -1 )  
	    {  
	        //new_filename = MimeUtility.encodeText(filename, "UTF8", "B");  
	        //str = "attachment; filename=" + new_filename;  
	    }  
	    // FireFox浏览器，可以使用MimeUtility或filename*或ISO编码的中文输出  
	    else if (userAgent.indexOf("mozilla") != -1)  
	    {  
	        str = "attachment; filename*=UTF-8''" + new_filename;  
	    }
		return str;
	}
}
