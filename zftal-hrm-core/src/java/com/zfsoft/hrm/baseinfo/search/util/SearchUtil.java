package com.zfsoft.hrm.baseinfo.search.util;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.hrm.baseinfo.search.constants.SearchConstants;

/**
 * 查询工具类
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-9-20
 * @version V1.0.0
 */
public class SearchUtil {
	
	/**
	 * 根据当前状态（任职状态）进行过滤
	 * <p>
	 * 如果原始的条件表达式中不存在当前状态，则对其添加默认的状态，即“在职”
	 * </p>
	 * @param express 原始表达式
	 * @return
	 */
	public static String siftByInService( String express ) {
		StringBuilder result = new StringBuilder( express );
		
		siftByInService( result );
		
		return result.toString();
		
	}
	
	/**
	 * 根据当前状态（任职状态）进行过滤
	 * <p>
	 * 如果原始的条件表达式中不存在当前状态，则对其添加默认的状态，即“在职”
	 * </p>
	 * @param express 原始表达式
	 * @return
	 */
	public static void siftByInService( StringBuilder express ) {
		if( express.toString().toUpperCase().indexOf( SearchConstants.STATE_IN_SERVICE ) == -1) {
			if(!StringUtils.isEmpty(express.toString())){
				express.append(" and ");
			}
			
			express.append( SearchConstants.STATE_IN_SERVICE_1 );
		}
	}

}
