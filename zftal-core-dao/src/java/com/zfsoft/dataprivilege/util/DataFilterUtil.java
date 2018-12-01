package com.zfsoft.dataprivilege.util;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionContext;
import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.dataprivilege.dto.AbstractFilter;
import com.zfsoft.dataprivilege.filter.IDealFilter;
import com.zfsoft.dataprivilege.xentity.Context;
import com.zfsoft.dataprivilege.xentity.Resource;
/**
 * 数据范围过滤器工具
 * @author Patrick Shen
 */
public class DataFilterUtil {
	private static final Logger logger=Logger.getLogger(DataFilterUtil.class);
	
	/**
	 * 获取过滤条件，程序主动调用时用
	 * @param paramMap
	 * @param contextId
	 * @return
	 */
	public static String getCondition(Map<String,Object> paramMap,String contextId){
		Context context = ResourceUtil.getContextById(contextId);
		
		IDealFilter dealFilter = null;
		try {
			dealFilter = (IDealFilter) Class.forName(context.getDealclass())
					.newInstance();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("resources.xml dealclass未正确配置，找不到类");
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return dealFilter.getCondition(paramMap);
	}
	/**
	 * 提供给Mybatis拦截器使用
	 * @param className
	 * @param methodName
	 * @return
	 */
	public static String getCondition( String className,String methodName) {
		if(ActionContext.getContext() == null){//如果Struts容器已启动,开始判断是否有数据范围过滤
			return null;
		}
		if(SessionFactory.getHttpRequest() == null){return null;}
		
		String url=SessionFactory.getHttpRequest().getServletPath();
		
		Resource resource=ResourceUtil.getResourceById(url, className, methodName);
		
		if (resource==null) {
			logger.debug("当前Url“无”需数据过滤的方法:"+className+"."+methodName);
			return null;
		}else{
			logger.debug("当前Url需数据过滤的方法:"+className+"."+methodName);
		}
		
		Context context = ResourceUtil.getContextByUrl(url);

		IDealFilter dealFilter = null;
		try {
			dealFilter = (IDealFilter) Class.forName(context.getDealclass())
					.newInstance();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("resources.xml dealclass未正确配置，找不到类");
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return dealFilter.getCondition(context,resource);
	}
	/**
	 * 获取过滤条件，程序主动调用时用
	 * @param paramMap
	 * @param contextId
	 * @return
	 */
	public static List<? extends AbstractFilter> getCondition(String contextId){
		
		Context context = ResourceUtil.getContextById(contextId);
		
		IDealFilter dealFilter = null;
		try {
			dealFilter = (IDealFilter) Class.forName(context.getDealclass())
					.newInstance();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("resources.xml dealclass未正确配置，找不到类");
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return dealFilter.getCondition();
	}
	
	/**
	 * 获取过滤条件，绩效专用
	 * @param contextId
	 * @return
	 */
	public static String getConditionForApr(String contextId,String aslisName,String fieldName){
		
		Context context = ResourceUtil.getContextById(contextId);
		
		IDealFilter dealFilter = null;
		try {
			dealFilter = (IDealFilter) Class.forName(context.getDealclass())
					.newInstance();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("resources.xml dealclass未正确配置，找不到类");
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return dealFilter.getConditionForApr(contextId, aslisName,fieldName);
	}
	
	public static Boolean isMathMethod(String className,String methodName){
		if(ActionContext.getContext() == null){//如果Struts容器已启动,开始判断是否有数据范围过滤
			return false;
		}
		String url=SessionFactory.getHttpRequest().getServletPath();
		
		Resource resource=ResourceUtil.getResourceById(url, className, methodName);
		
		if (resource==null) {
			logger.debug("当前Url“无”需数据过滤的方法:"+className+"."+methodName);
			return false;
		}else{
			logger.debug("当前Url需数据过滤的方法:"+className+"."+methodName);
			return true;
		}
	}
}
