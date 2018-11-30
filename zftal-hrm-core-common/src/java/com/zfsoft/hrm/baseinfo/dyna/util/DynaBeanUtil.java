package com.zfsoft.hrm.baseinfo.dyna.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.zfsoft.hrm.baseinfo.dyna.business.bizinterface.IDynaBeanBusiness;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.config.IConstants;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.common.spring.SpringHolder;

/**
 * 动态信息类工具类
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-8-20
 * @version V1.0.0
 */
public class DynaBeanUtil {

	private static IDynaBeanBusiness business;
	private static long internal = 10*60*1000;//10分钟
	private static Map<String,DynaBean> personMap = new HashMap<String,DynaBean>();
	private static Map<String,Date> dateMap = new HashMap<String,Date>();
	
	static {
		business = (IDynaBeanBusiness) SpringHolder.getBean("baseDynaBeanBusiness");
	}
	
	/**
	 * 私有化构造函数
	 */
	private DynaBeanUtil() {
		// do nothing
	}
	
	/**
	 * 获取人员信息
	 * @param personId 人员ID，如工号
	 * @return
	 */
	public static DynaBean getPerson( String personId ) {
		Date date = dateMap.get(personId);
		if(date == null){
			return cachePerson(personId);
		}else if((new Date().getTime() - date.getTime())>=internal){
			return cachePerson(personId);
		}else{
			return personMap.get(personId);
		}
		
	}
	
	private synchronized static DynaBean cachePerson(String personId){
		DynaBean bean = business.findUniqueByParam( "gh", personId );
		personMap.put(personId, bean);
		dateMap.put(personId, new Date());
		return bean;
	}
	
	/**
	 * 获取人员姓名
	 * @param personId 人员ID，如工号,多个以;分隔
	 * @return
	 */
	public static String getPersonName( String personId ) {
		
		StringBuilder out = new StringBuilder();
		
		if( personId == null ) {
			return "";
		}
		
		String[] ids = personId.split( IConstants.SPLIT_STR );
		
		for ( String id : ids ) {
			String name = "";
			if(id.equals("system")){//内置用户
				name = "系统";
			}
			if(StringUtil.isEmpty(name)){
				DynaBean bean = getPerson( id );
				
				if( bean == null ) {
					continue;
				}else{
					name = (String)bean.getValue( "xm" );
				}
			}
			if(out.length()>0){
				out.append(IConstants.SPLIT_STR);
			}
			out.append(name);
		}
		
		return out.toString();
	}
	
	/**
	 * 清除某个用户缓存
	 * @param personId
	 */
	public static void clear(String personId){
		if(!StringUtil.isEmpty(personId)){
			dateMap.remove(personId);
			personMap.remove(personId);
		}
	}
	
	/**
	 * 清除所有用户缓存
	 */
	public static void clearAll(){
		dateMap.clear();
		personMap.clear();
	}
} 
