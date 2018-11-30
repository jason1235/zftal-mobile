package com.zfsoft.hrm.core.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * 操作异常信息工厂
 * <p>
 * 对异常的信息进行有效转换
 * </p>
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-9-13
 * @version V1.0.0
 */
public class ExceptionMessageFactory {
	
	private static Map<String, String> errors;
	
	static {
		errors = new HashMap<String, String>();
		errors.put("ORA-00904", "无效标识符！");
		errors.put("ORA-12899", "字段长度太长！");
	}
	
	/**
	 * 获取消息
	 * @param message 原始的消息
	 * @return
	 */
	public static String getMessage( String message ) {
		
		for ( String key : errors.keySet() ) {
			if( message.indexOf( key ) > -1 ) {
				message = errors.get( key );
				break;
			}
		}
		
		return message;
	}
	
}
