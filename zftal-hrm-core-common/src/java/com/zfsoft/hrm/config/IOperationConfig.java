package com.zfsoft.hrm.config;

/** 
 * 动态类日志数据库操作类型
 * @author jinjj
 * @date 2012-10-15 下午03:29:05 
 *  
 */
public interface IOperationConfig {

	/**
	 * 增加
	 */
	public String ADD = "add";
	
	/**
	 * 修改
	 */
	public String MODIFY = "modify";
	
	/**
	 * 删除
	 */
	public String REMOVE = "remove";
}
