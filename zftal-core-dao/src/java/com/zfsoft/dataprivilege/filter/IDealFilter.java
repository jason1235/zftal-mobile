package com.zfsoft.dataprivilege.filter;

import java.util.List;
import java.util.Map;

import com.zfsoft.dataprivilege.dto.AbstractFilter;
import com.zfsoft.dataprivilege.xentity.Context;
import com.zfsoft.dataprivilege.xentity.Resource;
/**
 * 过滤处理接口
 * @author Patrick Shen
 */
public interface IDealFilter {
	/**
	 * 获取过滤对象，通过xml文件
	 * @param xml
	 * @return
	 */
	public AbstractFilter getObjectFromXml(String xml);
	/**
	 * 获取过滤字串，通过Object
	 * @param obj
	 * @return
	 */
	public String getXmlFromObject(AbstractFilter obj);
	/**
	 * 获取过滤条件字串，拦截器用
	 * @param context
	 * @param resource
	 * @return
	 */
	public String getCondition(Context context,Resource resource);
	
	/**
	 * 获取过滤条件，程序主动调用时用
	 * @param context
	 * @param resource
	 * @return
	 */
	public String getCondition(Map<String,Object> paramMap);
	
	/**
	 * 获取过滤对象，程序主动调用时用
	 * @return
	 */
	public List<? extends AbstractFilter> getCondition();
	
	/**
	 * 获取过滤对象，程序主动调用时用
	 * @return
	 */
	public String getConditionForApr(String contextId,String aslisName,String fieldName);
}
