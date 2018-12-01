package com.zfsoft.fifa.identity;

import java.io.Serializable;

/**
 * 页面类型
 * <p>
 * 现有页面的类型：主库、日志、快照、编辑、审核
 * <p>
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-15
 * @version V1.0.0
 */
public interface PageType extends Serializable {

	/**
	 * 主库
	 */
	public static final String OVERALL = "OVERALL";
	
	/**
	 * 日志
	 */
	public static final String LOG = "LOG";
	
	/**
	 * 快照
	 */
	public static final String SNAP = "SNAP";
	
	/**
	 * 编辑
	 */
	public static final String MAIN = "MAIN";
	
	/**
	 * 审核
	 */
	public static final String AUDIT = "AUDIT";
	
	/**
	 * 空
	 */
	public static final String EMPTY = "";
}
