package com.zfsoft.hrm.baseinfo.search.constants;

/** 
 * 常用查询常量
 * @author jinjj
 * @date 2012-8-20 上午11:39:56 
 *  
 */
public interface SearchConstants {

	/**
	 * 人员信息表过滤表达式:
	 * 退休、离休、死亡、调出、辞职、离职、开除这几种状态值要从人员库中去除
	 */
//	public final String OverallFilterExpression = " dqztm not in ('01','02','03', '05','06','07','08')";
	
	/**
	 * 当前状态标志
	 */
	public final String STATE_IN_SERVICE = "DQZTM";
	
	/**
	 * 当前状态，在职
	 */
	public final String STATE_IN_SERVICE_1 = "DQZTM like '1%'";
	
	/**
	 * 当前状态，不在职
	 */
	public final String STATE_IN_SERVICE_0 = "DQZTM like '0%'";
}
