package com.zfsoft.hrm.core.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

/** 
 * SQL表达式解析工具
 * @ClassName: SQLExplainUtil 
 * @author jinjj
 * @date 2012-6-21 下午01:22:53 
 *  
 */
public class SQLExplainUtil {
	
	public static final String LIKE = "##";
	
	public static final String EQUAL = "==";
	
	private static Map<String,String> keyMap;
	
	static {//常用解析映射
		keyMap = new HashMap<String,String>();
		keyMap.put( LIKE , "like");
		keyMap.put( EQUAL, "=");
	}
	
	public static String parseExpress(String express){
		if(StringUtils.isEmpty(express)){
			return null;
		}else{
			for(Entry<String,String> entry : keyMap.entrySet()){
				express = express.replaceAll(entry.getKey(), entry.getValue());
			}
			return express;
		}
	}
}
