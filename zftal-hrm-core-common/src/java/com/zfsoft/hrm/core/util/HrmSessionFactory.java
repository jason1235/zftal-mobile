package com.zfsoft.hrm.core.util;

import java.util.HashMap;
import java.util.Map;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;

/**
 * 人事回话工厂
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-7-31
 * @version V1.0.0
 */
public class HrmSessionFactory {
	
	private HrmSessionFactory() {
		//do nothing
	}
	
	private static final String SESSION_INFO_CLASS_ID = "_s_hrm_infoClass_id";
	
	/**
	 * 在回话中查找当前信息类的信息(never null)
	 * @return	key：信息类类型 ； value：对应的信息类ID
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> getCurrentInfoClassSession() {
		Map<String, String> result = (HashMap<String, String>) SessionFactory.getSession().getAttribute( SESSION_INFO_CLASS_ID );
		
		if( result == null ) {
			result = new HashMap<String, String>();
		}
		
		return result;
	}
	
	/**
	 * 在回话中设置当前信息类
	 * @param clazz 当前信息类描述信息
	 */
	public static void setCurrentInfoClassSession( InfoClass clazz ) {
		
		setCurrentInfoClassSession( clazz.getCatalog().getType(), clazz.getGuid() );
	}
	
	/**
	 * 在回话中设置当前信息类
	 * @param type 当前信息类的类型
	 * @param classId 当前信息类ID
	 */
	public static void setCurrentInfoClassSession( String type, String classId ) {
		Map<String, String> _s_value = getCurrentInfoClassSession();
		_s_value.put( type, classId );
		
		SessionFactory.getSession().setAttribute( SESSION_INFO_CLASS_ID, _s_value );
		
	}
	
}
